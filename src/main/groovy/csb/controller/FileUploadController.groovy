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
@RequestMapping (value = "/fileupload") //toplevel controller endpoint
public class FileUploadController {

    private static final Logger logger =
            LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private ISubmitService ss

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("csbMetadataInput") String csbMetadataInput, @RequestPart("file") Part file){
        logger.debug "Upload request made."

        def userEntries = [:]
        userEntries << [ JSON : csbMetadataInput ]
        userEntries << [ FILE : file]
        def msg = ss.transform( userEntries )
        return msg.TRANSFORMED

    }

}