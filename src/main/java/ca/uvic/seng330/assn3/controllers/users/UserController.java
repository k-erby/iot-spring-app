package ca.uvic.seng330.assn3.controllers.users;

import ca.uvic.seng330.assn3.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private User user;

    UserController(User user) {
        this.user = user;
    }

    @GetMapping("/new_user")
    public String new_user() {
        return "new_user";
    }

    @PostMapping("/register_user")
    public String register_user(
            @RequestParam(name="username", required=true) String username,
            @RequestParam(name="password", required=true) String password) {

        User user = new User(username, password);
        return "new_user_created";
    }
}
