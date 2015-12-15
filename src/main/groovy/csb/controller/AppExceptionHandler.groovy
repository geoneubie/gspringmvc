package csb.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

/**
 * Created by dneufeld on 12/3/15.
 */
@ControllerAdvice
class AppExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger( AppExceptionHandler.class )

    @ExceptionHandler( Exception.class )
    public @ResponseBody String exceptionHandler( Throwable e ) {

        def msg = "Unknown error trying to upload your file, please contact " +
                "the system administrator if the problem continues."
        logger.error( "handleFileUpload error", e )
        return msg;

    }

}


