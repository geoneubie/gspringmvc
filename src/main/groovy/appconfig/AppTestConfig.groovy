package appconfig

import dsmodelinput.IUserSubmission
import dsmodelinput.Staging
import dsmodelinput.StagingDevelopment
import dsmodelinput.StagingTest
import dsmodelinput.UserSubmission

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@EnableAutoConfiguration
@Profile("test")
public class AppTestConfig {

    @Bean
    public Staging staging() {

        Staging staging = new StagingTest()
        return staging

    }

    @Bean
    public IUserSubmission us() {

        return new UserSubmission(staging())

    }

}
