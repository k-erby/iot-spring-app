package ca.uvic.seng330.assn3.models.devices;

import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.models.Hub;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.util.DeviceType;
import ca.uvic.seng330.assn3.util.Status;

public class Lightbulb extends Device implements SwitchableDevice {

  private final Mediator aMed;
  private DeviceType aDeviceType;

  public Lightbulb(Mediator pMed) {

    aMed = pMed;
    aDeviceType = DeviceType.LIGHTBULB;

    try {
      aMed.register(this);
    } catch (HubRegistrationException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void toggle() {

    String s = (isOn) ? "off." : "on.";
    boolean a = getCondition();
    isOn = !isOn;
    boolean b = getCondition();
    assert a != b;
    aMed.alert(Hub.LogLevel.NOTIFY, this, "Switching lightbulb " + s);
    state.setPowerState((isOn) ? Status.ON : Status.OFF);
  }

  public boolean getCondition() {
    return isOn;
  }

  @Override
  public String toString() {
    return this.getIdentifier().toString();
  }

  public String getDeviceType() {
    return aDeviceType.toString().toLowerCase();
  }

  public DeviceType getDeviceTypeEnum() {
    return aDeviceType;
  }

  public static void main(String[] args) {
    Hub h = new Hub();
    Lightbulb l = new Lightbulb(h);
    System.out.println(l.state);
    h.startup();
    System.out.println("Starting up...");
    System.out.println(l.state);
    System.out.println("Turning on...");
    l.toggle(); // needs Hub
    System.out.println(l.state);
    System.out.println("Turning off...");
    l.toggle();
    System.out.println(l.state);
    System.out.println("Shutting down...");
    h.shutdown();
  }
}
