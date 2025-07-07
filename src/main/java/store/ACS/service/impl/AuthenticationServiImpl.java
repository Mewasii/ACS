package store.ACS.service.impl;

import org.springframework.stereotype.Service;
import store.ACS.dto.request.AuthenticationRequest;
import store.ACS.respository.UserRepo;
import store.ACS.service.IAuthenticationServi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthenticationServiImpl implements IAuthenticationServi {
	@Autowired
	UserRepo userRepo;

	public boolean authenticate(AuthenticationRequest request) {
		var user = userRepo.findByUsername(request.getUsername());
		PasswordEncoder PasswordEncoder = new BCryptPasswordEncoder(10);
		return PasswordEncoder.matches(request.getPassword(), user.getPassword());
	}

}
