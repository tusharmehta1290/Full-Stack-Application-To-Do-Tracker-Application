package com.niit.UserToDoTaskService.proxy;

import com.niit.UserToDoTaskService.Domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Feign Client for registering user in the mysql database using communication
@FeignClient(name = "UserAuthenticationService", url = "localhost:8082" )
public interface UserToDoProxy
{
    // calling the UserAuthenticationService method and save the user automatically!
    @PostMapping("/api/v2/save")
    public ResponseEntity<?> saveUser(@RequestBody User user);
}
