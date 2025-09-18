package store.ACS.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import store.ACS.dto.request.AuthenticationRequest;
import store.ACS.dto.request.IntrospectRequest;
import store.ACS.dto.request.LogoutRequest;
import store.ACS.dto.request.RefreshTokenRequest;
import store.ACS.dto.response.AuthenticationResponse;
import store.ACS.dto.response.IntrospectResponse;
import store.ACS.entity.InvalidatedToken;
import store.ACS.entity.User;
import store.ACS.exception.AppException;
import store.ACS.exception.ErrorCode;
import store.ACS.respository.InvalidTokenRepo;
import store.ACS.respository.UserRepo;
import store.ACS.service.IAuthenticationServi;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Slf4j
@Service
public class AuthenticationServiImpl implements IAuthenticationServi {
	@Autowired
	UserRepo userRepo;
	@Autowired
	InvalidTokenRepo invalidTokenRepo;
	//Gọi key 
	 @NonFinal
	 @Value("${jwt.signerkey}")
	 protected String SIGNER_KEY;
	 
	 //Kiểm tra login, hợp lệ trả về cho client token
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		var user = userRepo.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
		if (!authenticated) {
			return AuthenticationResponse.builder().Authenticated(false).token(null).build();
		} else {
			var token = generateToken(user);
			return AuthenticationResponse.builder().token(token).Authenticated(true).build();
		}
	}
    //Hàm tạo Token 
	private String generateToken(User user) {
// JSON WEB SIGNATURE HEADER
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

// BODY
		JWTClaimsSet jwtClamsSet = new JWTClaimsSet.Builder().subject(user.getUsername()).issuer("AnimalConShop.com")
				.issueTime(new Date()).expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
				// thêm id cho token
				.jwtID(UUID.randomUUID().toString())
				//thêm vào claim table giá trị scope = roles
				.claim("scope",buildScope(user))
				.build();

		Payload payload = new Payload(jwtClamsSet.toJSONObject());
//inject header và payload vào token
		JWSObject jwsObject = new JWSObject(header, payload);
// Ký Token
		try {
			jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
			return jwsObject.serialize();
		} catch (JOSEException e) {
			throw new RuntimeException(e);
		}
	}

	// Kiểm tra token ( dùng signature )
	 public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
	        var token = request.getToken();
	        boolean isValid = true;

	        try {
	            verifytoken(token);
	        } catch (Exception e ) {
	            isValid = false;
	        }

	        return IntrospectResponse.builder().valid(isValid).build();
	    }
	//hàm thêm role vào jwt
	private String buildScope(User user) {
	    StringJoiner stringJoiner = new StringJoiner(" ");
	    if (!CollectionUtils.isEmpty(user.getRoles())) { // Check if roles are NOT empty
	        user.getRoles().forEach(role -> {
	        stringJoiner.add("ROLE_"+role.getName());
	        if (!CollectionUtils.isEmpty(role.getPermissions()))
	     role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
	        });
	    }
	    return stringJoiner.toString();
	}
	//giữ token khi logout
	public void logout(LogoutRequest request)  throws ParseException,JOSEException {
		var signedToken = verifytoken(request.getToken());
		String jit = signedToken.getJWTClaimsSet().getJWTID();
		Date expiryTime = signedToken.getJWTClaimsSet().getExpirationTime();
		InvalidatedToken invalidatedToken = InvalidatedToken.builder()
				.id(jit)
				.expiryTime(expiryTime)
				.build();
	 invalidTokenRepo.save(invalidatedToken);
	}
	
	private SignedJWT verifytoken(String token) {
	    try {
	        SignedJWT signedJWT = SignedJWT.parse(token);
	        MACVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
	        boolean verified = signedJWT.verify(verifier);

	        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

	        if (!verified) {
	            throw new AppException(ErrorCode.TOKEN_INVALID);
	        }
	        if (expiryTime.before(new Date())) {
	            throw new AppException(ErrorCode.TOKEN_EXPIRED);
	        }
	        if (invalidTokenRepo.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
	            throw new AppException(ErrorCode.TOKEN_BLACKLISTED);
	        }

	        return signedJWT;

	    } catch (AppException e) {
	        // ném lại các lỗi custom để handler xử lý
	        throw e;
	    } catch (Exception e) {
	        // fallback cho lỗi parse hoặc lỗi JOSE khác
	        throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
	    }
	}
	
	public AuthenticationResponse refreshToken(RefreshTokenRequest request)throws ParseException,JOSEException {
		//Thực hiện invalidated token cũ
		var signedJWT = verifytoken(request.getToken());
		var jit = signedJWT.getJWTClaimsSet().getJWTID(); 
		var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
		InvalidatedToken invalidatedToken = InvalidatedToken.builder()
				.id(jit)
				.expiryTime(expiryTime)
				.build();
		 invalidTokenRepo.save(invalidatedToken);
		 //Issue Token mới 
		 var username =  signedJWT.getJWTClaimsSet().getSubject();
		 log.info("Authenticating user: {}", username);
		 var user = userRepo.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED) );
		 var token = generateToken(user);
		return AuthenticationResponse.builder().token(token).Authenticated(true).build();
	}

}
