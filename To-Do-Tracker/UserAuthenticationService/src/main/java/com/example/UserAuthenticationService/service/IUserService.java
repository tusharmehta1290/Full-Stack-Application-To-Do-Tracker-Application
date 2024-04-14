package com.example.UserAuthenticationService.service;

import com.example.UserAuthenticationService.domain.User;
import com.example.UserAuthenticationService.exception.InvalidCredentialsException;
import com.example.UserAuthenticationService.exception.UserAlreadyExistsException;
import com.example.UserAuthenticationService.exception.UserNotFoundException;

import java.util.*;

public interface IUserService
{
    User saveUser(User user) throws UserAlreadyExistsException;
    User getUserByEmailIdAndPassword(String emailId, String password) throws InvalidCredentialsException;

    // ----------------------- Admin Use -------------------------------------------

    List<User> gettingAllUsersForAdmin();
    boolean deleteExistingUserForAdmin(String email) throws UserNotFoundException;

}
