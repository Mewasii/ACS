package store.ACS.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import store.ACS.dto.request.UserCreRequest;
import store.ACS.dto.request.UserUpdRequest;
import store.ACS.dto.response.UserResponse;
import store.ACS.entity.Role;
import store.ACS.entity.User;
import store.ACS.respository.RoleRepo;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected RoleRepo roleRepo;

    // Map từ request -> entity
    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    public abstract User toUser(UserCreRequest request);

    public abstract UserResponse toUserResponse(User user);
    
    //map user được lấy lên từ repo(role = dataset với request role = String)
    @Mapping(target = "roles",ignore = true, source = "roles", qualifiedByName = "mapRoles")
    public abstract void updateUser(@MappingTarget User user, UserUpdRequest request);

    // Custom mapper để convert Set<String> -> Set<Role>
    @Named("mapRoles")
    protected Set<Role> mapRoles(Set<String> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(roleName -> roleRepo.findById(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .collect(Collectors.toSet());
    }
}
