package ca.uvic.seng330.assn3.models.devices;

import ca.uvic.seng330.assn3.exceptions.CameraFullException;
import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.models.Hub.LogLevel;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.util.DeviceType;
import ca.uvic.seng330.assn3.util.Status;

public class SmartPlug extends Device implements SwitchableDevice {

    private final Mediator aMed;
    private DeviceType aDeviceType;

    public SmartPlug(Mediator med) {
        aMed = med;
        aDeviceType = DeviceType.SMARTPLUG;
        
        try {
            aMed.register(this);
        } catch (HubRegistrationException e) {
            e.printStackTrace();
        }
    }

    public boolean getCondition() {

      return isOn;
    }

    /**
     * Toggle powerOn and update states.
     *
     * @post pre-toggle powerOn != post-toggle powerOn
     */
    public void toggle() {

      String s = (isOn) ? "off." : "on.";
      boolean a = getCondition();
      isOn ^= true;
      boolean b = getCondition();
      assert a != b;
      aMed.alert(LogLevel.NOTIFY, this, "Switching SmartPlug " + s);
      state.setPowerState((isOn) ? Status.ON : Status.OFF);
    }
    
    /*
     * 
     * Consider adding Outlets later
     * 
     */
   
    public String getDeviceType() {
        return aDeviceType.toString().toLowerCase();
    }

    public DeviceType getDeviceTypeEnum() {
        return aDeviceType;
    }
    
    @Override
    public String toString() {
      String className = getClass().getSimpleName();
      return className + " " + getIdentifier();
    }

    public static void main(String[] args) throws CameraFullException, InterruptedException {
      
      ca.uvic.seng330.assn3.models.Hub h = new ca.uvic.seng330.assn3.models.Hub();
      SmartPlug p = new SmartPlug(h);
      System.out.println(p.state);
      System.out.println("Starting up...");
      h.startup();
      System.out.println(p.state);
      System.out.println("Turning on...");
      p.toggle(); // needs Hub
      System.out.println(p.state);
      System.out.println("Turning off...");
      p.toggle();
      System.out.println(p.state);
      System.out.println("Shutting down...");
      h.shutdown();
    }
}
