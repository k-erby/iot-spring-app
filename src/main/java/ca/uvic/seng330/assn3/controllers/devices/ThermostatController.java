package ca.uvic.seng330.assn3.controllers.devices;

import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.devices.Device;
import ca.uvic.seng330.assn3.models.devices.Thermostat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.UUID;

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
//        System.out.println(device.getDeviceType());
//        if (device.getDeviceType() != DeviceType.CAMERA) {
//            return "device_error";
//        }
        Thermostat thermostat = (Thermostat) device;

        // get thermostat details
        model.addAttribute("name", thermostat.toString());

        // get thermostat status
        String status;
        switch (thermostat.getStatus()) {
            case OFF: status = "The thermostat is off"; break;
            case ERROR: status = "The thermostat is having a bad day"; break;
            case NOT_AVAILABLE: status = "thermostat data is not available"; break;
            default: status = "The thermostat is operating normally";
        }
        model.addAttribute("status", status);

        return "thermostat";
    }

}