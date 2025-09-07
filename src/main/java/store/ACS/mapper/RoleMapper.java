package store.ACS.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import store.ACS.dto.request.RoleRequest;

import store.ACS.dto.response.RoleResponse;
import store.ACS.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
	@Mapping(target = "permissions",ignore = true)

	Role toRole(RoleRequest request);

	RoleResponse toRoleResponse(Role role);

}