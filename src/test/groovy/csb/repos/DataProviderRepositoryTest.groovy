package csb.repos

import csb.config.AppConfig
import csb.model.DataProviderEntity
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.Transactional

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
class DataProviderRepositoryTest {

    private static final Logger logger =
            LoggerFactory.getLogger( DataProviderRepositoryTest.class )

    @Autowired
    private IDataProviderRepository dpRepository

    @Test
    @Transactional
    public void saveDps( ) {

        dpRepository.save( new DataProviderEntity( "SEAID", "support@seaid.org", "https://www.sea-id.org","support@seaid.org", "support@seaid.org") )
        dpRepository.save( new DataProviderEntity( "LINBLAD", "support@expeditions.com", "http://www.expeditions.com","support@expeditions.com", "support@expeditions.com") )

        for (DataProviderEntity dpe : dpRepository.findAll()) {
            logger.debug( dpe.toString() )
        }
        DataProviderEntity dpe = dpRepository.findByName("SEAID")

        assert dpe.toString() == "3:SEAID:support@seaid.org:https://www.sea-id.org:support@seaid.org:support@seaid.org"

    }

}


