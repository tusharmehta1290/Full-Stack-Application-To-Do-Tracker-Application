package com.niit.UserToDoTaskService.Service;

import com.niit.UserToDoTaskService.Domain.ToDoList;
import com.niit.UserToDoTaskService.Domain.User;
import com.niit.UserToDoTaskService.Exception.TaskAlreadyPresentException;
import com.niit.UserToDoTaskService.Exception.TaskNotFoundException;
import com.niit.UserToDoTaskService.Exception.UserAlreadyPresentException;
import com.niit.UserToDoTaskService.Exception.UserNotFoundException;
import com.niit.UserToDoTaskService.Repository.UserToDoRepository;
import com.niit.UserToDoTaskService.proxy.UserToDoProxy;
import com.niit.UserToDoTaskService.proxy.UserWelcomeEmailProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.*;

@Service
public class UserToDoServiceImpl implements UserToDoService
{

    private UserToDoRepository userToDoRepository;

    private UserToDoProxy userToDoProxy;

    private UserWelcomeEmailProxy userWelcomeEmailProxy;

    @Autowired
    public UserToDoServiceImpl(UserToDoRepository userToDoRepository, UserToDoProxy userToDoProxy,UserWelcomeEmailProxy userWelcomeEmailProxy)
    {
        this.userToDoRepository = userToDoRepository;
        this.userToDoProxy = userToDoProxy;
        this.userWelcomeEmailProxy = userWelcomeEmailProxy;
    }

    // registering a new user!
    @Override
    public User registerNewUser(User user) throws UserAlreadyPresentException
    {
        if (userToDoRepository.findById(user.getEmailId()).isPresent())
            throw new UserAlreadyPresentException();

        // ----------->
        User newUser = userToDoRepository.save(user);
        if(!(newUser.getEmailId().isEmpty())){
            ResponseEntity re = userToDoProxy.saveUser(user);
            System.out.println(re.getBody());
        }

        String sendMail = userWelcomeEmailProxy.sendTheEmailToUser(newUser);

        return newUser;
    }

    // Saving a new Task in the ToDoList!
    @Override
    public User saveNewTask(ToDoList toDoList, String emailId) throws UserNotFoundException
    {
        if (userToDoRepository.findById(emailId).isEmpty())
            throw new UserNotFoundException();

        User userData = userToDoRepository.findById(emailId).get();

        List<ToDoList> myToDoList = userData.getToDoList();

        if (myToDoList == null)
        myToDoList = new ArrayList<>();

        String newDate = toDoList.getCompletionDate();
        String subDate = newDate.substring(0,10);

        LocalDate completionDate = LocalDate.parse(subDate);
        LocalDate newCompletionDate = completionDate.plusDays(1);
        String formattedDate = newCompletionDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

        toDoList.setCompletionDate(formattedDate);

        myToDoList.add(toDoList);

        userData.setToDoList(myToDoList);

        return userToDoRepository.save(userData);
    }

    // Getting all the task that are currently available!
    @Override
    public List<ToDoList> getAllTasks(String emailId) throws UserNotFoundException
    {
        if (userToDoRepository.findById(emailId).isEmpty())
            throw new UserNotFoundException();

        List<ToDoList> allTasks = userToDoRepository.findById(emailId).get().getToDoList();

        String UserName = getUserName(emailId);

        if(allTasks.equals(null) || allTasks.isEmpty() || allTasks == null )
        {
            ToDoList welcomeUser = new ToDoList(
              UUID.randomUUID(),
                    "Welcome "+UserName,
                    "Welcome to our ToDo App! We're delighted to have you on board. Feel free to add, manage, and prioritize your tasks. Don't hesitate to reach out to our support team.",
                    "",
                    "",
                    ""
            );

            allTasks.add(welcomeUser);
            userToDoRepository.save(userToDoRepository.findById(emailId).get());

        }


        return allTasks;
    }

    // updating a specific task!
    @Override
    public User updateTask(ToDoList toDoList, String emailId) throws UserNotFoundException, TaskNotFoundException {
        boolean isUpdated = false;

        if (userToDoRepository.findById(emailId).isEmpty())
            throw new UserNotFoundException();

        User user = userToDoRepository.findById(emailId).get();
        List<ToDoList> allTasks = user.getToDoList();

        for (ToDoList currentListElement : allTasks) {
            UUID currentListId = currentListElement.getTodoId();
            if (currentListId.equals(toDoList.getTodoId())) {
                // Use equals() for comparing UUIDs
                if (toDoList.getTaskName() != null)
                    currentListElement.setTaskName(toDoList.getTaskName());

                if (toDoList.getTaskDescription() != null)
                    currentListElement.setTaskDescription(toDoList.getTaskDescription());

                if (toDoList.getCompletionDate() != null)
                {
                    String subDate = toDoList.getCompletionDate().substring(0,10);

                    LocalDate completionDate = LocalDate.parse(subDate);
                    LocalDate newCompletionDate = completionDate.plusDays(1);
                    String formattedDate = newCompletionDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

                    toDoList.setCompletionDate(formattedDate);

                    currentListElement.setCompletionDate(toDoList.getCompletionDate());
                }

                if (toDoList.getIsCompleted() != null)
                    currentListElement.setIsCompleted(toDoList.getIsCompleted());

                if (toDoList.getPriority() != null)
                    currentListElement.setPriority(toDoList.getPriority());

                isUpdated = true;
                break; // Exit the loop once the task is found and updated
            }
        }

        if (!isUpdated)
            throw new TaskNotFoundException();

        user.setToDoList(allTasks);

        return userToDoRepository.save(user);
    }

    // deleting a specific task!
    @Override
    public User deleteTask(String emailId, UUID taskId) throws UserNotFoundException, TaskNotFoundException {
        boolean deletedSuccessfully = false;

        if (userToDoRepository.findById(emailId).isEmpty())
            throw new UserNotFoundException();

        User user = userToDoRepository.findById(emailId).get();
        List<ToDoList> allToDoList = user.getToDoList();

        Iterator<ToDoList> toDoListIterator = allToDoList.iterator();
        while (toDoListIterator.hasNext()) {
            ToDoList currentElement = toDoListIterator.next();
            if (currentElement.getTodoId().equals(taskId)) {
                toDoListIterator.remove();
                deletedSuccessfully = true;
                break; // Exit the loop once the task is found and deleted
            }
        }

        if (!deletedSuccessfully)
            throw new TaskNotFoundException();

        userToDoRepository.save(user);

        return user;
    }


    @Override
    public String getUserName(String emailId) throws UserNotFoundException
    {
        if (userToDoRepository.findById(emailId).isEmpty())
            throw new UserNotFoundException();

        User user = userToDoRepository.findById(emailId).get();
        String userName = user.getUserName();

        return userName;
    }


    @Override
    public ToDoList getSingleTask(String emailId, UUID id) throws UserNotFoundException, TaskNotFoundException
    {
        ToDoList myObj = null;
        boolean check = false;
        if (userToDoRepository.findById(emailId).isEmpty())
            throw new UserNotFoundException();

        User user = userToDoRepository.findById(emailId).get();
        List<ToDoList> currentList = user.getToDoList();

        if (currentList == null)
             throw new TaskNotFoundException();

        Iterator<ToDoList> i = currentList.iterator();

        while (i.hasNext())
        {
            ToDoList currentElement = i.next();
            if (currentElement.getTodoId()
                    .toString().equals(id.toString()))
            {
                myObj = currentElement;
                check = true;
                break;
            }
        }

        if (!check)
            throw new TaskNotFoundException();

        return myObj;
    }

    // ----------------------------------- ADMIN USE --------------------------------------------

    // deleting a user by the admin function getting called automatically from Feign client so that the whole data got deleted
    @Override
    public boolean deleteUser(String email) throws UserNotFoundException
    {
        boolean isDeleted = false;
                if(userToDoRepository.findById(email).isEmpty())
                    throw new UserNotFoundException();

                else
                {
                    userToDoRepository.deleteById(email);
                    isDeleted = true;
                }

        return isDeleted;
    }

    //---------------------------------- Secret Tasks --------------------------------------

    // getting all the secret tasks
    @Override
    public List<ToDoList> getAllSecretTasks(String emailId) throws UserNotFoundException
    {
        if (userToDoRepository.findById(emailId).isEmpty())
            throw new UserNotFoundException();

        return userToDoRepository.findById(emailId).get().getArchieveList();
    }


    // shifting a task from the todoList to the secretList and deleting it from the previous list
    @Override
    public User postNewSecretTask(String emailId, UUID taskId) throws UserNotFoundException, TaskNotFoundException
    {
        if (userToDoRepository.findById(emailId).isEmpty())
            throw new UserNotFoundException();

        User user = userToDoRepository.findById(emailId).get();

        List<ToDoList> allToDoList = user.getToDoList();
        List<ToDoList> archiveList = user.getArchieveList();

        ToDoList archivedTask = null;

        Iterator<ToDoList> toDoListIterator = allToDoList.iterator();

        while (toDoListIterator.hasNext())
        {
            ToDoList currentElement = toDoListIterator.next();
            if (currentElement.getTodoId().equals(taskId)) {
                toDoListIterator.remove();
                archivedTask = currentElement;
                break;
            }
        }

        if (archivedTask == null)
            throw new TaskNotFoundException();


        if (archiveList == null)
            archiveList = new ArrayList<>();

        archiveList.add(archivedTask);
        user.setToDoList(allToDoList);
        user.setArchieveList(archiveList);

        userToDoRepository.save(user);

        return user;
    }

    // deleting a secret task from the list
    @Override
    public User deleteTaskFromSecretTask(String emailId, UUID taskId) throws UserNotFoundException, TaskNotFoundException
    {
        boolean isDeleted = false;

        if (userToDoRepository.findById(emailId).isEmpty())
            throw new UserNotFoundException();

        User user = userToDoRepository.findById(emailId).get();
        List<ToDoList> currentSecretList= user.getArchieveList();

        Iterator<ToDoList> i = currentSecretList.iterator();

        while (i.hasNext())
        {
            ToDoList currentTask = i.next();

            if (currentTask.getTodoId().equals(taskId))
            {
                i.remove();
                isDeleted = true;
                break;
            }

        }

        if (!isDeleted)
            throw new TaskNotFoundException();

        user.setArchieveList(currentSecretList);

        return userToDoRepository.save(user);
    }


    @Override
    public User TransferTaskFromSecretTaskToMainList(String emailId, UUID taskId) throws UserNotFoundException, TaskNotFoundException
    {
        if (userToDoRepository.findById(emailId).isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = userToDoRepository.findById(emailId).get();

        List<ToDoList> allToDoList = user.getToDoList();
        List<ToDoList> archiveList = user.getArchieveList();

        ToDoList restoredTask = null;

        Iterator<ToDoList> archiveListIterator = archiveList.iterator();

        while (archiveListIterator.hasNext()) {
            ToDoList currentElement = archiveListIterator.next();
            if (currentElement.getTodoId().equals(taskId)) {
                archiveListIterator.remove();
                restoredTask = currentElement;
                break;
            }
        }

        if (restoredTask == null) {
            throw new TaskNotFoundException();
        }

        if (allToDoList == null) {
            allToDoList = new ArrayList<>();
        }

        allToDoList.add(restoredTask);
        user.setToDoList(allToDoList);
        user.setArchieveList(archiveList);

        userToDoRepository.save(user);

        return user;
    }

    @Override
    public ToDoList GetSingleTaskFromSecretTask(String emailId, UUID id) throws UserNotFoundException, TaskNotFoundException
    {
        ToDoList myObj = null;
        boolean check = false;

        if (userToDoRepository.findById(emailId).isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = userToDoRepository.findById(emailId).get();
        List<ToDoList> secretList = user.getArchieveList();

        System.out.println("************************************");
        System.out.println(secretList);

        if (secretList == null || secretList.isEmpty()) {
            throw new TaskNotFoundException();
        }

        Iterator<ToDoList> i = secretList.iterator();

        while (i.hasNext()) {
            ToDoList currentElement = i.next();
            if (currentElement.getTodoId().equals(id)) {
                myObj = currentElement;
                check = true;
                break;
            }
        }

        if (!check) {
            throw new TaskNotFoundException();
        }

        return myObj;
    }

    @Override
    public String giveBackTheSecretPassword(String emailId) throws UserNotFoundException {

        if (userToDoRepository.findById(emailId).isEmpty())
            throw new UserNotFoundException();

        User user = userToDoRepository.findById(emailId).get();
        String myPassword = user.getSecretPassword();

        if(myPassword == null) {
            System.out.println("SOME ERROR IN FETCHING THE SECRET PASSWORD!");
        }

        return myPassword;
    }

    @Override
    public String giveBackTheEmail(String emailId) throws UserNotFoundException
    {
        if (userToDoRepository.findById(emailId).isEmpty())
            throw new UserNotFoundException();


        return emailId;
    }
}

