package store.ACS.service;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;

import store.ACS.dto.request.AuthenticationRequest;
import store.ACS.dto.request.IntrospectRequest;
import store.ACS.dto.response.AuthenticationResponse;
import store.ACS.dto.response.IntrospectResponse;

public interface IAuthenticationServi {
	AuthenticationResponse authenticate(AuthenticationRequest request);

	IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException;
}
