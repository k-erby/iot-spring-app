package ca.uvic.seng330.assn3.models.devices;

import ca.uvic.seng330.assn3.util.Temperature;
import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.util.Status;

public class Thermostat extends Device {
    private final Mediator aMed;
    private Status status = Status.NORMAL;
    private Temperature setPoint;

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
        try {
            aMed.register(this);
        } catch (HubRegistrationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Thermostat id " + super.getIdentifier().toString();
    }

    public void setTemp(Temperature t) {
        setPoint = t;
        aMed.alert(this, "Setting temp to " + t.getTemperature());
        status = Status.NORMAL;
    }
}