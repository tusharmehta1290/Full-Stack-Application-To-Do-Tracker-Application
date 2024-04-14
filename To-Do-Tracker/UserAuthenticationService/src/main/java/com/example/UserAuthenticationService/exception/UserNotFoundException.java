package com.example.UserAuthenticationService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User is not present in the database!")
public class UserNotFoundException extends Exception{
}
