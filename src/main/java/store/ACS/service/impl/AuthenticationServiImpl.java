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
import store.ACS.dto.request.AuthenticationRequest;
import store.ACS.dto.request.IntrospectRequest;
import store.ACS.dto.response.AuthenticationResponse;
import store.ACS.dto.response.IntrospectResponse;
import store.ACS.entity.User;
import store.ACS.respository.UserRepo;
import store.ACS.service.IAuthenticationServi;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthenticationServiImpl implements IAuthenticationServi {
	@Autowired
	UserRepo userRepo;
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
	public IntrospectResponse introspect(IntrospectRequest request) throws ParseException {
		var token = request.getToken();
		SignedJWT signedJWT;
		boolean verified;
		try {
			signedJWT = SignedJWT.parse(token);
			MACVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
			verified = signedJWT.verify(verifier);
		} catch (Exception e) {
			return IntrospectResponse.builder().valid(false).build(); // Token lỗi định dạng hoặc không xác minh được
		}

		Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
		boolean isValid = verified && expiryTime.after(new Date());

		return IntrospectResponse.builder().valid(isValid).build();
	}
	//hàm thêm role vào jwt
	private String buildScope(User user) {
	    StringJoiner stringJoiner = new StringJoiner(" ");
	    if (!CollectionUtils.isEmpty(user.getRoles())) { // Check if roles are NOT empty
	        user.getRoles().forEach(stringJoiner::add);
	    }
	    return stringJoiner.toString();
	}
}
