package lk.softvil.assignment.eventm.config;


import lk.softvil.assignment.eventm.filters.RateLimitingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.cache.CacheManager;

@Configuration
public class RateLimitingConfig {

    @Bean
    public FilterRegistrationBean<RateLimitingFilter> rateLimitingFilter(CacheManager cacheManager) {
        FilterRegistrationBean<RateLimitingFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RateLimitingFilter(cacheManager));
        registration.addUrlPatterns("/api/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registration;
    }
}