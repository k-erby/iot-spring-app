package ca.uvic.seng330.assn3.models.devices;

import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.util.DeviceType;
import ca.uvic.seng330.assn3.util.Status;

public class SmartPlug extends Device implements SwitchableDevice {

    private final Mediator aMed;
    private boolean isOn = false;
    private Status status = Status.OFF;
    public DeviceType aDeviceType;

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
        if (status == Status.ON) {
            status = Status.OFF;
        } else if (status == Status.OFF){
            status = Status.ON;
        }
    }

    @Override
    public String toString() {
        return super.getIdentifier().toString();
    }

    public Status getStatus() {
        return status;
    }

    public String getDeviceType() {
        return aDeviceType.toString().toLowerCase();
    }

    public DeviceType getDeviceTypeEnum() {
        return aDeviceType;
    }
}
