package store.ACS.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import store.ACS.dto.request.PermissionRequest;
import store.ACS.dto.response.PermissionResponse;
import store.ACS.entity.Permission;
import store.ACS.mapper.PermissionMapper;
import store.ACS.respository.PermissionRepo;
import store.ACS.service.IPermissionServi;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiImpl implements IPermissionServi {
	PermissionRepo permissionRepo;
	PermissionMapper permissionMapper;

	public PermissionResponse create(PermissionRequest request) {
		Permission permission = permissionMapper.toPermission(request);
		permission = permissionRepo.save(permission);
		return permissionMapper.toPermissionResponse(permission);
	}

	public List<PermissionResponse> getAll() {
		var permissions = permissionRepo.findAll();
		return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
	}

	public void delete(String permission) {
		permissionRepo.deleteById(permission);
	}
}