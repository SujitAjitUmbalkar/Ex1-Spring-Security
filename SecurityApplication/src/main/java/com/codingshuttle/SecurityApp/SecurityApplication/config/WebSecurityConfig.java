package com.codingshuttle.SecurityApp.SecurityApplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
                        .requestMatchers("/posts","/error","/auth","/**").permitAll()
                        .requestMatchers("/posts/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()) // any request must be authenticated
                .csrf(csrfConfig->csrfConfig.disable())
                .sessionManagement(sessionConfig->sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

//              .formLogin(Customizer.withDefaults()); // enables default login form for authentication

        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager  authenticationManagerBean(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }

//    @Bean           // way to creating inmemory users for testing purpose
//    UserDetailsService myInMemoryUserDetailsService()
//    {
//        UserDetails normalUser = User
//                .withUsername("sujit")
//                .password(passwordEncoder().encode("Sujit@123"))
//                .roles("USER")
//                .build();
//
//        UserDetails adminUser = User
//                .withUsername("Ajit")
//                .password(passwordEncoder().encode("Ajit@123"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(normalUser,adminUser);        // store in inmemoryuserdetailsservice
//    }

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
