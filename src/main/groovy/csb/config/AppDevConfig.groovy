package csb.config

import csb.dsmodelinput.*
import csb.service.ISubmitService
import csb.service.SubmitService

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
    public ISubmitService us() {

        SubmitService ss = new SubmitService(staging())
        ss.setFoo("bar")
        return ss

    }

}
