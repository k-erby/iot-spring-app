package ca.uvic.seng330.assn3.controllers.devices;

import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.devices.Camera;
import ca.uvic.seng330.assn3.models.devices.Lightbulb;
import ca.uvic.seng330.assn3.models.devices.SmartPlug;
import ca.uvic.seng330.assn3.models.devices.Thermostat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeviceController {

    private Mediator hub;

    DeviceController(Mediator hub) {
        this.hub = hub;
    }

    @GetMapping("/new_device")
    public String new_device() {
        return "new_device";
    }

    @PostMapping("/register_device")
    public String register_device(@RequestParam(name="device", required=true) String device, Model model) {
        switch (device) {
            case "Camera":
                new Camera(this.hub);
                break;
            case "Lightbulb":
                new Lightbulb(this.hub);
                break;
            case "SmartPlug":
                new SmartPlug(this.hub);
                break;
            case "Thermostat":
                new Thermostat(this.hub);
                break;
            default:
                // TODO: write a statement here
        }
        return "registered";
    }
}
