package store.ACS.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import store.ACS.dto.request.UserCreRequest;
import store.ACS.dto.request.UserUpdRequest;
import store.ACS.dto.response.UserResponse;
import store.ACS.entity.Role;
import store.ACS.entity.User;
import store.ACS.mapper.UserMapper;
import store.ACS.respository.RoleRepo;
import store.ACS.respository.UserRepo;
import store.ACS.service.IUserServi;



@Service
public class UserServiImpl implements IUserServi {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo roleRepo;

	// Create user
	public User createRequest(UserCreRequest request) {
	    // check exist
	    if (userRepo.existsByUsername(request.getUsername())) {
	        throw new RuntimeException("Username existed");
	    }
	    if (userRepo.existsByEmail(request.getEmail())) {
	        throw new RuntimeException("Email existed");
	    }
	    if (userRepo.existsByPhone(request.getPhone())) {
	        throw new RuntimeException("Phone existed");
	    }

	    User user = userMapper.toUser(request);
	    user.setPassword(passwordEncoder.encode(request.getPassword()));

	    // Nếu client không gửi role thì gán mặc định USER
	    if (request.getRoles() == null || request.getRoles().isEmpty()) {
	        Role defaultRole = roleRepo.findById("USER")
	                .orElseThrow(() -> new RuntimeException("Default role USER not found in DB"));
	        user.setRoles(Set.of(defaultRole));
	    }

	    return userRepo.save(user);
	}


	// gọi all users
	//@PreAuthorize("hasRole('ADMIN')")
	public List<User> getUser() {
		return userRepo.findAll();
	}

	// gọi user theo id
	@Override
	public UserResponse getUserById(UUID Id) {
		return userMapper.toUserResponse( userRepo.findById(Id).orElseThrow(() -> new RuntimeException("User not found")));
	}

	// update user theo id
	@Override
	public UserResponse updateUserById(UUID userId, UserUpdRequest request) {
	    User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		var roles = roleRepo.findAllById(request.getRoles());
		user.setRoles(new HashSet<>(roles));
		return userMapper.toUserResponse( userRepo.save(user));
	}

	// delete user theo id
	public void deleteUserById(UUID userId) {
		userRepo.deleteById(userId);
	}
	//ContextHolder
	public UserResponse getMyInfo() {
		var context = SecurityContextHolder.getContext();
		String name = context.getAuthentication().getName();
		User user = userRepo.findByUsername(name).orElseThrow(() -> new RuntimeException("User not found"));
		return userMapper.toUserResponse(user);
		}
	
}
