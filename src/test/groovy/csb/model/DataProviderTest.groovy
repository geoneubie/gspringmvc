package csb.model

import org.junit.Test

/**
 * Created by dneufeld on 12/8/15.
 */
class DataProviderTest {

    @Test
    void dataProviderCreate() {
        DataProviderEntity dp = new DataProviderEntity()

        dp.name = "SEAID"
        dp.providerEmail = "support@sea-id.org"
        dp.processorEmail = "support@sea-id.org"
        dp.ownerEmail = "support@sea-id.org"

        assert dp.toString() == "0:SEAID:support@sea-id.org:support@sea-id.org:support@sea-id.org"
    }

}
