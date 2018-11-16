package ca.uvic.seng330.assn3.controllers;

import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.devices.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Controller
public class HubController {

    private Mediator hub;

    HubController (Mediator hub) {
        this.hub = hub;
    }

    @GetMapping("/hub")
    public String hub(@RequestParam(name="name", required=false, defaultValue="Hub") String name, Model model) {

        Map<UUID, Device> devices = this.hub.getDevices();
        ArrayList<String> deviceIds = new ArrayList();

        for (Device device : devices.values()) {
            deviceIds.add(device.getIdentifier().toString());
        }

        model.addAttribute("devices", deviceIds);

        return "hub";
    }

}