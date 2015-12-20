package csb.controller

import csb.service.AsyncGeoJsonServiceAdapter
import csb.service.ITransformService
import csb.service.ValidationService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

/**
 * Created by dneufeld on 9/24/15.
 */

@Controller
@ComponentScan( basePackages=[ "csb.config", "csb.bootstrap" ] )
@RestController
@RequestMapping ( value = "/fileupload" ) //toplevel controller endpoint
class FileUploadController {

    private static final Logger logger =
            LoggerFactory.getLogger( FileUploadController.class )

    @Autowired
    private ITransformService ss

    @Autowired
    private ITransformService gs

    @Autowired
    private AsyncGeoJsonServiceAdapter agsw

    @Secured( "USER" )
    @RequestMapping( value="/health", method=RequestMethod.GET )
    public Map health() throws Exception {

        Map hmResponse = [ "FileUploadController" : ["status" : "UP" ] ]
        return hmResponse

    }

    @Secured( "USER" )
    @RequestMapping( value="/upload", method=RequestMethod.POST )
    public String handleFileUpload( @RequestParam( "csbMetadataInput" ) String csbMetadataInput,
                                                  @RequestPart( "file" ) MultipartFile file ) {

        logger.debug( "Upload request made." )

        def userEntryMap = [:]

        ValidationService<String> vs = new ValidationService<String>( csbMetadataInput )

        // Validate incoming metadata fields has minimum required values
        if ( vs.validate() == false ) {
            logger.debug("JSON not valid, return...")
            return "You must complete all required fields."
        }

        userEntryMap << [ JSON: csbMetadataInput ]
        userEntryMap << [ FILE: file ]

        // Transfer incoming data to local disk
        Map ssResultMap = ss.transform( userEntryMap )
        if ( !ssResultMap.ERROR ) { //Continue processing the file
            logger.debug( "No errors continue processing..." )
            // Convert the data to geojson
            // This should be asynchronous processing
            def beforeTransform = Calendar.getInstance().getTimeInMillis()
            agsw.transform( gs, ssResultMap  )
            def afterAsyncTransformCompleted  = Calendar.getInstance().getTimeInMillis()
            def durationCompleted = afterAsyncTransformCompleted - beforeTransform
            logger.debug( "response to browser ${durationCompleted/1000} in secs" )

        }

        return ssResultMap.TRANSFORMED

    }

}