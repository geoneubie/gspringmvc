package appconfig

import dsmodelinput.*
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@EnableAutoConfiguration
@Profile("dev")

public class AppDevConfig {

    @Bean
    public Staging staging() {

        Staging staging = new StagingDevelopment()
        return staging

    }


    @Bean
    public IUserSubmission us() {

        return new UserSubmission(staging())

    }

}
