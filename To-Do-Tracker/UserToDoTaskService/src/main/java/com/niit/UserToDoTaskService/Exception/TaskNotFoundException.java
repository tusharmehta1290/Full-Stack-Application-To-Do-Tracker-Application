package com.niit.UserToDoTaskService.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Task is not found!")
public class TaskNotFoundException extends Exception{
}
