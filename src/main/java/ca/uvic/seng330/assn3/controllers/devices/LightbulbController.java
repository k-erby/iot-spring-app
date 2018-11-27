package ca.uvic.seng330.assn3.controllers.devices;

import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.devices.Device;
import ca.uvic.seng330.assn3.models.devices.Lightbulb;

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
        model.addAttribute("name", lightbulb.getIdentifier());

        // get lightbulb status
        model.addAttribute("status", lightbulb.getState().stateView());

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