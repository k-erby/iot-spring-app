package ca.uvic.seng330.assn3.models.devices;

import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.util.DeviceType;

public class SmartPlug extends Device implements SwitchableDevice {

    private final Mediator aMed;
    private boolean isOn = false;
    private DeviceType aDeviceType;

    public SmartPlug(Mediator med) {
        super();
        aMed = med;
        isOn = false;
        aDeviceType = DeviceType.SMARTPLUG;
        try {
            aMed.register(this);
        } catch (HubRegistrationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toggle() {
        isOn = !isOn;
        String status = "plug is now ";
        aMed.alert(this, status + isOn);
    }

    @Override
    public String toString() {
        return "Smartplug id " + super.getIdentifier().toString();
    }
}
