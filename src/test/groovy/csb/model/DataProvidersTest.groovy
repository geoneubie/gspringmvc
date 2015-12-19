package csb.model

import csb.config.AppConfig
import csb.repos.IDataProviderRepository
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
class DataProvidersTest {

    private static final Logger logger =
            LoggerFactory.getLogger( DataProvidersTest.class )

    @Autowired
    private IDataProviderRepository idpRepository

    @Test
    void dataProvidersCreate() {
        DataProvider dp = new DataProvider()

        dp.name = "SEAID"
        dp.providerEmail = "support@sea-id.org"
        dp.providerUrl = "https://www.sea-id.org"
        dp.processorEmail = "support@sea-id.org"
        dp.ownerEmail = "support@sea-id.org"

        DataProviders dps = new DataProviders()
        dps.addProvider("SEAID", dp)
        assert dps.getProvider("SEAID").toString() == "0:SEAID:support@sea-id.org:https://www.sea-id.org:support@sea-id.org:support@sea-id.org"
    }

    @Test
    @Transactional
    void dataProvidersDb() {

        idpRepository.save( new DataProvider( "DP1", "support@dp1.org", "https://www.dp1.org", "support@dp1.org", "support@dp1.org") )
        idpRepository.save( new DataProvider( "DP2", "support@dp2.com", "https://www.dp2.com", "support@dp2.com", "support@dp2.com") )

        for (DataProvider dpe : idpRepository.findAll()) {
            logger.debug( dpe.toString() )
        }
        DataProvider dpe = idpRepository.findByName("DP1")
        DataProviders dps = new DataProviders()
        dps.addProvider( dpe.name, dpe)

        assert dpe.toString() == "1:DP1:support@dp1.org:https://www.dp1.org:support@dp1.org:support@dp1.org"
        assert dps.getHmProviders().size() == 1

    }
}
