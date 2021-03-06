package ca.uvic.seng330.assn3.controllers.devices;

import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.devices.Device;
import ca.uvic.seng330.assn3.models.devices.SmartPlug;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SmartPlugController {

  private Mediator hub;

  SmartPlugController(Mediator hub) {
    this.hub = hub;
  }

  /**
   * Basic constructor view for smartplug.
   * @param id : uuid of smartplug
   * @param model : model
   * @return the template for smartplug
   */
  @GetMapping("/hub/smartplug")
  public String smartplug(@RequestParam(name = "id", required = true) String id, Model model) {
    Map<UUID, Device> devices = this.hub.getDevices();
    Device device = devices.get(UUID.fromString(id));
    SmartPlug smartplug = (SmartPlug)device;

    // get smartplug details
    model.addAttribute("name", smartplug.getIdentifier());

    // get smartplug status
    model.addAttribute("status", smartplug.getState().stateView());

    return "smartplug";
  }

  @GetMapping("/hub/smartplug/toggle")
  public String toggle(@RequestParam(name = "id", required = true) String id, Model model) {
    Map<UUID, Device> devices = this.hub.getDevices();
    Device device = devices.get(UUID.fromString(id));
    device.toggle();

    model.addAttribute("notification", "Smartplug status toggled.");
    return smartplug(id, model);
  }
}
