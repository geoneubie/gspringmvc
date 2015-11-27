package appconfig

import dsmodelinput.UserSubmission
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
public class UserSubmissionConfig {

    @Bean
    public UserSubmission us() {

        return new UserSubmission()

    }

}
