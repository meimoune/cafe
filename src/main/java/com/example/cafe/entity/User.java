package com.example.cafe.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;


@NamedQuery(name = "User.findByEmail",query = "select u from User u where u.email=:email")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "users")

public class User implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String contactNumber;
    private String email;
    private String password;
    private String status;
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
   List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(role));
    return authorities;
    }
    @Override
    public String getUsername() {
        return this.email;   
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
     }
    @Override
    public boolean isAccountNonLocked() {
        return true;
      }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
     }
    @Override
    public boolean isEnabled() {
        return true;
      }
    

}
