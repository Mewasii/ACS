package store.ACS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import store.ACS.dto.ApiResponse;
import store.ACS.dto.UserCreRequest;
import store.ACS.dto.UserUpdRequest;
import store.ACS.entity.User;
import store.ACS.service.IUserServi;

@RestController
public class UserContro {

	@Autowired
	private IUserServi iUserServi;

	// Tạo mới user
	@PostMapping("/users") // thêm Validate và trả về apiResponse
	public ResponseEntity<ApiResponse<User>> createUser(@RequestBody @Valid UserCreRequest request) {
		User createdUser = iUserServi.createRequest(request);
		ApiResponse<User> response = new ApiResponse<>(true, "Create Successfully", createdUser,
				HttpStatus.CREATED.value() // Trả về status
		);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// Lấy toàn bộ user
	@GetMapping("/users")
	List<User> getUsers() {
		return iUserServi.getUser();
	}

	// Lấy user theo Id
	@GetMapping("/{userId}")
	User getUser(@PathVariable Long userId) {
		return iUserServi.getUserById(userId);
	}

	// Thay đổi user theo Id
	@PutMapping("/{userId}")
	User updateUser(@PathVariable Long userId, @RequestBody UserUpdRequest request) {
		return iUserServi.updateUserById(userId, request);
	}

	// Xóa User theo Id
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable Long userId) {
		iUserServi.deleteUserById(userId);
	}
}
