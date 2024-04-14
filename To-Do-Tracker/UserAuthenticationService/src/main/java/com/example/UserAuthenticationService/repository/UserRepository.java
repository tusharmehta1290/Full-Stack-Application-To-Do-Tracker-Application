package com.example.UserAuthenticationService.repository;

import com.example.UserAuthenticationService.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String>
{
    // user defined method
    User findByEmailIdAndPassword(String emailId, String password);
}
