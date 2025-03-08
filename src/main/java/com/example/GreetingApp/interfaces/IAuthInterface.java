package com.example.greetingapp.interfaces;

import com.example.greetingapp.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface IAuthInterface {
    String register(AuthUserDTO user);
    String login(LoginDTO user);
    AuthUserDTO forgetPassword(PassDTO pass, String email);
    String resetPassword(String email, String currentPass, String newPass);
}