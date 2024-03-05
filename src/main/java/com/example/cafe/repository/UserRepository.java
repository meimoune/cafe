package com.example.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.cafe.entity.User;



public interface UserRepository extends JpaRepository<User,Integer>{
    User findByEmail( @Param("email") String email);
}
