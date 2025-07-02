package store.ACS.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import store.ACS.dto.UserCreRequest;
import store.ACS.dto.UserUpdRequest;
import store.ACS.entity.User;
import store.ACS.respository.UserRepo;
import store.ACS.service.IUserServi;

@Service
public class UserServiImpl implements IUserServi {
	@Autowired
	private UserRepo userRepo;

	// Create user
	public User createRequest(UserCreRequest request) {
		// check exist
		if (userRepo.existsByUsername(request.getUsername())) {
			throw new RuntimeException("Username existed");
		}
		if (userRepo.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email existed");
		} else {
			User user = new User();
			user.setUsername(request.getUsername());
			user.setPassword(request.getPassword());
			user.setFullname(request.getFullname());
			user.setEmail(request.getEmail());
			user.setPhone(request.getPhone());
			user.setRole(request.getRole());
			user.setActive(request.getActive());
			return userRepo.save(user);
		}
	}

	// gọi all users
	public List<User> getUser() {
		return userRepo.findAll();
	}

	// gọi user theo id
	@Override
	public User getUserById(Long Id) {
		return userRepo.findById(Id).orElseThrow(() -> new RuntimeException("User not found"));
	}

	// update user theo id
	@Override
	public User updateUserById(Long userId, UserUpdRequest request) {
		User user = getUserById(userId);
		user.setPassword(request.getPassword());
		user.setFullname(request.getFullname());
		user.setEmail(request.getEmail());
		user.setPhone(request.getPhone());
		user.setRole(request.getRole());
		user.setActive(request.getActive());
		return userRepo.save(user);
	}

	// delete user theo id
	public void deleteUserById(Long userId) {
		userRepo.deleteById(userId);
	}
}
