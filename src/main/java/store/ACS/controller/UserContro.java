package store.ACS.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import store.ACS.dto.UserCreRequest;
import store.ACS.entity.User;
import store.ACS.service.IUserServi;

@RestController
public class UserContro {

    @Autowired
    private IUserServi iUserServi;
    

    // Tạo mới user
    @PostMapping("/users")
     User createUser(@RequestBody UserCreRequest request) {
        return iUserServi.createRequest(request);
    }
    @GetMapping("/users")
     List<User> getUsers(){
       return iUserServi.getUser();
    }
}
