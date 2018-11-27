package ca.uvic.seng330.assn3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(name="error", required=false) String error,
                        @RequestParam(name="logout", required=false) String logout, Model model) {
        return "login";
    }

    @RequestMapping(value = "/logout", method= GET)
    public String logout(ModelMap map) {
        return "logout";
    }

    @RequestMapping(value = "/login_error", method= GET)
    public String loginError(ModelMap map) {
        return "login_error";
    }

}