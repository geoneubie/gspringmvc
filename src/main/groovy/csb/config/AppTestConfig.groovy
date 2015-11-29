package csb.config

import csb.dsmodelinput.IUserSubmission
import csb.dsmodelinput.Staging
import csb.dsmodelinput.StagingTest
import csb.dsmodelinput.UserSubmission

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
