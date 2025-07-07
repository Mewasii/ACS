package store.ACS.service;

import store.ACS.dto.request.AuthenticationRequest;

public interface IAuthenticationServi {
	boolean authenticate(AuthenticationRequest request);
}
