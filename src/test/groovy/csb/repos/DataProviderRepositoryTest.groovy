package csb.repos
import csb.config.AppConfig
import csb.model.DataProvider
import csb.service.DataProviderService
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
class DataProviderRepositoryTest {

    private static final Logger logger =
            LoggerFactory.getLogger( DataProviderRepositoryTest.class )

    @Autowired
    private DataProviderService dpService

    @Test
    public void saveDps() {

        def dpe
        dpe = new DataProvider( "SEAID", "support@seaid.org", "https://www.sea-id.org","support@seaid.org", "support@seaid.org" )
        dpService.save( dpe )

        dpe = new DataProvider( "LINBLAD", "support@expeditions.com", "http://www.expeditions.com","support@expeditions.com", "support@expeditions.com" )
        dpService.save( dpe )

        dpService.findAll().each { p ->
            logger.debug( p.toString() )
        }

        dpe = dpService.distinctFindByName( "SEAID" )

        assert dpe.toString() == "SEAID:support@seaid.org:https://www.sea-id.org:support@seaid.org:support@seaid.org"

    }

}


