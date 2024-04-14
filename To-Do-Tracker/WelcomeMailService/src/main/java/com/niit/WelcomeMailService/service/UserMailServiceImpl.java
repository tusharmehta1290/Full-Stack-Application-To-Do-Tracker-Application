package com.niit.WelcomeMailService.service;

import com.niit.WelcomeMailService.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UserMailServiceImpl implements UserMailService
{

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("$(spring.mail.username)")
    private String sender;

    @Override
    public String sendMail(User user) {

        try {

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            // implementing the sending from the application properties
            simpleMailMessage.setFrom(sender);

            // implementing the receiver
            simpleMailMessage.setTo(user.getEmailId());

            // setting up the subject
            simpleMailMessage.setSubject("Welcome to Todo Tracker – Your Gateway to Productivity Excellence!");

            // setting a welcome body
            String body = String.format("Dear %s,\n\n" +
                    "Welcome to Todo Tracker! We're delighted to have you embark on this journey of organization and productivity " +
                    "with us. As you step into the world of task tracking, envision a seamless and efficient experience tailored just " +
                    "for you. Your decision to join Todo Tracker signifies a commitment to personal and professional growth, " +
                    "and we're here to support every endeavor. May your days be filled with accomplishment and your goals " +
                    "effortlessly met. Good luck on this venture, and may Todo Tracker be your trusted companion in achieving " +
                    "all your aspirations.\n\n" +
                    "Best regards,\nTodo Tracker Team", user.getUserName());

            simpleMailMessage.setText(body);

            // sending the mail !
            javaMailSender.send(simpleMailMessage);

            return "Mail is sent successfully!";
        }
        catch (Exception e)
        {
            return "Some Issue in sending the mail";
        }

    }


    @Override
    public String sendWelcomeEmail(User user)
    {
        try {
            // creating a simple mail message
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            //Setting up necessary details
            simpleMailMessage.setFrom(sender);


            simpleMailMessage.setTo(user.getEmailId());
            simpleMailMessage.setText(user.getMsgBody());
            simpleMailMessage.setSubject(user.getSubject());

            // Sending the mail
            javaMailSender.send(simpleMailMessage);
            return "Mail status sent Successfully...";
        }
        // Catch block to handle the exceptions
        catch (Exception e) {
            System.out.println(e);
            return "Error while Sending Mail status";

        }
    }

}

