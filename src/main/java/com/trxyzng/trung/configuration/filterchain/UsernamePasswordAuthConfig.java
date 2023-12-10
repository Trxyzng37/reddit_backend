package com.trxyzng.trung.configuration.filterchain;

//import com.trxyzng.trung.filter.UsernamePasswordFilter;
import com.trxyzng.trung.filter.UsernamePasswordFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
import org.springframework.security.web.session.ForceEagerSessionCreationFilter;

@Configuration
@EnableWebSecurity
public class UsernamePasswordAuthConfig {
    @Autowired
    private UsernamePasswordFilter usernamePasswordFilter;
    @Bean
    @Order(1)
    public SecurityFilterChain UsernamePasswordFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/signin/username-password")
                .addFilterBefore(usernamePasswordFilter, RequestCacheAwareFilter.class)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/signin/username-password").permitAll();
//                    auth.anyRequest().authenticated();
                })
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(s ->
                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
        ;
        return http.build();
    }
}
