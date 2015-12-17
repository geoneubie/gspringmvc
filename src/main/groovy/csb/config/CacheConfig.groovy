package csb.config
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    public CacheManager cacheManager() {

        // In a larger data context we'll want to switch to a REAL cache
        // While the number of data providers is low this is fine.
        return new ConcurrentMapCacheManager();

    }

}

