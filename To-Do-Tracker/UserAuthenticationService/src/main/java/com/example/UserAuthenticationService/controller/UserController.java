package com.example.UserAuthenticationService.controller;

import com.example.UserAuthenticationService.domain.User;
import com.example.UserAuthenticationService.exception.InvalidCredentialsException;
import com.example.UserAuthenticationService.exception.UserAlreadyExistsException;
import com.example.UserAuthenticationService.exception.UserNotFoundException;
import com.example.UserAuthenticationService.security.SecurityTokenGeneration;
import com.example.UserAuthenticationService.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
public class UserController
{
    private SecurityTokenGeneration securityTokenGeneration;
    private IUserService iUserService;
    private ResponseEntity<?> responseEntity;

    @Autowired
    public UserController(IUserService iUserService, SecurityTokenGeneration securityTokenGeneration)
    {
        this.iUserService = iUserService;
        this.securityTokenGeneration = securityTokenGeneration;
    }

    // Saving a new User
    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody User user) throws UserAlreadyExistsException
    {
        try
        {
            User userData = iUserService.saveUser(user);
            responseEntity = new ResponseEntity<>(userData, HttpStatus.CREATED);
        }
        catch(UserAlreadyExistsException e)
        {
            throw new UserAlreadyExistsException();
        }
        catch(Exception e)
        {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    // Login and creating a token
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) throws InvalidCredentialsException
    {
        User retrievedUser = iUserService.getUserByEmailIdAndPassword(user.getEmailId(), user.getPassword());
        if (retrievedUser == null)
        {
            throw new InvalidCredentialsException();
        }

        String token = securityTokenGeneration.createToken(user);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

// --------------------------------------------- Admin Use ---------------------------------------------

    // getting all the users and passwords
    @GetMapping("/admin/getAllUsers")
    public ResponseEntity<?> getAllCurrentRegisteredUsers()
    {
        try
        {
            responseEntity = new ResponseEntity<>(iUserService.gettingAllUsersForAdmin(),HttpStatus.OK);
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }

        return responseEntity;
    }

    // deleting a user
    @DeleteMapping("/admin/deleteUser/{email}")
    public ResponseEntity<?> deleteUserForAdmin(@PathVariable String email) throws UserNotFoundException
    {

        try
        {
            responseEntity = new ResponseEntity<>(iUserService.deleteExistingUserForAdmin(email),HttpStatus.OK);
        }
        catch (UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
        catch (Exception e)
        {
            responseEntity =  new ResponseEntity<>("Some Error in deleting the user",HttpStatus.BAD_GATEWAY);
        }

        return responseEntity;
    }


}
