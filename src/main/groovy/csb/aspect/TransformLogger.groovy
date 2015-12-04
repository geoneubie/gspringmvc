package csb.aspect

/**
 * Created by dneufeld on 11/29/15.
 */

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.AfterThrowing

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.stereotype.Component

@Aspect
@Component
class TransformLogger {

    private static final Logger logger =
            LoggerFactory.getLogger( TransformLogger.class )

    @Before( "execution(* csb.service.ITransformService.transform(..)) and args(userEntries)" )
    public void startTransformLogging( Map userEntries ) {

        logger.debug( "Begin transform with ${userEntries}");

    }

    @AfterThrowing( pointcut = "execution(* csb.service.ITransformService.transform(..))", throwing = "e" )
    public void startTransformLogging(Throwable e) {

        logger.error( "Caught exception: ${e.message}");

    }

}

