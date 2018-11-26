package ca.uvic.seng330.assn3.controllers;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
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

@Controller
public class HubController {
	private User currentUser = null;
    private Mediator hub;
    private Map<String, User>  users;

    HubController (Mediator hub, Map<String, User>  users) {
        this.hub = hub;
        this.users = users;
    }

    @GetMapping("/hub")
    public String hub(@RequestParam(name="username", required=false, defaultValue="null") String name, @RequestParam(name="password", required=false, defaultValue="null") String password, Model model) {
    	
    	if(name.equals("null")||password.equals("null")) {
    		if (currentUser == null) {
    			return "login";
    		}
    	}else {
    		if(!this.users.containsKey(name)) {
        		return "login_error";
        	}
        	if(!this.users.get(name).checkPassword(password)){
        		return "login_error";
        	}
        	this.currentUser = this.users.get(name);
    	}
    	
    	if(currentUser.getIsAdmin()) {
    				
	        Map<UUID, Device> devices = this.hub.getDevices();
	        ArrayList<String> cameraIds = new ArrayList();
	        ArrayList<String> lightbulbIds = new ArrayList();
	        ArrayList<String> smartplugIds = new ArrayList();
	        ArrayList<String> thermostatIds = new ArrayList();
	
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
	        
	        ArrayList<String> userNames = new ArrayList();
	        for(User user : this.users.values()) {
	        	userNames.add(user.getUsername());
	        }
	        model.addAttribute("users", userNames);
	        
	        return "hub_admin";
    	}else {
    		return "hub";
    	}
    }

    @GetMapping("/unregister")
    public String unregister(@RequestParam(name="id", required=true) String id, Model model) {
        Map<UUID, Device> devices = this.hub.getDevices();
        Device device = devices.get(UUID.fromString(id));
        try {
            model.addAttribute("message", "Unregistered successfully!");
            hub.unregister(device);
        } catch (HubRegistrationException e) {
            model.addAttribute("message", "Unable to unregister with following error: " + e.getMessage());
        }
        return "unregistered";
    }

    @GetMapping("/new_device")
    public String new_device() {
        return "new_device";
    }

    @PostMapping("/register_device")
    public String register_device(@RequestParam(name="device", required=true) String device, Model model) {
        switch (device) {
            case "Camera":
                new Camera(this.hub);
                break;
            case "Lightbulb":
                new Lightbulb(this.hub);
                break;
            case "SmartPlug":
                new SmartPlug(this.hub);
                break;
            case "Thermostat":
                new Thermostat(this.hub);
                break;
            default:
                // TODO: write a statement here
        }
        return "registered";
    }
}