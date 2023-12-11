package com.trxyzng.trung.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<UsernamePasswordFilter> myFilter() {
        FilterRegistrationBean<UsernamePasswordFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UsernamePasswordFilter());
        registrationBean.addUrlPatterns("/username-password");
        registrationBean.setOrder(1); // Set the order
        return registrationBean;
    }
}
