package com.example.UserAuthenticationService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User Already Exists. Please enter details with different Id")
public class UserAlreadyExistsException extends Exception{
}
