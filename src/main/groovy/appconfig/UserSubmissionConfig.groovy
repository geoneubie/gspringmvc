package appconfig

import dsmodelinput.IUserSubmission
import dsmodelinput.UserSubmission
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan( basePackageClasses=[ IUserSubmission.class ] )
public class UserSubmissionConfig {

    @Bean
    public UserSubmission us() {

        def hmStagingDirs = [ : ]
        //Future pull from config server
        hmStagingDirs << [ CSBFILES : "/tmp" ]
        def us = new UserSubmission()
        us.setStagingDirs( hmStagingDirs )
        return us

    }

}
