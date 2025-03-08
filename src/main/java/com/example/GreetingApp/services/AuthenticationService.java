package com.example.greetingapp.services;

import com.example.greetingapp.dto.*;
import com.example.greetingapp.interfaces.IAuthInterface;
import com.example.greetingapp.models.AuthUser;
import com.example.greetingapp.repositories.UserRepository;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthInterface {

  private final UserRepository userRepository;
  EmailService emailService;
  JwtTokenService jwtTokenService;

  public AuthenticationService(
      UserRepository userRepository, EmailService emailService, JwtTokenService jwtTokenService) {
    this.userRepository = userRepository;
    this.emailService = emailService;
    this.jwtTokenService = jwtTokenService;
  }

  public String register(AuthUserDTO user) {

    List<AuthUser> l1 =
        userRepository.findAll().stream()
            .filter(authuser -> user.getEmail().equals(authuser.getEmail()))
            .toList();

    if (!l1.isEmpty()) {
      return "User already registered";
    }

    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
    String hashPass = bcrypt.encode(user.getPassword());

    AuthUser newUser =
        new AuthUser(
            user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), hashPass);

    newUser.setHashPass(hashPass);

    userRepository.save(newUser);

    emailService.sendEmail(
        user.getEmail(),
        "Your Account is Ready!",
        "UserName : "
            + user.getFirstName()
            + " "
            + user.getLastName()
            + "\nEmail : "
            + user.getEmail()
            + "\nYou are registered!\nBest Regards");

    return "user registered";
  }

  public String login(LoginDTO user) {

    List<AuthUser> l1 =
        userRepository.findAll().stream()
            .filter(authuser -> authuser.getEmail().equals(user.getEmail()))
            .toList();
    if (l1.isEmpty()) return "User not registered";

    AuthUser foundUser = l1.getFirst();

    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    if (!bcrypt.matches(user.getPassword(), foundUser.getHashPass())) return "Invalid password";

    String token = jwtTokenService.createToken(foundUser.getId());

    foundUser.setToken(token);

    userRepository.save(foundUser);

    return "user logged in" + "\ntoken : " + token;
  }

  public AuthUserDTO forgetPassword(PassDTO pass, String email) {

    AuthUser foundUser = userRepository.findByEmail(email);

    if (foundUser == null) throw new RuntimeException("user not registered!");

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    String hashedPassword = bCryptPasswordEncoder.encode(pass.getPassword());

    foundUser.setPassword(pass.getPassword());
    foundUser.setHashPass(hashedPassword);

    userRepository.save(foundUser);

    emailService.sendEmail(email, "Password Reset Status", "Your password has been reset");

    return new AuthUserDTO(
        foundUser.getFirstName(),
        foundUser.getLastName(),
        foundUser.getEmail(),
        foundUser.getPassword(),
        foundUser.getId());
  }
}