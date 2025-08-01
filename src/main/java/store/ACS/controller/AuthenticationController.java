package store.ACS.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nimbusds.jose.JOSEException;

import jakarta.validation.Valid;
import store.ACS.dto.request.AuthenticationRequest;
import store.ACS.dto.request.IntrospectRequest;
import store.ACS.dto.response.AuthenticationResponse;
import store.ACS.dto.response.IntrospectResponse;
import store.ACS.dto.response.ApiResponse;
import store.ACS.service.IAuthenticationServi;

import java.text.ParseException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final IAuthenticationServi iAuthenticationServi;

	@PostMapping("/log-in")
	public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(
			@Valid @RequestBody AuthenticationRequest request) {
		AuthenticationResponse authResponse = iAuthenticationServi.authenticate(request);
		boolean isAuthenticated = authResponse.isAuthenticated();
		HttpStatus status = isAuthenticated ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
		String message = isAuthenticated ? "Login successful" : "Invalid username or password";

		ApiResponse<AuthenticationResponse> response = ApiResponse.<AuthenticationResponse>builder()
				.success(isAuthenticated).message(message).result(authResponse).status(status.value())
				.timestamp(LocalDateTime.now()).build();

		return ResponseEntity.status(status).body(response);
	}

	@PostMapping("/introspect")
	public ResponseEntity<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request)
			throws ParseException, JOSEException {
		IntrospectResponse result = iAuthenticationServi.introspect(request);

		boolean isValid = result.isValid();
		HttpStatus status = isValid ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

		ApiResponse<IntrospectResponse> response = ApiResponse.<IntrospectResponse>builder().success(isValid)
				.message(isValid ? "Token is valid" : "Token is invalid or expired").status(status.value())
				.timestamp(LocalDateTime.now()).result(result).build();

		return ResponseEntity.status(status).body(response);
	}

}
