package com.example.UserAuthenticationService.service;

import com.example.UserAuthenticationService.Proxy2.UserProxy;
import com.example.UserAuthenticationService.domain.User;
import com.example.UserAuthenticationService.exception.InvalidCredentialsException;
import com.example.UserAuthenticationService.exception.UserAlreadyExistsException;
import com.example.UserAuthenticationService.exception.UserNotFoundException;
import com.example.UserAuthenticationService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService
{
    private UserRepository userRepository;
    private UserProxy userProxy;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserProxy userProxy)
    {
        this.userRepository = userRepository;
        this.userProxy = userProxy;
    }

    // Saving a new User
    @Override
    public User saveUser(User user) throws UserAlreadyExistsException
    {

        if(userRepository.findById(user.getEmailId()).isPresent())
        {
            throw new UserAlreadyExistsException();
        }

        return userRepository.save(user);
    }

    // checking the credentials
    @Override
    public User getUserByEmailIdAndPassword(String emailId, String password) throws InvalidCredentialsException
    {

        User loggedUser = userRepository.findByEmailIdAndPassword(emailId, password);

        if (loggedUser == null)
        {
            throw new InvalidCredentialsException();
        }

        return loggedUser;
    }

//    -------------------------------------------------- ADMIN USE ------------------------------------------------------

    // get all the users for admin
    @Override
    public List<User> gettingAllUsersForAdmin()
    {
        return userRepository.findAll();
    }

    // delete a user by admin due to some reason
    @Override
    public boolean deleteExistingUserForAdmin(String email) throws UserNotFoundException
    {
        boolean isDeleted = false;

        if (userRepository.findById(email).isEmpty())
            throw new UserNotFoundException();

        else
        {
            userRepository.deleteById(email);
            ResponseEntity r = userProxy.deleteUser(email);
            isDeleted = true;
        }

        return isDeleted;
    }

}
