package com.codingshuttle.SecurityApp.SecurityApplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig
{
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/posts").permitAll()      // /posts wiill be permitted to all
                        .anyRequest().authenticated()) // any request must be authenticated
                .formLogin(Customizer.withDefaults());      // removes login form if authenticated

        return httpSecurity.build();
    }

}
