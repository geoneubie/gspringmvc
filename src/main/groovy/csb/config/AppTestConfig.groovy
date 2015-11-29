package csb.config

import csb.service.ISubmitService
import csb.dsmodelinput.Staging
import csb.dsmodelinput.StagingTest
import csb.service.SubmitService

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
    public ISubmitService us() {

        SubmitService ss = new SubmitService(staging())
        ss.setFoo("bar")
        return ss

    }


}
