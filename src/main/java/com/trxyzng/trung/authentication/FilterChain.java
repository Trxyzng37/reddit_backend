//package com.trxyzng.trung.authentication;
//
//import com.trxyzng.trung.authentication.refreshtoken.RefreshTokenValidationFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
//
//@Configuration
//@EnableWebSecurity
//public class FilterChain {
//    //required authentication
//    @Bean
//    @Order(1)
//    public SecurityFilterChain needfilterChain(HttpSecurity http) throws Exception {
//        http
//                .securityMatcher(
//                        "ping",
//                        "save-comment", "update-comment-vote", "get-comment-status", "edit-comment", "delete-comment", "get-comments-by-uid",
//                        "/create-community",
//                        "edit-community",
//                        "join-community", "check-join-community",
//                        "set-allow-post",
//                        "/check-vote-post",
//                        "create-post",
//                        "delete-post",
//                        "edit-editor-post", "edit-img-post","edit-link-post","edit-video-post",
//                        "get-control-posts", "get-search-posts", "get-posts-by-uid", "/get-posts-by-uid-not-delete-not-allow",
//                        "/save-post", "/get-save-post", "/get-save-post-status",
//                        "show-post",
//                        "/vote-post",
//                        "set-recent-visit-community", "set-recent-visit-post", "get-recent-visit-post", "get-recent-visit-community",
//                        "get-subscribed-communities", "get-community-info-by-uid", "delete-community",
//                        "edit-user-info", "get-user-info-by-uid"
//                )
//                .addFilterBefore(new RefreshTokenValidationFilter(), RequestCacheAwareFilter.class)
//                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("*").permitAll();
//                })
//                .cors(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .anonymous(AbstractHttpConfigurer::disable)
//                .formLogin(AbstractHttpConfigurer::disable)
//                .logout(AbstractHttpConfigurer::disable)
//                .sessionManagement(s ->
//                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
//        ;
//        return http.build();
//    }
//
//    @Bean
//    @Order(2)
//    public SecurityFilterChain noneedFilterChain(HttpSecurity http) throws Exception {
//        http
//                .securityMatcher(
//                        "/is-email-exist", "/check-passcode", "/change-password","/resend-change-password-passcode", "/change-password",
//                        "get-comment", "get-comments",
//                        "/get-post", "/get-popular-posts", "get-community-posts", "get-home-posts",
//                        "/find-community", "get-community-info",
//                        "/find-user-profile", "/get-user-info", "/get-user-info-by-username",
//                        "check-username", "select-username"
//                )
//                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("*").permitAll();
//                })
//                .cors(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .anonymous(AbstractHttpConfigurer::disable)
//                .formLogin(AbstractHttpConfigurer::disable)
//                .logout(AbstractHttpConfigurer::disable)
//                .sessionManagement(s ->
//                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
//        ;
//        return http.build();
//    }
//}
