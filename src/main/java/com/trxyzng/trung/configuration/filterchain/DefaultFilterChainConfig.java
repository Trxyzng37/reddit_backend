package com.trxyzng.trung.configuration.filterchain;

import com.trxyzng.trung.refresh_token_server.filter.RefreshTokenValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;

@Configuration
@EnableWebSecurity
@Order(2)
public class DefaultFilterChainConfig {
    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/signin/check-google-jwt-token")
            .addFilterBefore(new RefreshTokenValidationFilter(), RequestCacheAwareFilter.class)
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .anonymous(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .logout(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> {
//                auth.anyRequest().permitAll();
                auth.requestMatchers("signin/check-google-jwt-token").permitAll();
                auth.anyRequest().authenticated();
            })
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
        ;
        return http.build();
    }
}
