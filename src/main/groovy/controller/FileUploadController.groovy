package controller

import dsmodelinput.IUserSubmission

/**
 * Created by dneufeld on 9/24/15.
 */

import dsmodelinput.UserSubmission
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
@ComponentScan( basePackageClasses=[ dsmodelinput.IUserSubmission ] )
@RequestMapping (value = "/fileupload") //define to level endpoint

public class FileUploadController {

    @Autowired
    private UserSubmission us

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("csbMetadataInput") String csbMetadataInput, @RequestPart("file") Part file){

        def userEntries = [:]
        userEntries << [ JSON : csbMetadataInput ]
        userEntries << [ FILE : file]
        def msg = us.transform( userEntries )
        return msg.TRANSFORMED

    }

}