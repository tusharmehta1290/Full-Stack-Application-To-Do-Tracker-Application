package com.niit.UserToDoTaskService.Controller;

import com.niit.UserToDoTaskService.Domain.ToDoList;
import com.niit.UserToDoTaskService.Domain.User;
import com.niit.UserToDoTaskService.Exception.TaskNotFoundException;
import com.niit.UserToDoTaskService.Exception.UserAlreadyPresentException;
import com.niit.UserToDoTaskService.Exception.UserNotFoundException;
import com.niit.UserToDoTaskService.Service.UserToDoServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UserToDoController
{

    private UserToDoServiceImpl userToDoService;
    private ResponseEntity<?> responseEntity;

    @Autowired
    public UserToDoController(UserToDoServiceImpl userToDoService)
    {
        this.userToDoService = userToDoService;
    }

    //getting email from the token
    private String getEmailFromToken(HttpServletRequest request)
    {
        String authHeader = request.getHeader("Authorization");
        Claims claims = (Claims) request.getAttribute("claims");
        return claims.getSubject();
    }

    // Registering a new User!
    @PostMapping("/register")
    public ResponseEntity<?> registerANewUser(@RequestBody User user) throws UserAlreadyPresentException
    {

        try
        {
            responseEntity = new ResponseEntity<>(userToDoService.registerNewUser(user), HttpStatus.CREATED);
        }
        catch (UserAlreadyPresentException exe)
        {
            throw new UserAlreadyPresentException();
        }
        catch (Exception exe)
        {
            responseEntity = new ResponseEntity<>(exe.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    // posting a new task
    @PostMapping("/user/todo")
    public ResponseEntity<?> saveANewTask(@RequestBody ToDoList toDoList, HttpServletRequest request) throws UserNotFoundException
    {
        System.out.println(toDoList);
        try
        {
            String emailId = getEmailFromToken(request);
            responseEntity = new ResponseEntity<>(userToDoService.saveNewTask(toDoList, emailId), HttpStatus.CREATED);
        }
        catch (UserNotFoundException exe)
        {
            throw new UserNotFoundException();
        }
        catch (Exception exe)
        {
            responseEntity = new ResponseEntity<>(exe.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    // Getting all the available Tasks of the User
    @GetMapping("/user/todos")
    public ResponseEntity<?> getAllTasks(HttpServletRequest request) throws UserNotFoundException
    {

        try
        {
            String emailId = getEmailFromToken(request);
            responseEntity = new ResponseEntity<>(userToDoService.getAllTasks(emailId), HttpStatus.OK);
        }
        catch (UserNotFoundException exe)
        {
            throw new UserNotFoundException();
        }
        catch (Exception exe)
        {
            responseEntity = new ResponseEntity<>(exe.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    // updating an existing Task based on email from token
    @PutMapping("user/todo")
    public ResponseEntity<?> updateExistingTask(@RequestBody ToDoList toDoList, HttpServletRequest request) throws UserNotFoundException, TaskNotFoundException
    {
        try
        {
            String emailId = getEmailFromToken(request);
            responseEntity = new ResponseEntity<>(userToDoService.updateTask(toDoList, emailId), HttpStatus.RESET_CONTENT);
        }
        catch (UserNotFoundException exe)
        {
            throw new UserNotFoundException();
        }
        catch (TaskNotFoundException exe)
        {
            throw new TaskNotFoundException();
        }
        catch (Exception exe)
        {
            responseEntity = new ResponseEntity<>(exe.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    // deleting a specific task
    @DeleteMapping("/user/todo/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable UUID taskId, HttpServletRequest request) throws UserNotFoundException, TaskNotFoundException
    {
        try
        {
            String emailId = getEmailFromToken(request);
            responseEntity = new ResponseEntity<>(userToDoService.deleteTask(emailId, taskId), HttpStatus.OK);
        }
        catch (UserNotFoundException exe)
        {
            throw new UserNotFoundException();
        }
        catch (TaskNotFoundException exe)
        {
            throw new TaskNotFoundException();
        }
        catch (Exception exe)
        {
            responseEntity = new ResponseEntity<>(exe.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @GetMapping("/user/getUsername")
    public ResponseEntity<?> getUserName(HttpServletRequest request) throws UserNotFoundException
    {

        try
        {
            String emailId = getEmailFromToken(request);
            responseEntity = new ResponseEntity<>(userToDoService.getUserName(emailId),HttpStatus.OK);
        }
        catch (UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }


    @GetMapping("/user/get-todo/{todoId}")
    public ResponseEntity<?> getSingleTask(HttpServletRequest request, @PathVariable UUID todoId) throws UserNotFoundException,TaskNotFoundException
    {

        try
        {
            String emailId= getEmailFromToken(request);
            responseEntity = new ResponseEntity<>(userToDoService.getSingleTask(emailId,todoId),HttpStatus.OK);
        }
        catch (UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
        catch (TaskNotFoundException e)
        {
            throw new TaskNotFoundException();
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }


//   ------------------------------------------ ADMIN USE --------------------------------------------------

    // deleting a user getting triggered by the UserAuthenticationService so that user needs to re register
    @DeleteMapping("/admin/deleteuser/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) throws UserNotFoundException
    {
        try
        {
            responseEntity = new ResponseEntity<>(userToDoService.deleteUser(email),HttpStatus.OK);
        }
        catch (UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }


// ------------------------------------------------- SECRET TASKS ------------------------------------------------------------

    // Getting all the secret tasks
    @GetMapping("user/archievedTodoList")
    public ResponseEntity<?> getAllTasksFromSecretList(HttpServletRequest request) throws UserNotFoundException
    {

        try
        {
            String emailId = getEmailFromToken(request);
            responseEntity = new ResponseEntity<>(userToDoService.getAllSecretTasks(emailId),HttpStatus.OK);
        }
        catch (UserNotFoundException exe)
        {
            throw new UserNotFoundException();
        }
        catch (Exception exe)
        {
            responseEntity = new ResponseEntity<>(exe.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }


    // shifting a task from the todolist to the secret task list
    @PostMapping("/user/archieveTodo/{uuid}")
    public ResponseEntity<?> postNewSecretTask(HttpServletRequest request, @RequestBody ToDoList toDoList, @PathVariable UUID uuid) throws UserNotFoundException, TaskNotFoundException
    {

        try
        {
            String emailId = getEmailFromToken(request);
            responseEntity = new ResponseEntity<>(userToDoService.postNewSecretTask(emailId,uuid),HttpStatus.OK);
        }
        catch (UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
        catch (TaskNotFoundException e)
        {
            throw new TaskNotFoundException();
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity<>("Some issue in secret task",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    // deleting a task from the secret task
    @DeleteMapping("user/archievedTodoList/{taskId}")
    public ResponseEntity<?> deleteSecretTask(@PathVariable UUID taskId, HttpServletRequest request) throws UserNotFoundException,TaskNotFoundException
    {

        try
        {
            String emailId = getEmailFromToken(request);
            responseEntity = new ResponseEntity<>(userToDoService.deleteTaskFromSecretTask(emailId,taskId),HttpStatus.OK);
        }
        catch (UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
        catch (TaskNotFoundException e)
        {
            throw new TaskNotFoundException();
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    // ------------------------------------------------- TRANSFERRING BACK FROM SECRET LIST -------------------------

    @PostMapping("/user/transfer/{uuid}")
    public ResponseEntity<?> postBackSecretTask(HttpServletRequest request, @RequestBody ToDoList toDoList, @PathVariable UUID uuid) throws UserNotFoundException, TaskNotFoundException
    {

        try
        {
            String emailId = getEmailFromToken(request);
            responseEntity = new ResponseEntity<>(userToDoService.TransferTaskFromSecretTaskToMainList(emailId,uuid),HttpStatus.OK);
        }
        catch (UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
        catch (TaskNotFoundException e)
        {
            throw new TaskNotFoundException();
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity<>("Some issue in secret task",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @GetMapping("/user/get-stask/{todoId}")
    public ResponseEntity<?> getSingleTaskFromSecretTasks(HttpServletRequest request, @PathVariable UUID todoId) throws UserNotFoundException,TaskNotFoundException
    {

        try
        {
            String emailId= getEmailFromToken(request);
            System.out.println("WORKING TILL HERE");
            responseEntity = new ResponseEntity<>(userToDoService.GetSingleTaskFromSecretTask(emailId,todoId),HttpStatus.OK);
        }
        catch (UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
        catch (TaskNotFoundException e)
        {
            throw new TaskNotFoundException();
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    // GETTING THE  SECRET PASSWORD FOR SECURITY!

    @GetMapping("/user/getmesecretpassword")
    public ResponseEntity<?> fetchSecretPassword(HttpServletRequest request) throws UserNotFoundException
    {
        try
        {
            String emailId = getEmailFromToken(request);
            String secretPassword = userToDoService.giveBackTheSecretPassword(emailId);
            //  it seems you are returning the actual secret password as a plain string, not as a JSON object. When the frontend receives this response,
            //  it tries to parse it as JSON, which causes a syntax error because a plain string is not a valid JSON.
            responseEntity = new ResponseEntity<>(Collections.singletonMap("secretPassword", secretPassword), HttpStatus.OK);
        }
        catch (UserNotFoundException exe)
        {
            throw new UserNotFoundException();
        }
        catch (Exception exe)
        {
            responseEntity = new ResponseEntity<>("Error in fetching the secret password", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }


    @GetMapping("/user/getOnlyEmail")
    public ResponseEntity<?> giveBackTheEmail(HttpServletRequest request) throws UserNotFoundException
    {
        try
        {
            String email = getEmailFromToken(request);
            responseEntity = new ResponseEntity<>(Collections.singletonMap("emailId",email), HttpStatus.OK);
        }

        catch (Exception exe)
        {
            responseEntity = new ResponseEntity<>("Error is getting only email", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

}
