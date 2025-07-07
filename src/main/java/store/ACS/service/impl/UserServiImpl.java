package store.ACS.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import store.ACS.dto.request.UserCreRequest;
import store.ACS.dto.request.UserUpdRequest;
import store.ACS.dto.response.UserResponse;
import store.ACS.entity.User;
import store.ACS.mapper.UserMapper;
import store.ACS.respository.UserRepo;
import store.ACS.service.IUserServi;

@Service
public class UserServiImpl implements IUserServi {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private UserMapper userMapper;

	// Create user
	public User createRequest(UserCreRequest request) {
		// check exist
		if (userRepo.existsByUsername(request.getUsername())) {
			throw new RuntimeException("Username existed");
		}
		if (userRepo.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email existed");
		} else {
			User user = userMapper.toUser(request);
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4) ; //param strenght được truyền vào 
			user.setPassword(passwordEncoder.encode(request.getPassword()));
			return  userRepo.save(user);

		}
	}

	// gọi all users
	public List<User> getUser() {
		return userRepo.findAll();
	}

	// gọi user theo id
	@Override
	public UserResponse getUserById(Long Id) {
		return userMapper.toUserResponse( userRepo.findById(Id).orElseThrow(() -> new RuntimeException("User not found")));
	}

	// update user theo id
	@Override
	public UserResponse updateUserById(Long userId, UserUpdRequest request) {
	    User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		userMapper.updateUser(user,request);
		return userMapper.toUserResponse( userRepo.save(user));
	}

	// delete user theo id
	public void deleteUserById(Long userId) {
		userRepo.deleteById(userId);
	}
	
}
