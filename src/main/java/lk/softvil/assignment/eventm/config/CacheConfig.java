package lk.softvil.assignment.eventm.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.cache.CacheManager;
import javax.cache.Caching;
import java.util.concurrent.TimeUnit;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

import org.springframework.cache.jcache.JCacheCacheManager;

@Configuration
@EnableCaching
public class CacheConfig {


    @Bean
    public CacheManager jCacheManager() {
        javax.cache.spi.CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();

        cacheManager.createCache("rate-limit-buckets",
                new MutableConfiguration<>()
                        .setStoreByValue(false)
                        .setStatisticsEnabled(true));

        cacheManager.createCache("otp-cache",
                new MutableConfiguration<>()
                        .setStoreByValue(false)
                        .setStatisticsEnabled(true)
                        .setExpiryPolicyFactory(
                                CreatedExpiryPolicy.factoryOf(
                                        new Duration(TimeUnit.MINUTES, 2)
                                )
                        ));

        cacheManager.createCache("upcomingEvents",
                new MutableConfiguration<>()
                        .setStoreByValue(false)
                        .setStatisticsEnabled(true)
                        .setExpiryPolicyFactory(
                                CreatedExpiryPolicy.factoryOf(
                                        new Duration(TimeUnit.MINUTES, 10) // set TTL as needed
                                )
                        ));

        return cacheManager;
    }

    @Bean
    public org.springframework.cache.CacheManager springCacheManager(CacheManager jCacheManager) {
        return new JCacheCacheManager(jCacheManager);
    }
}