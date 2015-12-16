package csb.bootstrap
import csb.config.AppConfig
import csb.model.DataProviderEntity
import csb.repos.IDataProviderRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class DataProviderLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private IDataProviderRepository idpRepository

    private static final Logger logger =
            LoggerFactory.getLogger(DataProviderLoader.class)

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        logger.debug( AppConfig.activeProfile )
        if (AppConfig.activeProfile != "prod") {
            DataProviderEntity dpe

            dpe = new DataProviderEntity()
            dpe.name = "Sea-ID"
            dpe.providerEmail = "support@sea-id.org"
            dpe.providerUrl = "https://www.sea-id.org"
            dpe.processorEmail = "support@sea-id.org"
            dpe.ownerEmail = "support@sea-id.org"
            idpRepository.save(dpe)
            logger.debug( "Saved DataProvider - name: ${dpe.name}" )

            dpe = new DataProviderEntity()
            dpe.name = "LINBLAD"
            dpe.providerEmail = "support@expeditions.com"
            dpe.providerUrl = "http://www.expeditions.com"
            dpe.processorEmail = "support@expeditions.com"
            dpe.ownerEmail = "support@expeditions.com"
            idpRepository.save(dpe)
            logger.debug( "Saved DataProvider - name: ${dpe.name}" )

        }
    }
}