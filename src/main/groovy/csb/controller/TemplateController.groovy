package csb.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Created by dneufeld on 12/5/15.
 */
@Controller
@RequestMapping
class TemplateController {

    @RequestMapping("/login")
    String login(){
        return "login"
    }

}
