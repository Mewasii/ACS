package store.ACS.service;

import java.text.ParseException;

import org.springframework.web.bind.annotation.RequestBody;

import com.nimbusds.jose.JOSEException;

import store.ACS.dto.request.AuthenticationRequest;
import store.ACS.dto.request.IntrospectRequest;
import store.ACS.dto.request.LogoutRequest;
import store.ACS.dto.request.RefreshTokenRequest;
import store.ACS.dto.response.AuthenticationResponse;
import store.ACS.dto.response.IntrospectResponse;

public interface IAuthenticationServi {
	AuthenticationResponse authenticate(AuthenticationRequest request);

	IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException;
	
	void logout(@RequestBody LogoutRequest request) throws ParseException,JOSEException;
	
	public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException,JOSEException;
}
