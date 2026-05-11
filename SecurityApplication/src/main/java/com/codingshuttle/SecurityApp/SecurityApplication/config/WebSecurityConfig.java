package com.codingshuttle.SecurityApp.SecurityApplication.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig
{
//    private final UserDetailsService userDetailsService; // Inject the interface
//    private final PasswordEncoder passwordEncoder;     // Now coming from AppConfig

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
        {
            httpSecurity
                    // 1. Disable CSRF for stateless REST APIs
                    .csrf(csrf -> csrf.disable())

                    // 2. Disable default Spring Security HTML form login and Basic Auth
                    .formLogin(form -> form.disable())
                    .httpBasic(basic -> basic.disable())

                    // 3. Set session management to stateless (for JWTs)
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )

                    // 4. Configure route authorization
                    .authorizeHttpRequests(auth -> auth
                            // Make sure your Controller is mapped to /auth for this to work!
                            .requestMatchers("/auth/**", "/error").permitAll()
                            .requestMatchers("/posts/**").hasRole("ADMIN")
                            .anyRequest().authenticated()
                    );

            return httpSecurity.build();
        }

        @Bean
        AuthenticationManager  authenticationManagerBean(AuthenticationConfiguration config) throws Exception
        {
            return config.getAuthenticationManager();
        }

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
