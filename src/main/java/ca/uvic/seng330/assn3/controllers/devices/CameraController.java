package ca.uvic.seng330.assn3.controllers.devices;

import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.devices.Camera;
import ca.uvic.seng330.assn3.models.devices.Device;
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
        Camera camera = (Camera) device;

        // get camera details
        model.addAttribute("name", camera.toString());
        model.addAttribute("recording", camera.isRecording());

        // get camera status
        String status;
        switch (camera.getStatus()) {
            case OFF: status = "OFF"; break;
            case ON: status = "ON"; break;
            case ERROR: status = "ERROR"; break;
            case NORMAL: status = "The camera is operating normally."; break;
            default: status = "Status is unavailable.";
        }
        model.addAttribute("status", status);

        return "camera";
    }

}