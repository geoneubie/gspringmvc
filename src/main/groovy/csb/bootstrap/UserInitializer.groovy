package csb.bootstrap
import csb.config.AppConfig
import csb.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class UserInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger =
            LoggerFactory.getLogger(UserInitializer.class)

    @Autowired
    private UserService userService


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if ( AppConfig.activeProfile != "prod" ) {

            logger.debug( "Bootstrapping initial Users..." )
            userService.seed()

        }

    }

}