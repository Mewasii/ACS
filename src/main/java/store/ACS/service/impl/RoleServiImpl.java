package store.ACS.service.impl;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import store.ACS.dto.request.RoleRequest;
import store.ACS.dto.response.RoleResponse;
import store.ACS.mapper.RoleMapper;
import store.ACS.respository.PermissionRepo;
import store.ACS.respository.RoleRepo;
import store.ACS.service.IRoleService;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiImpl implements IRoleService {
RoleRepo roleRepo;
PermissionRepo permissionRepo;
RoleMapper roleMapper;


public RoleResponse create(RoleRequest request) {
    var role = roleMapper.toRole(request);
    var permissions = permissionRepo.findAllById(request.getPermission());
    role.setPermissions(new HashSet<>(permissions));
    roleRepo.save(role);
    return roleMapper.toRoleResponse(role);
}

public List<RoleResponse> getAll(){
	return roleRepo.findAll().stream().map(roleMapper::toRoleResponse).toList();
}
public void delete(String role ) {
	roleRepo.deleteById(role);
}

}
