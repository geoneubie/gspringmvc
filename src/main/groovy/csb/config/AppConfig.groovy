package csb.config
import csb.aspect.TransformLogger
import csb.model.Staging
import csb.service.GeoJsonService
import csb.service.ITransformService
import csb.service.SubmitService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAsync
@EnableAspectJAutoProxy
@EnableAutoConfiguration
@EntityScan(basePackages = ["csb.model"] )
@EnableJpaRepositories(basePackages = ["csb.repos"] )
@EnableTransactionManagement
class AppConfig {

    private static final Logger logger =
            LoggerFactory.getLogger AppConfig.class

    private static String activeProfile

    private def config

    public AppConfig() {

        // Get active profile
        this.activeProfile = System.getProperty "spring.profiles.active"
        logger.debug "activeProfile=${activeProfile}"
        config = new ConfigSlurper("${activeProfile}").parse(
                new File("config/appConfig.groovy").toURI().toURL())
    }

    public static String getActiveProfile() {
        return this.activeProfile
    }

    @Bean
    public Staging staging() {

        logger.debug "Staging bean construction..."
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

        ITransformService gs = new GeoJsonService( )
        return gs

    }

    @Bean
    public TransformLogger tLogger() {

        return new TransformLogger()

    }

}
