package ca.uvic.seng330.assn3.controllers.users;

import ca.uvic.seng330.assn3.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private User user;

    UserController(User user) {
        this.user = user;
    }

    @RequestMapping(value = "/new_user", method=RequestMethod.GET)
    public String new_user() {
        return "new_user";
    }
}
