package ca.uvic.seng330.assn3.controllers.devices;

import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.devices.Device;
import ca.uvic.seng330.assn3.models.devices.Lightbulb;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.UUID;

@Controller
public class LightbulbController {

    private Mediator hub;

    LightbulbController(Mediator hub) {
        this.hub = hub;
    }

    @GetMapping("/hub/lightbulb")
    public String lightbulb(@RequestParam(name="id", required=true) String id, Model model) {

        Map<UUID, Device> devices = this.hub.getDevices();
        Device device = devices.get(UUID.fromString(id));
        Lightbulb lightbulb = (Lightbulb)device;

        // get lightbulb details
        model.addAttribute("name", lightbulb.toString());

        // get lightbulb status
        String status;
        switch (lightbulb.getStatus()) {
            case OFF: status = "Off"; break;
            case ON: status = "On"; break;
            case ERROR: status = "ERROR"; break;
            case NORMAL: status = "The lightbulb is operating normally."; break;
            default: status = "Status is unavailable.";
        }
        model.addAttribute("status", status);

        return "lightbulb";
    }

    @GetMapping("/hub/lightbulb/toggle")
    public String toggle(@RequestParam(name="id", required=true) String id, Model model) {
        Map<UUID, Device> devices = this.hub.getDevices();
        Device device = devices.get(UUID.fromString(id));
        ((Lightbulb) device).toggle();
        return lightbulb(id, model);
    }

}