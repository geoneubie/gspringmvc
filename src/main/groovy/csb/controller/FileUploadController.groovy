package csb.controller

/**
 * Created by dneufeld on 9/24/15.
 */

import csb.service.ISubmitService

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.Part

@Controller
@ComponentScan( basePackages=[ "csb.config" ] )
@RequestMapping ( value = "/fileupload" ) //toplevel controller endpoint
class FileUploadController {

    private static final Logger logger =
            LoggerFactory.getLogger( FileUploadController.class )

    @Autowired
    private ISubmitService ss

    @RequestMapping(value="/ping", method=RequestMethod.GET)
    public @ResponseBody String ping() {
        return "FileUploadController is alive."
    }

    @RequestMapping( value="/upload", method=RequestMethod.POST )
    public @ResponseBody String handleFileUpload( @RequestParam("csbMetadataInput") String csbMetadataInput,
                                                  @RequestPart("file") Part file ) {
        logger.debug "Upload request made."

        def userEntries = [:]
        String msg

        if ( ss.validateJSON( csbMetadataInput ) == false ) {
            logger.debug("JSON not valid, return...")
            msg = "You must complete all required fields."
            return msg
        }

        try {

            userEntries << [ JSON : csbMetadataInput ]
            userEntries << [ FILE : file]
            msg = (ss.transform( userEntries )).TRANSFORMED

        } catch (Exception e) {

            msg = "Unknown error trying to upload your file, please contact " +
                    "the system administrator if the problem continues."
            logger.error("handleFileUpload error", e)

        } finally {

            return msg

        }

    }

}