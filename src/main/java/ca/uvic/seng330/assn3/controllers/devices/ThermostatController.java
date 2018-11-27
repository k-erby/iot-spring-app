package ca.uvic.seng330.assn3.controllers.devices;

import java.util.Map;
import java.util.UUID;

import ca.uvic.seng330.assn3.util.Status;
import ca.uvic.seng330.assn3.util.Temperature;
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
        model.addAttribute("isOn", thermostat.getState().getPowerState() == Status.ON);

        return "thermostat";
    }

    @GetMapping("/hub/thermostat/toggleOn")
    public String toggleOn(@RequestParam(name="id", required=true) String id, Model model) {
        Map<UUID, Device> devices = this.hub.getDevices();
        Device device = devices.get(UUID.fromString(id));
        device.startup();
        return thermostat(id, model);
    }

    @GetMapping("/hub/thermostat/toggleOff")
    public String toggleOff(@RequestParam(name="id", required=true) String id, Model model) {
        Map<UUID, Device> devices = this.hub.getDevices();
        Device device = devices.get(UUID.fromString(id));
        device.shutdown();
        return thermostat(id, model);
    }

    @GetMapping("/hub/thermostat/changeUnit")
    public String changeUnit(@RequestParam(name="id", required=true) String id, Model model)
            throws Temperature.TemperatureOutofBoundsException
    {
        Map<UUID, Device> devices = this.hub.getDevices();
        Device device = devices.get(UUID.fromString(id));
        try {
            ((Thermostat) device).scaleSwitch();
        } catch (Temperature.TemperatureOutofBoundsException e) {
            System.out.println("Temperature is out of bounds.");
        }
        return thermostat(id, model);
    }

}