package com.niit.UserToDoTaskService.Domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;

// we are using the MongoDB here.
@Document
public class User
{

    // email of the user
    @Id
    private String emailId;

    // password of the user
    private String password;

    // unique name of the user

//    ---------------------------------- Initially used username as unique but changing frontend from username to Full Name.
    //    @Indexed(unique = true)
    private String userName;

    // Getting code/password from user for the SecretCode
    private String secretPassword;

    // User has todoList
    private List<ToDoList> toDoList;

    private  List<ToDoList> archieveList;
// -------------------------------------------------------------need to add the list for secret lists. -------------------------

    public User () {

    }

    public User(String emailId, String password, String userName, String secretPassword, List<ToDoList> toDoList, List<ToDoList> archieveList) {
        this.emailId = emailId;
        this.password = password;
        this.userName = userName;
        this.secretPassword = secretPassword;
        this.toDoList = toDoList;
        this.archieveList = archieveList;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<ToDoList> getToDoList() {
        return toDoList;
    }

    public void setToDoList(List<ToDoList> toDoList) {
        this.toDoList = toDoList;
    }

    public List<ToDoList> getArchieveList() {
        return archieveList;
    }

    public void setArchieveList(List<ToDoList> archieveList) {
        this.archieveList = archieveList;
    }

    public String getSecretPassword() {
        return secretPassword;
    }

    public void setSecretPassword(String secretPassword) {
        this.secretPassword = secretPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", secretPassword='" + secretPassword + '\'' +
                ", toDoList=" + toDoList +
                ", archieveList=" + archieveList +
                '}';
    }
}