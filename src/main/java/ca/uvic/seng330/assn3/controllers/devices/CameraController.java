package ca.uvic.seng330.assn3.controllers.devices;

import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.devices.Camera;
import ca.uvic.seng330.assn3.models.devices.Device;
import ca.uvic.seng330.assn3.util.DeviceType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.UUID;

@Controller
public class CameraController {

    private Mediator hub;

    CameraController(Mediator hub) {
        this.hub = hub;
    }

    @GetMapping("/hub/camera")
    public String camera(@RequestParam(name="id", required=true) String id, Model model) {

        Map<UUID, Device> devices = this.hub.getDevices();
        Device device = devices.get(UUID.fromString(id));
//        System.out.println(device.getDeviceType());
//        if (device.getDeviceType() != DeviceType.CAMERA) {
//            return "device_error";
//        }
        Camera camera = (Camera)device;

        // get camera details
        model.addAttribute("name", camera.toString());

        // get camera status
        String status;
        switch (camera.getStatus()) {
            case OFF: status = "The camera is off"; break;
            case ERROR: status = "The camera is having a bad day"; break;
            case NOT_AVAILABLE: status = "camera data is not available"; break;
            default: status = "The camera is operating normally";
        }
        model.addAttribute("status", status);

        return "camera";
    }

}