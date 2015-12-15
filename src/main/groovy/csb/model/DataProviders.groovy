package csb.model

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
/**
 * Created by dneufeld on 12/4/15.
 */
@Component
class DataProviders {

    private static final Logger logger =
            LoggerFactory.getLogger DataProviders.class

    def hmProviders = new HashMap()

    public DataProviders( List dpsConfigList ) {

        dpsConfigList.each { p ->
            this.addProvider( "${p.name}", p)
        }

    }

    void addProvider( String name, Map dpConfigMap ) {

        logger.debug( "Adding from Map" )
        def dp = new DataProviderEntity()
        dp.name = "${dpConfigMap.name}"
        dp.providerEmail = "${dpConfigMap.providerEmail}"
        dp.processorEmail = "${dpConfigMap.processorEmail}"
        dp.ownerEmail = "${dpConfigMap.ownerEmail}"
        this.hmProviders.put(name, dp)

    }


    void addProvider( String name, DataProviderEntity dp ) {

        logger.debug( "Adding from DataProvider" )
        this.hmProviders.put(name, dp)

    }

    DataProviderEntity getProvider( String name ) {
        return this.hmProviders.get( name )
    }
}
