package ca.uvic.seng330.assn3.controllers.devices;

import ca.uvic.seng330.assn3.exceptions.CameraFullException;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.devices.Camera;
import ca.uvic.seng330.assn3.models.devices.Device;
import ca.uvic.seng330.assn3.util.Status;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CameraController {

  private Mediator hub;

  CameraController(Mediator hub) {
    this.hub = hub;
  }

  /**
  * Creates the camera view and populates the model attributes in the html.
  * @param id : uuid of camera
  * @param model : model
  * @return the template rendered
  */
  @GetMapping("/hub/camera")
  public String camera(@RequestParam(name = "id", required = true) String id, Model model) {
    Map<UUID, Device> devices = this.hub.getDevices();
    Device device = devices.get(UUID.fromString(id));
    Camera camera = (Camera) device;

    // get camera details
    model.addAttribute("name", camera.getIdentifier());
    model.addAttribute("recording", camera.isRecording());
    model.addAttribute("memory", camera.getDiskSize());

    // get camera status
    model.addAttribute("status", camera.getState().stateView());
    model.addAttribute("isOn", camera.getState().getPowerState() == Status.ON);

    return "camera";
  }

  /**
  * Turns on the camera.
  * @param id : uuid of camera
  * @param model : model
  * @return goes to the above function, with updated attributes for the model
  */
  @GetMapping("/hub/camera/toggleOn")
  public String toggleOn(@RequestParam(name = "id", required = true) String id, Model model) {
    Map<UUID, Device> devices = this.hub.getDevices();
    Device device = devices.get(UUID.fromString(id));
    device.startup();

    model.addAttribute("notification", "Camera has been toggled on.");
    return camera(id, model);
  }

  @GetMapping("/hub/camera/toggleOff")
  public String toggleOff(@RequestParam(name = "id", required = true) String id, Model model) {
    Map<UUID, Device> devices = this.hub.getDevices();
    Device device = devices.get(UUID.fromString(id));
    device.shutdown();

    model.addAttribute("notification", "Camera has been toggled off.");
    return camera(id, model);
  }

  /**
  * Turns the recording on - this will cause a flashing red circle to appear under the image.
  * @param id : uuid of camera
  * @param model : model
  * @return goes to the above function, with updated attributes for the model
  */
  @GetMapping("/hub/camera/recordOn")
  public String recordOn(@RequestParam(name = "id", required = true) String id, Model model)
          throws CameraFullException {
    Map<UUID, Device> devices = this.hub.getDevices();
    Device device = devices.get(UUID.fromString(id));
    device.startup();
    try {
      ((Camera) device).record();
    } catch (CameraFullException e) {
      model.addAttribute("notification", "Camera is Full.");
    }
    model.addAttribute("notification", "Camera is recording.");
    return camera(id, model);
  }

  @GetMapping("/hub/camera/recordOff")
  public String recordOff(@RequestParam(name = "id", required = true) String id, Model model) {
    Map<UUID, Device> devices = this.hub.getDevices();
    Device device = devices.get(UUID.fromString(id));
    try {
      ((Camera) device).record();
    } catch (CameraFullException e) {
      model.addAttribute("notification", "Camera is Full.");
    }
    model.addAttribute("notification", "Camera has stopped recording.");
    return camera(id, model);
  }

  /**
  * Resets the memory on the camera.
  * @param id : uuid of camera
  * @param model : model
  * @return goes to the above function, with updated attributes for the model
  */
  @GetMapping("/hub/camera/reset")
  public String reset(@RequestParam(name = "id", required = true) String id, Model model) {
    Map<UUID, Device> devices = this.hub.getDevices();
    Device device = devices.get(UUID.fromString(id));
    ((Camera) device).resetMemory();

    model.addAttribute("notification", "Camera memory has been reset.");
    return camera(id, model);
  }
}

