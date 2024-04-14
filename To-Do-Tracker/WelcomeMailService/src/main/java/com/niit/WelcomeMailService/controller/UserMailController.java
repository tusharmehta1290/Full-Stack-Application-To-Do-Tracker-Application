package com.niit.WelcomeMailService.controller;

import com.niit.WelcomeMailService.model.User;
import com.niit.WelcomeMailService.service.UserMailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class UserMailController
{

    private UserMailServiceImpl userMailService;

    @Autowired
    public UserMailController(UserMailServiceImpl userMailService) {
        this.userMailService = userMailService;
    }


    // method getting called automatically by other service using the Feign client
    @PostMapping("/sendEmail")
    public String sendTheEmailToUser(@RequestBody User user)
    {
        String status = userMailService.sendMail(user);
        return status;
    }


    @PostMapping("/sendConfirmationStatus")
    public String sendConfirmation(@RequestBody User user)
    {
        return userMailService.sendWelcomeEmail(user);
    }

}
