package com.example.greetingapp.controllers;

import com.example.greetingapp.dto.*;
import com.example.greetingapp.interfaces.IAuthInterface;
import com.example.greetingapp.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    
    EmailService emailService;

    @Autowired
    IAuthInterface iAuthInterface;

    @PostMapping(path = "/register")
    public String register(@RequestBody AuthUserDTO user){
        return iAuthInterface.register(user);
    }

    @PostMapping(path ="/login")
    public String login(@RequestBody LoginDTO user){
        return iAuthInterface.login(user);
    }

    @PostMapping(path = "/sendMail")
    public String sendMail(@RequestBody MailDTO message){
        emailService.sendEmail(message.getTo(), message.getSubject(), message.getBody());
        return "Mail sent";
    }

    @PutMapping("/forgetPassword/{email}")
    public AuthUserDTO forgetPassword(@RequestBody PassDTO pass, @PathVariable String email){
        return iAuthInterface.forgetPassword(pass, email);
    }

    @PutMapping("/resetPassword/{email}")
    public String resetPassword(@PathVariable String email ,@RequestParam String currentPass, @RequestParam String newPass){
        return iAuthInterface.resetPassword(email, currentPass, newPass);
    }
}