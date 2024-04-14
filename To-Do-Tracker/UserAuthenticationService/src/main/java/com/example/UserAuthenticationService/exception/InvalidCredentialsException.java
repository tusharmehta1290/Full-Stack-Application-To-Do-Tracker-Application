package com.example.UserAuthenticationService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid Credentials. Please enter correct credentials")
public class InvalidCredentialsException extends Exception{
}
