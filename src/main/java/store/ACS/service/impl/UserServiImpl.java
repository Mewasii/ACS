package store.ACS.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import store.ACS.dto.UserCreRequest;
import store.ACS.entity.User;
import store.ACS.respository.UserRepo;
import store.ACS.service.IUserServi;
@Service
public class UserServiImpl implements IUserServi {
	 @Autowired
	  private UserRepo userRepo;
	  public User createRequest(UserCreRequest request){
		  User user = new User();
		  
	        user.setUsername(request.getUsername());
	        user.setPassword(request.getPassword());
	        user.setFullname(request.getFullname());
	        user.setEmail(request.getEmail());
	        user.setPhone(request.getPhone());
	        user.setRole(request.getRole());
	        user.setActive(request.getActive());	        
	        return userRepo.save(user);
	    }
	  
	  
	  //gọi users
	  public List<User> getUser(){
	    	return userRepo.findAll();
	  }
}
