package ca.uvic.seng330.assn3.models.devices;

import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.util.DeviceType;
import ca.uvic.seng330.assn3.util.Status;
import ca.uvic.seng330.assn3.util.Temperature;

public class Thermostat extends Device {
    private final Mediator aMed;
    private Status status = Status.OFF;
    private Temperature setPoint;
    public DeviceType aDeviceType;

    {
        try {
            setPoint = new Temperature(72, Temperature.Unit.FAHRENHEIT);
        } catch (Temperature.TemperatureOutofBoundsException e) {
            e.printStackTrace();
        }
    }

    public Thermostat(Mediator mediator) {
        super();
        this.aMed = mediator;
        this.aDeviceType = DeviceType.THERMOSTAT;
        try {
            aMed.register(this);
        } catch (HubRegistrationException e) {
            e.printStackTrace();
        }
    }

    public Status getStatus() {
        return status;
    }

    public String getTemperature() {
        String temp =  Double.toString(setPoint.getTemperature());
        if (setPoint.getUnit() == Temperature.Unit.FAHRENHEIT) {
            temp = temp + " F";
        } else {
            temp = temp + " C";
        }
        return temp;
    }

    @Override
    public String toString() {
        return super.getIdentifier().toString();
    }

    public void setTemp(Temperature t) {
        setPoint = t;
        aMed.alert(this, "Setting temp to " + t.getTemperature());
        status = Status.NORMAL;
    }

    public String getDeviceType() {
        return aDeviceType.toString().toLowerCase();
    }

    public DeviceType getDeviceTypeEnum() {
        return aDeviceType;
    }
}