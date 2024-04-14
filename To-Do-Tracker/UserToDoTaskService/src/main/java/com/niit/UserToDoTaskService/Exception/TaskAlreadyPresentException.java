package com.niit.UserToDoTaskService.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "Task Already present")
public class TaskAlreadyPresentException extends Exception{
}
