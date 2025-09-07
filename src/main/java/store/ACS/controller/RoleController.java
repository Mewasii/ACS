package store.ACS.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import store.ACS.dto.request.RoleRequest;
import store.ACS.dto.response.ApiResponse;
import store.ACS.dto.response.RoleResponse;
import store.ACS.service.IRoleService;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
	IRoleService iRoleservice;

	@PostMapping
	ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
		return ApiResponse.<RoleResponse>builder().result(iRoleservice.create(request)).build();
	}

	@GetMapping
	ApiResponse<List<RoleResponse>> getAll() {
		return ApiResponse.<List<RoleResponse>>builder().result(iRoleservice.getAll()).build();
	}

	@DeleteMapping("/{permission}")
	ApiResponse<Void> delete(@PathVariable String role) {
		iRoleservice.delete(role);
		return ApiResponse.<Void>builder().build();
	}
}
