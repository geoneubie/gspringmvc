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
@RequestMapping (value = "/fileupload") //define to level endpoint
public class FileUploadController {

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("shipname") String shipname, @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            def filename = "csb_" + UUID.randomUUID() + ".xyz"
            try {
                println "${shipname} : ${filename}"
                byte[] bytes = file.getBytes()
                def stream =
                        new BufferedOutputStream(new FileOutputStream(new File("/tmp/${name}")))
                stream.write(bytes)
                stream.close()
                return "You successfully uploaded ${filename}!!"
            } catch (Exception e) {
                return "You failed to upload ${name}" + e.getMessage()
            }
        } else {
            return "You failed to upload ${filename} because the file was empty."
        }
    }

}