package com.niit.WelcomeMailService.service;

import com.niit.WelcomeMailService.model.User;

public interface UserMailService
{

    String sendMail(User user);

    public String sendWelcomeEmail(User user);

}
