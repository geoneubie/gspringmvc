package csb.model

import org.junit.Test

/**
 * Created by dneufeld on 12/8/15.
 */
class DataProviderTest {

    @Test
    void dataProviderCreate() {
        DataProvider dp = new DataProvider()

        dp.name = "SEAID"
        dp.providerEmail = "support@sea-id.org"
        dp.providerUrl = "https://www.sea-id.org"
        dp.processorEmail = "support@sea-id.org"
        dp.ownerEmail = "support@sea-id.org"

        assert dp.toString() == "SEAID:support@sea-id.org:https://www.sea-id.org:support@sea-id.org:support@sea-id.org"
    }

}
