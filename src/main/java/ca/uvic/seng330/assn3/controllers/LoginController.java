package ca.uvic.seng330.assn3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method=RequestMethod.GET)
    public String login(ModelMap map) {
        return "'/'";
    }

    @RequestMapping(value = "/logout", method=RequestMethod.GET)
    public String logout(ModelMap map) {
        return "logout";
    }

    @RequestMapping(value = "/login_error", method=RequestMethod.GET)
    public String loginError(ModelMap map) {
        return "login_error";
    }

}