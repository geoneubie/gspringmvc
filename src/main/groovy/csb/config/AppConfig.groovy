package csb.config

import csb.aspect.TransformLogger
import csb.dsmodelinput.*
import csb.service.ISubmitService
import csb.service.SubmitService

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

@Configuration
@EnableAutoConfiguration
@EnableAspectJAutoProxy
public class AppConfig {

    private static final Logger logger =
            LoggerFactory.getLogger AppConfig.class

    @Bean
    public Staging staging() {

        logger.debug "Staging bean construction..."

        // get active profile
        String activeProfile = System.getProperty "spring.profiles.active"
        def config = new ConfigSlurper("${activeProfile}").parse(
                new File("config/appConfig.groovy").toURI().toURL())

        logger.debug "activeProfile=${activeProfile}"

        Staging staging = new Staging()
        staging.setStagingDirs( config.staging.dir.map )
        return staging

    }


    @Bean
    public ISubmitService ss() {

        ISubmitService ss = new SubmitService( staging() )
        return ss

    }

    @Bean
    public TransformLogger tLogger() {

        return new TransformLogger()

    }
}
