package csb.controller

import csb.service.ITransformService
import csb.service.ValidationService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Controller

/**
 * Created by dneufeld on 9/24/15.
 */
import org.springframework.web.bind.annotation.*

import javax.servlet.http.Part

@Controller
@ComponentScan( basePackages=[ "csb.config" ] )
@RequestMapping ( value = "/fileupload" ) //toplevel controller endpoint
class FileUploadController {

    private static final Logger logger =
            LoggerFactory.getLogger( FileUploadController.class )

    @Autowired
    private ITransformService ss

    @Autowired
    private ITransformService gs

    @RequestMapping(value="/ping", method=RequestMethod.GET)
    public @ResponseBody String ping() throws Exception {
        return "FileUploadController is alive."
    }

    @RequestMapping( value="/upload", method=RequestMethod.POST )
    public @ResponseBody String handleFileUpload( @RequestParam("csbMetadataInput") String csbMetadataInput,
                                                  @RequestPart("file") Part file ) {
        logger.debug "Upload request made."

        def userEntryMap = [:]
        String msg
        ValidationService<String> vs = new ValidationService<String>( csbMetadataInput )

        // Validate incoming metadata fields has minimum required values
        if (vs.validate() == false) {

            logger.debug("JSON not valid, return...")
            msg = "You must complete all required fields."
            return msg

        }

        userEntryMap << [JSON: csbMetadataInput]
        userEntryMap << [FILE: file]

        // Transfer incoming data to local disk
        def resultMap = ss.transform( userEntryMap )
        if ( !resultMap.ERROR ) { //Continue processing the file
            logger.debug("No errors continue processing...")
            // Convert the data to geojson
            // This should be asynchronous processing
            gs.transform(resultMap)
        }
        msg = resultMap.TRANSFORMED

    }

}