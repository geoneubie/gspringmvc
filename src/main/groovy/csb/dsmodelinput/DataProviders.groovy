package csb.dsmodelinput

import org.springframework.stereotype.Component

/**
 * Created by dneufeld on 12/4/15.
 */
@Component
class DataProviders {

    def hmProviders = new HashMap<String, DataProvider>()

    void addProvider( String name, Map dpConfigMap ) {
        def dp = new DataProvider()
        dp.uid = dpConfigMap.uid
        dp.name = dpConfigMap.name
        dp.providerEmail = dpConfigMap.providerEmail
        dp.processorEmail = dpConfigMap.processorEmail
        dp.ownerEmail = dpConfigMap.ownerEmail
        this.hmProviders << [ name : dp ]
    }

    DataProvider getProvider( String name ) {
        return this.hmProviders.name
    }
}
