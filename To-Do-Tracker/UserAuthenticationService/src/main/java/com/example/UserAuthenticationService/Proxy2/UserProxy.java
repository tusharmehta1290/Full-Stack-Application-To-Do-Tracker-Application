package com.example.UserAuthenticationService.Proxy2;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

// creating the feign client for deleting the user from the other service
@FeignClient(name = "UserTaskService", url = "localhost:8081")
public interface UserProxy
{

    // marking the function of the other service to get triggered itself
    @DeleteMapping("/api/v1/admin/deleteuser/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email);

}
