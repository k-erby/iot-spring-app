package ca.uvic.seng330.assn3.models.devices;

import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.models.Mediator;

public class Lightbulb extends Device implements SwitchableDevice {

    private boolean isOn = false;
    private final Mediator aMed;

    public Lightbulb(Mediator pMed) {
        super();
        aMed = pMed;
        isOn = false;
        try {
            aMed.register(this);
        } catch (HubRegistrationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toggle() {
        isOn = !isOn;
        String status = "lightbulb is now ";
        aMed.alert(this, status + isOn);
    }

    public boolean getCondition() {
        return isOn;
    }

    @Override
    public String toString() {
        return "Lightbulb id " + super.getIdentifier().toString();
    }
}