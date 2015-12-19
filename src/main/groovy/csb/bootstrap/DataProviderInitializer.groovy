package csb.bootstrap
import csb.config.AppConfig
import csb.service.DataProviderService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class DataProviderInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger =
            LoggerFactory.getLogger(DataProviderInitializer.class)

    @Autowired
    private DataProviderService dpService

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (AppConfig.activeProfile != "prod") {
            logger.debug( "Bootstrapping initial DataProviderEntities..." )
            dpService.seed()
        }

    }

}