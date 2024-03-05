package com.example.cafe.service;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cafe.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class UserDetailsImpService implements UserDetailsService{
    @Autowired
    UserRepository userRepository;

    private com.example.cafe.entity.User userDetail; 
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       log.info("insider loadUserByUsername {}", email);
        userDetail=userRepository.findByEmail(email);
        if (!Objects.isNull(userDetail)) {
            return new User(userDetail.getEmail(), userDetail.getPassword(),new ArrayList<>());
            
        }
        else{
            throw new UsernameNotFoundException("email does not exist.");
        }

    }

public com.example.cafe.entity.User getUserDetail(){
    return userDetail;
}

  
    


    
}
