package com.example.Users.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService{
    @Autowired
    private JavaMailSender emailSender;

    public Boolean sendMail(String mail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("fromEmail@gmail.com");
        message.setText(mail);
        message.setTo("toEmail@gmail.com");
        message.setSubject("Testing");
        emailSender.send(message);
        return true;
    }
}
