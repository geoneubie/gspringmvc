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

    private IDataProviderRepository idpRepository

    private static final Logger logger =
            LoggerFactory.getLogger(DataProviderLoader.class)

    @Autowired
    public void setDataProviderRepository(IDataProviderRepository idpRepository) {
        this.idpRepository = idpRepository
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        logger.debug( AppConfig.activeProfile )
        if (AppConfig.activeProfile != "prod") {
            DataProviderEntity dpe

            dpe = new DataProviderEntity()
            dpe.name = "SEAID"
            dpe.providerEmail = "support@sea-id.org"
            dpe.processorEmail = "support@sea-id.org"
            dpe.ownerEmail = "support@sea-id.org"
            idpRepository.save(dpe)
            logger.debug( "Saved DataProvider - name: ${dpe.name}" )

            dpe = new DataProviderEntity()
            dpe.name = "LINBLAD"
            dpe.providerEmail = "support@expeditions.com"
            dpe.processorEmail = "support@expeditions.com"
            dpe.ownerEmail = "support@expeditions.com"
            idpRepository.save(dpe)
            logger.debug( "Saved DataProvider - name: ${dpe.name}" )

        }
    }
}