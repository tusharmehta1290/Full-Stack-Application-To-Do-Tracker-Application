package com.niit.UserToDoTaskService.Domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import static java.util.UUID.*;

public class ToDoList
{
    // unique id of the task : should be generated automatically!
    @Id
    private UUID todoId;

    // Name of the Task
    private String taskName;

    // Description about the task
    private String taskDescription;

    // Task need to completed by which date
    private String completionDate;

    // Is the task completed or not
    private String isCompleted;

    // What is the importance of this particular task
    private String priority;

    public ToDoList()
    {
        this.todoId = UUID.randomUUID();
    }

    public ToDoList(UUID todoId, String taskName, String taskDescription, String completionDate, String isCompleted, String priority) {
        this.todoId = todoId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.completionDate = completionDate;
        this.isCompleted = isCompleted;
        this.priority = priority;
    }

    public UUID getTodoId() {
        return todoId;
    }

    public void setTodoId(UUID todoId) {
        this.todoId = todoId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "ToDoList{" +
                "todoId=" + todoId +
                ", taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", completionDate=" + completionDate +
                ", isCompleted='" + isCompleted + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
