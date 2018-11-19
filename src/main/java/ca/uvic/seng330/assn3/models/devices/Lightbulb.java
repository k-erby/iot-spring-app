package ca.uvic.seng330.assn3.models.devices;

import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.util.DeviceType;
import ca.uvic.seng330.assn3.util.Status;

public class Lightbulb extends Device implements SwitchableDevice {

    private boolean isOn = false;
    private final Mediator aMed;
    private Status status = Status.OFF;
    public DeviceType aDeviceType;

    public Lightbulb(Mediator pMed) {
        super();
        aMed = pMed;
        isOn = false;
        aDeviceType = DeviceType.LIGHTBULB;
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
        String status = "lightbulb is now ";
        aMed.alert(this, status + isOn);
    }

    public boolean getCondition() {
        return isOn;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return super.getIdentifier().toString();
    }

    public String getDeviceType() {
        return aDeviceType.toString().toLowerCase();
    }

    public DeviceType getDeviceTypeEnum() {
        return aDeviceType;
    }
}