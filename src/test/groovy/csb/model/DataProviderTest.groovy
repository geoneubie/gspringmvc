package csb.model

import org.junit.Test

/**
 * Created by dneufeld on 12/8/15.
 */
class DataProviderTest {

    @Test
    void dataProviderTest() {
        DataProvider dp = new DataProvider()
        dp.uid = "1"
        dp.name = "SEAID"
        dp.providerEmail = "support@sea-id.org"
        dp.processorEmail = "support@sea-id.org"
        dp.ownerEmail = "support@sea-id.org"

        assert dp.toString() == "1:SEAID:support@sea-id.org:support@sea-id.org:support@sea-id.org"
    }
}
