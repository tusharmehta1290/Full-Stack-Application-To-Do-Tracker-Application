package com.niit.UserToDoTaskService.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "We are unable to find the user with this email kindly check again!")
public class UserNotFoundException extends Exception{
}
