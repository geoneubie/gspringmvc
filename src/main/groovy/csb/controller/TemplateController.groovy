package csb.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.security.access.annotation.Secured
import java.security.Principal

/**
 * Created by dneufeld on 12/5/15.
 */
@Controller
@RequestMapping
class TemplateController {

    @RequestMapping( "/login" )
    String login(){

        return "login"

    }


    @Secured
    @RequestMapping( "/fileupload/index.html" )
    String fileUploadForm( ModelMap model, Principal principal ) {

        String name = principal.getName(); //get logged in username
        model.addAttribute("username", name);
        return "fileupload/index"

    }

}
