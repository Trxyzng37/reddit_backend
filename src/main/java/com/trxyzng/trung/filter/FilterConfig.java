//package com.trxyzng.trung.filter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FilterConfig {
//    @Autowired
//    private UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;
//    @Bean
//    public FilterRegistrationBean<UsernamePasswordAuthenticationFilter> myFilter() {
//        FilterRegistrationBean<UsernamePasswordAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(usernamePasswordAuthenticationFilter);
//        registrationBean.addUrlPatterns("/signin/username-password");
//        registrationBean.setOrder(1); // Set the order
//        return registrationBean;
//    }
//}
