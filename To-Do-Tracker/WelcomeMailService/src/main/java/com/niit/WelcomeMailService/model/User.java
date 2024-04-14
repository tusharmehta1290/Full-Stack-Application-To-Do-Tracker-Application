package com.niit.WelcomeMailService.model;

public class User
{

    private String emailId;
    private String userName;
    private String password;
    private String msgBody;
    private String subject;

    public User() {
    }

    public User(String emailId, String userName, String password, String msgBody, String subject) {
        this.emailId = emailId;
        this.userName = userName;
        this.password = password;
        this.msgBody = msgBody;
        this.subject = subject;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "User{" +
                "emailId='" + emailId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", msgBody='" + msgBody + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }

}
