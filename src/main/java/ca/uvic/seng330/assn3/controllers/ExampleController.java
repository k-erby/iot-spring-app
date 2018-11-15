package ca.uvic.seng330.assn3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@RequestParam(name="name", required=false, defaultValue="World") String name

@RestController
public class ExampleController {

    @GetMapping("/hi")
    public String greeting() {
        return "Hi there!";
    }
}
