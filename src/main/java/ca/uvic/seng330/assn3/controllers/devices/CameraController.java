package ca.uvic.seng330.assn3.controllers.devices;

import java.util.Map;
import java.util.UUID;

import ca.uvic.seng330.assn3.util.Status;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ca.uvic.seng330.assn3.exceptions.CameraFullException;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.devices.Camera;
import ca.uvic.seng330.assn3.models.devices.Device;

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

        model.addAttribute("notification", hub.getRecentNotification());
        
        // get camera details
        model.addAttribute("name", camera.getIdentifier());
        model.addAttribute("recording", camera.isRecording());

        // get camera status
        model.addAttribute("status", camera.getState().stateView());
        model.addAttribute("isOn", camera.getState().getPowerState() == Status.ON);

        return "camera";
    }

    @GetMapping("/hub/camera/toggleOn")
    public String toggleOn(@RequestParam(name="id", required=true) String id, Model model) {
        Map<UUID, Device> devices = this.hub.getDevices();
        Device device = devices.get(UUID.fromString(id));
        device.startup();
        return camera(id, model);
    }

    @GetMapping("/hub/camera/toggleOff")
    public String toggleOff(@RequestParam(name="id", required=true) String id, Model model) {
        Map<UUID, Device> devices = this.hub.getDevices();
        Device device = devices.get(UUID.fromString(id));
        device.shutdown();
        return camera(id, model);
    }

    @GetMapping("/hub/camera/recordOn")
    public String recordOn(@RequestParam(name="id", required=true) String id, Model model) throws CameraFullException {
        Map<UUID, Device> devices = this.hub.getDevices();
        Device device = devices.get(UUID.fromString(id));
        device.startup();
        try {
            ((Camera) device).record();
        } catch (CameraFullException e) {
          model.addAttribute("notification", "Camera is Full.");

        }
        return camera(id, model);
    }


    @GetMapping("/hub/camera/recordOff")
    public String recordOff(@RequestParam(name="id", required=true) String id, Model model) {
        Map<UUID, Device> devices = this.hub.getDevices();
        Device device = devices.get(UUID.fromString(id));
        try {
            ((Camera) device).record();
        } catch (CameraFullException e) {
            model.addAttribute("notification", "Camera is Full.");
        }
        return camera(id, model);
    }
    
    @GetMapping("/hub/camera/reset")
    public String reset(@RequestParam(name="id", required=true) String id, Model model) {
      Map<UUID, Device> devices = this.hub.getDevices();
      Device device = devices.get(UUID.fromString(id));
      ((Camera) device).resetMemory();;
      return camera(id, model);
  }
    
    @GetMapping("/hub/camera/notif")
    public String notif(@RequestParam(name="id", required=true) String id, Model model) {
      
      model.addAttribute("notification", "this is a test");
      
      return "redirect:/hub/camera";
      
    }
    
    

}

