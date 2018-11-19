package ca.uvic.seng330.assn3.controllers.users;

import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.User;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
	
	private Map<String, User>  users;
    UserController (Map<String, User>  users) {
        this.users = users;
    }
    @GetMapping("/new_user")
    public String new_user() {
        return "new_user";
    }

    @PostMapping("/register_user")
    public String register_user(
            @RequestParam(name="username", required=true) String username,
            @RequestParam(name="password", required=true) String password, Model model) {

        User user = new User(username, password);
        this.users.put(username, user);
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        return "new_user_created";
    }
}
