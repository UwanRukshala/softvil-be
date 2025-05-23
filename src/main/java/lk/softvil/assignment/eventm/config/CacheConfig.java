package lk.softvil.assignment.eventm.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.jcache.spi.CaffeineCachingProvider;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {


    @Bean
    public CacheManager cacheManager() {
        CaffeineCachingProvider provider = new CaffeineCachingProvider();
        CacheManager cacheManager = provider.getCacheManager(
                provider.getDefaultURI(),
                provider.getDefaultClassLoader()
        );

        // Configure rate limit cache
        cacheManager.createCache("rate-limit-buckets",
                new MutableConfiguration<>()
                        .setStatisticsEnabled(true)
                        .setManagementEnabled(true)
        );

        return cacheManager;
    }
}