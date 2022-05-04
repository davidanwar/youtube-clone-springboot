package com.abs.youtubeclone.controller;

import com.abs.youtubeclone.model.User;
import com.abs.youtubeclone.service.UserService;
import org.apache.catalina.authenticator.SpnegoAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        userService.registerUser(jwt.getTokenValue());
        return "Register user successfull";
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createDummyUser(@RequestBody Map<String, String> user) {
        String id = user.get("id");
        String email  = user.get("emailAddress");
        User user1 = new User();
        user1.setId(id);
        user1.setEmailAddress(email);
        userService.createUser(user1);
        return "Sukses Membuat Dummy User";

    }
}
