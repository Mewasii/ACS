package store.ACS.service;


import java.util.List;

import org.springframework.stereotype.Service;
import store.ACS.dto.UserCreRequest;
import store.ACS.entity.User;
@Service
public interface IUserServi {
	 User createRequest(UserCreRequest request);
	 List<User> getUser();
}
