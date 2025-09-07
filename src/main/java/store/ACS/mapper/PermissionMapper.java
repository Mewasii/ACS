package store.ACS.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import store.ACS.dto.request.PermissionRequest;
import store.ACS.dto.response.PermissionResponse;
import store.ACS.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

    void updatePermission(@MappingTarget Permission permission, PermissionRequest request);
}