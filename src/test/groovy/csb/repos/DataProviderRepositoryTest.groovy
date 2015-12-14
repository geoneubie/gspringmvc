package csb.repos
import csb.config.RepositoryConfig
import csb.model.DataProviderEntity
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
/**
 * Created by dneufeld on 12/13/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RepositoryConfig.class)
//@SpringApplicationConfiguration(classes = [RepositoryConfiguration.class] )
class DataProviderRepositoryTest {

    private static final Logger logger =
            LoggerFactory.getLogger( DataProviderRepositoryTest.class )

    private IDataProviderRepository dpRepository

    @Autowired
    public void setIDataProviderRepository(IDataProviderRepository dpRepository) {
        this.dpRepository = dpRepository
    }

    @Test
    public void saveDps( ) {

        dpRepository.save( new DataProviderEntity( "SEAID", "support@seaid.org", "support@seaid.org", "support@seaid.org") )
        dpRepository.save( new DataProviderEntity( "LINBLAD", "support@expeditions.com", "support@expeditions.com", "support@expeditions.com") )

        for (DataProviderEntity dpe : dpRepository.findAll()) {
            logger.debug( dpe.toString() )
        }
        DataProviderEntity dpe = dpRepository.findByName("SEAID")

        assert dpe.toString() == "1:SEAID:support@seaid.org:support@seaid.org:support@seaid.org"
    }

}


