package ca.uvic.seng330.assn3.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import ca.uvic.seng330.assn3.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.User;
import ca.uvic.seng330.assn3.models.devices.Camera;
import ca.uvic.seng330.assn3.models.devices.Device;
import ca.uvic.seng330.assn3.models.devices.Lightbulb;
import ca.uvic.seng330.assn3.models.devices.SmartPlug;
import ca.uvic.seng330.assn3.models.devices.Thermostat;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HubController {
  private User currentUser = null;
  private Mediator hub;
  private UserRepository users;

  HubController(Mediator hub, UserRepository users) {
    this.hub = hub;
    this.users = users;
  }

  @GetMapping("/")
  public RedirectView homePage() {
    return new RedirectView("/hub");
  }

  @GetMapping("/hub")
  public String hub(Principal principal, Model model) {
    User currentUser = users.findByUsername(principal.getName()).get(0);

    if (!currentUser.signedIn()) currentUser.signIn();

    if (currentUser.getIsAdmin()) {

      Map<UUID, Device> devices = this.hub.getDevices();
      ArrayList<String> cameraIds = new ArrayList<String>();
      ArrayList<String> lightbulbIds = new ArrayList<String>();
      ArrayList<String> smartplugIds = new ArrayList<String>();
      ArrayList<String> thermostatIds = new ArrayList<String>();

      for (Device device : devices.values()) {
        if (device == null) continue;
        switch (device.getDeviceTypeEnum()) {
          case CAMERA:
            cameraIds.add(device.getIdentifier().toString());
            break;
          case LIGHTBULB:
            lightbulbIds.add(device.getIdentifier().toString());
            break;
          case SMARTPLUG:
            smartplugIds.add(device.getIdentifier().toString());
            break;
          case THERMOSTAT:
            thermostatIds.add(device.getIdentifier().toString());
            break;
          default:
            break;
        }
      }

      model.addAttribute("cameraDevices", cameraIds);
      model.addAttribute("lightbulbDevices", lightbulbIds);
      model.addAttribute("smartplugDevices", smartplugIds);
      model.addAttribute("thermostatDevices", thermostatIds);

      ArrayList<String> userNames = new ArrayList<String>();
      for (User user : this.users.findAll()) {
        userNames.add(user.getUsername());
      }
      model.addAttribute("users", userNames);

      return "hub_admin";
    } else {

      return "hub";
    }
  }

  @GetMapping("/unregister")
  public String unregister(@RequestParam(name = "id", required = true) String id, Model model) {
    Map<UUID, Device> devices = this.hub.getDevices();
    Device device = devices.get(UUID.fromString(id));
    try {
      model.addAttribute("message", "Unregistered successfully!");
      hub.unregisterDevice(currentUser, device);
      hub.unregister(device);
    } catch (HubRegistrationException e) {
      model.addAttribute(
          "message", "Unable to unregister because of following error: " + e.getMessage());
    }
    return "unregistered";
  }

  @GetMapping("/new_device")
  public String new_device() {
    return "new_device";
  }

  @PostMapping("/register_device")
  public String register_device(
      @RequestParam(name = "device", required = true) String device, Model model) {
    switch (device) {
      case "Camera":
        hub.registerDevice(currentUser, new Camera(this.hub));
        break;
      case "Lightbulb":
        hub.registerDevice(currentUser, new Lightbulb(this.hub));
        break;
      case "SmartPlug":
        hub.registerDevice(currentUser, new SmartPlug(this.hub));
        break;
      case "Thermostat":
        hub.registerDevice(currentUser, new Thermostat(this.hub));
        break;
      default:
        // TODO: write a statement here
    }
    return "registered";
  }

  @GetMapping("/hub/logout")
  public String logout() {

    currentUser.signOut();

    return "redirect:/login";
  }

  @GetMapping("/hub/shutdown")
  public String shutdown() {

    if (currentUser.getIsAdmin()) {
      hub.shutdown();
    } else {
      hub.shutdown(currentUser);
    }

    return "hub";
  }
}
