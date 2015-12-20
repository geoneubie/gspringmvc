package csb.config

import csb.service.AsyncGeoJsonServiceAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

/**
 * Created by dneufeld on 12/10/15.
 */
@Configuration
@EnableAsync
class AsyncConfig {
    @Bean
    public AsyncGeoJsonServiceAdapter agsw() {

        AsyncGeoJsonServiceAdapter agsw = new AsyncGeoJsonServiceAdapter( )
        return agsw

    }

    @Bean(name="workExecutor")
    public TaskExecutor taskExecutor() {

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;

    }
}
