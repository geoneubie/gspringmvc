package controller

/**
 * Created by dneufeld on 9/24/15.
 */

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseBody

import javax.servlet.http.Part

@Controller
@RequestMapping (value = "/fileupload") //define to level endpoint
public class FileUploadController {

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("csbMetadataInput") String csbMetadataInput, @RequestPart("file") Part file){
        if (file.size > 0) {
            def filename = "csb_" + UUID.randomUUID() + ".xyz"
            try {
                println "${csbMetadataInput} : ${filename}"
                file.write "/tmp/${filename}"
                return "You successfully uploaded ${filename}!!"
            } catch (Exception e) {
                return "You failed to upload ${filename}" + e.getMessage
            }
        } else {
            return "You failed to upload ${filename} because the file was empty."
        }
    }

}