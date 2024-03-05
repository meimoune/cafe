package com.example.cafe.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.cafe.filter.JwtFilter;
import com.example.cafe.service.UserDetailsImpService;


@Configuration
public class SecurityConfig {
     private final UserDetailsImpService userDetailsImpService;
    private final JwtFilter  jwtFilter;
    
  
    public SecurityConfig(UserDetailsImpService userDetailsImpService,
            JwtFilter jwtFilter) {
        this.userDetailsImpService = userDetailsImpService;
        this.jwtFilter = jwtFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(req->req
        .requestMatchers("/user/login","/user/signup","/user/forgotPassword")
        .permitAll()
        .requestMatchers("/admin")
        .hasAnyAuthority("ADMIN")
        .requestMatchers("/user")
        .hasAnyAuthority("USER")
        . anyRequest()
        .authenticated())
        .userDetailsService(userDetailsImpService)
        .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
        .build();    
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
        return configuration.getAuthenticationManager();
    }

}

