package controller

/**
 * Created by dneufeld on 9/24/15.
 */

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.multipart.MultipartFile

@Controller
public class FileUploadController {

    @RequestMapping(value="/fileupload/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            def name = "csb_" + UUID.randomUUID() + ".xyz"
            try {
                println name
                byte[] bytes = file.getBytes()
                def stream =
                        new BufferedOutputStream(new FileOutputStream(new File("/tmp/" + name)))
                stream.write(bytes)
                stream.close()
                return "You successfully uploaded " + name + "!"
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage()
            }
        } else {
            return "You failed to upload " + name + " because the file was empty."
        }
    }

}