package com.niit.UserToDoTaskService.proxy;

import com.niit.UserToDoTaskService.Domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// this will auto trigger the email service when user register and email the user as a welcome email

@FeignClient(name = "welcomeemailservice", url = "localhost:8083")
public interface UserWelcomeEmailProxy
{

    // function from the WelcomeEmailService to get called automatically!
    @PostMapping("/mail/sendEmail")
    public String sendTheEmailToUser(User user);

}
