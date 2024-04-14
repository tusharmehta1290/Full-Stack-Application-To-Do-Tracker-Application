package com.niit.UserToDoTaskService.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "The User is already present in our database, kindly use some other credentials!")
public class UserAlreadyPresentException extends Exception {
}
