package store.ACS.service;

import java.util.List;

import org.springframework.stereotype.Service;
import store.ACS.dto.UserCreRequest;
import store.ACS.dto.UserUpdRequest;
import store.ACS.entity.User;

@Service
public interface IUserServi {
	User createRequest(UserCreRequest request);

	List<User> getUser();

	User getUserById(Long Id);

	User updateUserById(Long userId, UserUpdRequest request);

	public void deleteUserById(Long userId);
}
