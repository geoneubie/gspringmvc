package csb.config
import csb.aspect.TransformLogger
import csb.model.DataProviders
import csb.model.Staging
import csb.service.GeoJsonService
import csb.service.ITransformService
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
class AppConfig {

    private static final Logger logger =
            LoggerFactory.getLogger AppConfig.class

    private String activeProfile

    public AppConfig() {

        // Get active profile
        this.activeProfile = System.getProperty "spring.profiles.active"
        logger.debug "activeProfile=${activeProfile}"

    }

    @Bean
    public DataProviders dps() {

        logger.debug "DataProviders bean construction..."

        def config = new ConfigSlurper("${activeProfile}").parse(
                new File("config/appConfig.groovy").toURI().toURL())

        DataProviders dps = new DataProviders( config.data.providers )

        return dps

    }

    @Bean
    public Staging staging() {

        logger.debug "Staging bean construction..."

        // get active profile
        String activeProfile = System.getProperty "spring.profiles.active"
        def config = new ConfigSlurper("${activeProfile}").parse(
                new File("config/appConfig.groovy").toURI().toURL())
        
        Staging staging = new Staging( config.staging.dir.map )
        return staging

    }

    @Bean
    public ITransformService ss() {

        ITransformService ss = new SubmitService( staging() )
        return ss

    }

    @Bean
    public ITransformService gs() {

        ITransformService gs = new GeoJsonService( staging(), dps() )
        return gs

    }

    @Bean
    public TransformLogger tLogger() {

        return new TransformLogger()

    }

}
