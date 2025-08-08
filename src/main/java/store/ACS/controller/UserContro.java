package store.ACS.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import store.ACS.dto.request.UserCreRequest;
import store.ACS.dto.request.UserUpdRequest;
import store.ACS.dto.response.ApiResponse;
import store.ACS.dto.response.UserResponse;
import store.ACS.entity.User;
import store.ACS.service.IUserServi;

@RestController
@RequestMapping("/users")
public class UserContro {

	@Autowired
	private IUserServi iUserServi;

	// Tạo mới user
	@PostMapping
	public ResponseEntity<ApiResponse<User>> createUser(@RequestBody @Valid UserCreRequest request) {
		User createdUser = iUserServi.createRequest(request);

		ApiResponse<User> response = ApiResponse.<User>builder().success(true).message("Create Successfully")
				.result(createdUser).status(HttpStatus.CREATED.value()).timestamp(LocalDateTime.now()).build();

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// Lấy toàn bộ user
	@GetMapping
	public ResponseEntity<ApiResponse<List<User>>> getUsers() {
		List<User> users = iUserServi.getUser();
		ApiResponse<List<User>> response = ApiResponse.<List<User>>builder().success(true).message("List of users")
				.result(users).status(HttpStatus.OK.value()).timestamp(LocalDateTime.now()).build();

		return ResponseEntity.ok(response);
	}

	// Lấy user theo Id
	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable UUID userId) {
		UserResponse user = iUserServi.getUserById(userId);

		ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder().success(true).message("User found")
				.result(user).status(HttpStatus.OK.value()).timestamp(LocalDateTime.now()).build();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/myInfo")
	public ResponseEntity<ApiResponse<UserResponse>> getMyInfo() {
		UserResponse user = iUserServi.getMyInfo();

		ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder().success(true).message("User found")
				.result(user).status(HttpStatus.OK.value()).timestamp(LocalDateTime.now()).build();

		return ResponseEntity.ok(response);
	}

	// Cập nhật user theo Id
	@PutMapping("/{userId}")
	public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable UUID userId,
			@RequestBody @Valid UserUpdRequest request) {
		UserResponse updatedUser = iUserServi.updateUserById(userId, request);

		ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder().success(true)
				.message("Update Successfully").result(updatedUser).status(HttpStatus.OK.value())
				.timestamp(LocalDateTime.now()).build();

		return ResponseEntity.ok(response);
	}

	// Xóa user theo Id
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable UUID userId) {
		iUserServi.deleteUserById(userId);

		ApiResponse<Object> response = ApiResponse.builder().success(true).message("Deleted successfully")
				.status(HttpStatus.NO_CONTENT.value()).timestamp(LocalDateTime.now()).build();

		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
	}
}
