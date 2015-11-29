package csb.config

import csb.dsmodelinput.*
import csb.service.ISubmitService
import csb.service.SubmitService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment

@Configuration
@EnableAutoConfiguration
@PropertySource("classpath:/application.properties")
public class AppConfig {

    @Autowired
    Environment env;

    @Bean
    public Staging staging() {

        // get active profile
        String activeProfile = System.getProperty("spring.profiles.active")
        String stagingDirKey = env.getProperty("${activeProfile}.staging.dir.key")
        String stagingDirVal = env.getProperty("${activeProfile}.staging.dir.value")
        println "activeProfile=${activeProfile}:${stagingDirKey}"

        Staging staging = new Staging()
        staging.setStagingDirs( stagingDirKey, stagingDirVal )
        return staging

    }


    @Bean
    public ISubmitService us() {

        SubmitService ss = new SubmitService(staging())
        return ss

    }

}
