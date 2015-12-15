package csb.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class AsyncGeoJsonServiceWrapper {

    private static final Logger logger =
            LoggerFactory.getLogger( AsyncGeoJsonServiceWrapper.class )

    //Async implementation
    @Async( "workExecutor" )
    void transform( ITransformService gs, Map entries ) {
        def start = Calendar.getInstance().getTimeInMillis()
        gs.transform( entries )
        def end = Calendar.getInstance().getTimeInMillis()
        def duration = end - start
        logger.debug( "Work completed in ${duration/1000} secs" )
    }

}
