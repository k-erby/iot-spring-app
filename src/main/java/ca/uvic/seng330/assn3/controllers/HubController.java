package ca.uvic.seng330.assn3.controllers;

import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
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
        ArrayList<String> cameraIds = new ArrayList();
        ArrayList<String> lightbulbIds = new ArrayList();
        ArrayList<String> smartplugIds = new ArrayList();
        ArrayList<String> thermostatIds = new ArrayList();

        for (Device device : devices.values()) {
            switch (device.getDeviceTypeEnum()) {
                case CAMERA: cameraIds.add(device.getIdentifier().toString()); break;
                case LIGHTBULB: lightbulbIds.add(device.getIdentifier().toString()); break;
                case SMARTPLUG: smartplugIds.add(device.getIdentifier().toString()); break;
                case THERMOSTAT: thermostatIds.add(device.getIdentifier().toString()); break;
                default: break;
            }
        }

        model.addAttribute("cameraDevices", cameraIds);
        model.addAttribute("lightbulbDevices", lightbulbIds);
        model.addAttribute("smartplugDevices", smartplugIds);
        model.addAttribute("thermostatDevices", thermostatIds);

        return "hub";
    }

    @GetMapping("/unregister")
    public String unregister(@RequestParam(name="id", required=true) String id, Model model) {
        Map<UUID, Device> devices = this.hub.getDevices();
        Device device = devices.get(UUID.fromString(id));
        try {
            model.addAttribute("message", "Unregistered successfully!");
            hub.unregister(device);
        } catch (HubRegistrationException e) {
            model.addAttribute("message", "Device not unregistered with following error: " + e.getMessage());
        }
        return "unregistered";
    }

    @GetMapping("/register")
    public String register(@RequestParam(name="id", required=false) String id, Model model) {
        Map<UUID, Device> devices = this.hub.getDevices();
        Device device = devices.get(UUID.fromString(id));
        try {
            model.addAttribute("message", "Registered successfully!");
            hub.unregister(device);
        } catch (HubRegistrationException e) {
            model.addAttribute("message", "Registered with following error: " + e.getMessage());
        }
        return "registered";
    }
}