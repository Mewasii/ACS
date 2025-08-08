package store.ACS.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import store.ACS.dto.request.UserCreRequest;
import store.ACS.dto.request.UserUpdRequest;
import store.ACS.dto.response.UserResponse;
import store.ACS.entity.User;

@Service
public interface IUserServi {

	User createRequest(UserCreRequest request);
    List<User> getUser();
    UserResponse getUserById(UUID userId);
    UserResponse updateUserById(UUID userId, UserUpdRequest request);
    void deleteUserById(UUID userId);
	UserResponse getMyInfo();
	
}
