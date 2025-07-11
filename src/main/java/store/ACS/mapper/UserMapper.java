package store.ACS.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import store.ACS.dto.request.UserCreRequest;
import store.ACS.dto.request.UserUpdRequest;
import store.ACS.dto.response.UserResponse;
import store.ACS.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // Map Data từ request về user
    @Mapping(source = "roles", target = "roles")
    User toUser(UserCreRequest request);

    // Map Data từ user qua UserResponse
    @Mapping(source = "roles", target = "roles")
    UserResponse toUserResponse(User user);

    // Phương thức user.set...(request.get...()) cho các field
    @Mapping(source = "roles", target = "roles")
    void updateUser(@MappingTarget User user, UserUpdRequest request);
}