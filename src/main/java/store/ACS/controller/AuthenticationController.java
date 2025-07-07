package store.ACS.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import store.ACS.dto.request.AuthenticationRequest;
import store.ACS.dto.response.AuthenticationResponse;
import store.ACS.dto.response.ApiResponse;
import store.ACS.service.IAuthenticationServi;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final IAuthenticationServi iAuthenticationServi;

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(
			@RequestBody AuthenticationRequest request) {

		boolean isAuthenticated = iAuthenticationServi.authenticate(request);

		AuthenticationResponse authResponse = AuthenticationResponse.builder().Authenticated(isAuthenticated).build();

		String message = isAuthenticated ? "Login successfully" : "Invalid credentials";

		ApiResponse<AuthenticationResponse> response = new ApiResponse<>(isAuthenticated, message, authResponse,
				isAuthenticated ? HttpStatus.OK.value() : HttpStatus.UNAUTHORIZED.value());

		return ResponseEntity.status(isAuthenticated ? HttpStatus.OK : HttpStatus.UNAUTHORIZED).body(response);
	}
}
