package com.trxyzng.trung.authentication.signin.google;

//import com.trxyzng.trung.authentication.signin.filter.GoogleSignInFilter;
//import com.trxyzng.trung.authentication.signin.filter.GoogleSignInFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
@Order(0)
public class GoogleSignInConfig {
    @Bean
    public SecurityFilterChain GoogleAuthenticationFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/signup/google", "/signin/google", "/oauth2/**", "/login/**")
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.anyRequest().authenticated();
                })
                .oauth2Login(Customizer.withDefaults())
        ;
        return http.build();
    }
}
