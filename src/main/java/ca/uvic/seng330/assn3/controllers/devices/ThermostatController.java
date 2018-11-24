package ca.uvic.seng330.assn3.controllers.devices;

import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.devices.Device;
import ca.uvic.seng330.assn3.models.devices.Thermostat;

@Controller
public class ThermostatController {

    private Mediator hub;

    ThermostatController(Mediator hub) {
        this.hub = hub;
    }

    @GetMapping("/hub/thermostat")
    public String thermostat(@RequestParam(name="id", required=true) String id, Model model) {
        Map<UUID, Device> devices = this.hub.getDevices();
        Device device = devices.get(UUID.fromString(id));
        Thermostat thermostat = (Thermostat) device;

        // get thermostat details
        model.addAttribute("name", thermostat.toString());
        model.addAttribute("temp", thermostat.getTemp().toString());

        // get thermostat status
        
        model.addAttribute("status", thermostat.getState().stateView());

        return "thermostat";
    }

}