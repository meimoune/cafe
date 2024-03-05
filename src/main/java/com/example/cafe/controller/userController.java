package com.example.cafe.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cafe.service.AuthenticationService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    AuthenticationService  authenticationService;
@PostMapping("/signup")
public ResponseEntity<String> signup(@RequestBody(required = true) Map<String, String> requestMap) {
    try {
        return authenticationService.signup(requestMap);

    } catch (Exception exception) {
        exception.printStackTrace();
        
    }

    return new ResponseEntity<String>("SomeThing went wrong",HttpStatus.INTERNAL_SERVER_ERROR);

}

@PostMapping("/login")
public ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap) {
    try {
        return authenticationService.login(requestMap);

    } catch (Exception exception) {
        exception.printStackTrace();
        
    }

    return new ResponseEntity<String>("SomeThing went wrong",HttpStatus.INTERNAL_SERVER_ERROR);

}
    

}
