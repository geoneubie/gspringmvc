package csb.controller

/**
 * Created by dneufeld on 9/24/15.
 */

import csb.service.ITransformService

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

        if (ss.validate(csbMetadataInput) == false) {

            logger.debug("JSON not valid, return...")
            msg = "You must complete all required fields."
            return msg

        }

        userEntryMap << [JSON: csbMetadataInput]
        userEntryMap << [FILE: file]
        def resultMap = ss.transform( userEntryMap )
        if ( !resultMap.ERROR ) { //Continue processing the file
            logger.debug("No errors continue processing...")
            // This should be asynchronous processing
            gs.transform(resultMap)
        }
        msg = resultMap.TRANSFORMED

    }

}