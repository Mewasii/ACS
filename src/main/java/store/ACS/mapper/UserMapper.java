package store.ACS.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import store.ACS.dto.request.UserCreRequest;
import store.ACS.dto.request.UserUpdRequest;
import store.ACS.dto.response.UserResponse;
import store.ACS.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	// Map Data từ request về user
	User toUser(UserCreRequest request);

	// Map Data từ user qua UserResponse
	UserResponse toUserResponse(User user);

//Phương thức user.set...(request.get...()) cho các field
	void updateUser(@MappingTarget User user, UserUpdRequest request);

}
