package ca.uvic.seng330.assn3.controllers;

import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.devices.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Controller
public class NewDeviceController {

    private Mediator hub;

    NewDeviceController(Mediator hub) {
        this.hub = hub;
    }

    @GetMapping("/hub/new_device")
    public String hub(@RequestParam(name="name", required=false, defaultValue="Hub") String name, Model model) {

        return "new_devicez";
    }

    // THIS DOESN'T WORK
//    @PostMapping("/hub/new_device")
//    public Device newDevice(@RequestBody Device newDevice) {
//        this.hub.register(newDevice);
//        return "new_device_created";
//    }

}