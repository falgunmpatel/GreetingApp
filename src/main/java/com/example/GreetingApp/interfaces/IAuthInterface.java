package com.example.greetingapp.interfaces;

import com.example.greetingapp.dto.AuthUserDTO;
import com.example.greetingapp.dto.LoginDTO;
import org.springframework.stereotype.Service;

@Service
public interface IAuthInterface {

    public String register(AuthUserDTO user);


    public String login(LoginDTO user);


}