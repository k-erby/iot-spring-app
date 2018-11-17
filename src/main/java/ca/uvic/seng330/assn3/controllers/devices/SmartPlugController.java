package ca.uvic.seng330.assn3.controllers.devices;

import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.devices.Device;
import ca.uvic.seng330.assn3.models.devices.SmartPlug;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.UUID;

@Controller
public class SmartPlugController {

    private Mediator hub;

    SmartPlugController(Mediator hub) {
        this.hub = hub;
    }

    @GetMapping("/hub/smartplug")
    public String smartplug(@RequestParam(name="id", required=true) String id, Model model) {

        Map<UUID, Device> devices = this.hub.getDevices();
        Device device = devices.get(UUID.fromString(id));
//        System.out.println(device.getDeviceType());
//        if (device.getDeviceType() != DeviceType.CAMERA) {
//            return "device_error";
//        }
        SmartPlug smartplug = (SmartPlug)device;

        // get smartplug details
        model.addAttribute("name", smartplug.toString());

        // get smartplug status
        String status;
        switch (smartplug.getStatus()) {
            case OFF: status = "The smartplug is off"; break;
            case ERROR: status = "The smartplug is having a bad day"; break;
            case NOT_AVAILABLE: status = "smartplug data is not available"; break;
            default: status = "The smartplug is operating normally";
        }
        model.addAttribute("status", status);

        return "smartplug";
    }

}