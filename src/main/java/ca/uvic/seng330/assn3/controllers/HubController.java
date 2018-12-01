package ca.uvic.seng330.assn3.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.models.Hub;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.User;
import ca.uvic.seng330.assn3.models.devices.Device;
import ca.uvic.seng330.assn3.repository.UserRepository;

@Controller
public class HubController {
	private User currentUser = null;
    private Hub hub;
    private UserRepository  users;

    HubController (Mediator hub, UserRepository users) {
        this.hub = (Hub) hub;
        this.users = users;
    }

    @GetMapping("/")
    public RedirectView homePage() {
        return new RedirectView("/hub");
    }

    @GetMapping("/hub")
    public String hub(Principal principal, Model model) {
        currentUser = users.findByUsername(principal.getName()).get(0);
        
        if(!hub.getInstance().getUsers().contains(currentUser)) hub.registerUser(hub.getInstance(), currentUser);
        if(currentUser.getIsAdmin()) hub.getInstance().setAdmin(currentUser);
        if(!currentUser.signedIn()) currentUser.signIn();

        Map<UUID, Device> devices = this.hub.getDevices();
        ArrayList<String> cameraIds = new ArrayList<String>();
        ArrayList<String> lightbulbIds = new ArrayList<String>();
        ArrayList<String> smartplugIds = new ArrayList<String>();
        ArrayList<String> thermostatIds = new ArrayList<String>();

        for (Device device : devices.values()) {
            if (device == null) continue;
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

        if(currentUser.getIsAdmin()) {
	        ArrayList<String> userNames = new ArrayList<String>();
	        for(User user : this.users.findAll()){
	        	userNames.add(user.getUsername());
	        }
	        model.addAttribute("users", userNames);
	        
	        return "hub_admin";
    	} else {
    		return "hub";
    	}
    }

    @GetMapping("/unregister")
    public String unregister(@RequestParam(name="id", required=true) String id, Model model) {
        Map<UUID, Device> devices = this.hub.getDevices();
        Device device = devices.get(UUID.fromString(id));
        try {
            hub.unregister(device);
            hub.unregisterDevice(currentUser, device);
            model.addAttribute("message", "Unregistered successfully!");
        } catch (HubRegistrationException e) {
            model.addAttribute("message", "Unable to unregister with following error: " + e.getMessage());
        }
        return "unregistered";
    }

    @GetMapping("/hub/logout")
    public String logout() {
        currentUser.signOut();
        return "redirect:/login";
    }

    @GetMapping("/hub/shutdown")
    public RedirectView shutdown(Model model) {
        hub.shutdown();
        model.addAttribute("notification", "Shutting down all registered devices.");
        return new RedirectView("/hub");
    }

//    @GetMapping("/hub/notif")
//    public String notif(Model model) {
//        model.addAttribute("notification", ((Hub) hub).getRecentNotification());
//        return "redirect:/hub";
//    }
}