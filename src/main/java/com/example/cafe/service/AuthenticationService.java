package com.example.cafe.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cafe.entity.User;
import com.example.cafe.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthenticationService {
@Autowired
UserRepository userRepository;
 @Autowired
 PasswordEncoder passwordEncoder;
  @Autowired
 JwtService jwtService;
@Autowired
AuthenticationManager authenticationManager;
@Autowired
UserDetailsImpService userDetailsImpService;

    public ResponseEntity<String> signup(Map<String, String> requestMap) {
log.info("inside signup{}", requestMap);
try {
    if (validateSignupMap(requestMap)) {
        User user=userRepository.findByEmail(requestMap.get("email"));
        if (user == null) {
            
            userRepository.save(getUserFromMap(requestMap));
            return new ResponseEntity<String>("Seccufuly signup",HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Email already exist",HttpStatus.BAD_REQUEST);
    
        }
    } 
        
        
        else {
             return new ResponseEntity<String>("invalide data",HttpStatus.BAD_REQUEST);
     }
    
} catch (Exception exception) {
   exception.printStackTrace();
}

return new ResponseEntity<String>("something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
  
}
        
    private boolean validateSignupMap(Map<String, String> requestMap) {
       if (requestMap.containsKey("name")&& requestMap.containsKey("contactNumber")
       && requestMap.containsKey("email") && requestMap.containsKey("password") 
     ) {
        return true;
       } 
       return false;
         
    }

    private User getUserFromMap(Map<String,String> requestMap ){
        User user=new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(passwordEncoder.encode(requestMap.get("password")));
        user.setStatus("false");
        user.setRole("USER");
        return user;
    }

    public ResponseEntity<String> login(Map<String,String> requestMap){
log.info("inside login");
try {
    Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password"))
    );
    if (authentication.isAuthenticated()) {
        User user=userRepository.findByEmail(requestMap.get("email"));
        if (userDetailsImpService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
           return new ResponseEntity<String>("token : "+jwtService.generateToken(user),HttpStatus.OK);
            
        } else {
            return new ResponseEntity<String>("wait for admin approval",HttpStatus.BAD_REQUEST);
           
        }
    }
} catch (Exception exception) {
   log.error("{}", exception);

    }
    return new ResponseEntity<String>("Bad credentials",HttpStatus.BAD_REQUEST);
           
}







}