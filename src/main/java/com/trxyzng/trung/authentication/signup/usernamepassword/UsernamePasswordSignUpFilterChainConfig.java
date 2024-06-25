package com.trxyzng.trung.authentication.signup.usernamepassword;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Order(3)
public class UsernamePasswordSignUpFilterChainConfig {
    @Bean
    public SecurityFilterChain SignUpFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/signup/username-password", "/resend-confirm-email-passcode", "/check-confirm-email-passcode")
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/signup/username-password", "/resend-confirm-email-passcode", "/check-confirm-email-passcode").permitAll();
                })
        ;
        return http.build();
    }
}
