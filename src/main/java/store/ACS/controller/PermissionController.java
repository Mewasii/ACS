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
import store.ACS.dto.request.PermissionRequest;
import store.ACS.dto.response.ApiResponse;
import store.ACS.dto.response.PermissionResponse;
import store.ACS.service.IPermissionServi;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
	IPermissionServi iPermissionService;

	@PostMapping
	ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
		return ApiResponse.<PermissionResponse>builder().result(iPermissionService.create(request)).build();
	}

	@GetMapping
	ApiResponse<List<PermissionResponse>> getAll() {
		return ApiResponse.<List<PermissionResponse>>builder().result(iPermissionService.getAll()).build();
	}

	@DeleteMapping("/{permission}")
	ApiResponse<Void> delete(@PathVariable String permission) {
		iPermissionService.delete(permission);
		return ApiResponse.<Void>builder().build();
	}
}
