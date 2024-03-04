//package com.trxyzng.trung.authentication.shared;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Config;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Config
//@EnableWebSecurity
//@Order(30)
//public class DefaultFilterChainConfig {
//    @Bean
//    public SecurityFilterChain dddFilterChain(HttpSecurity http) throws Exception {
//        http
////            .securityMatcher("")
//            .cors(Customizer.withDefaults())
//            .csrf(AbstractHttpConfigurer::disable)
//            .httpBasic(AbstractHttpConfigurer::disable)
//            .anonymous(AbstractHttpConfigurer::disable)
//            .formLogin(AbstractHttpConfigurer::disable)
//            .logout(AbstractHttpConfigurer::disable)
//            .authorizeHttpRequests(auth -> {
//                auth.anyRequest().permitAll();
//            })
//            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
//        ;
//        return http.build();
//    }
//}
