package ca.uvic.seng330.assn3.Controllers;

import ca.uvic.seng330.assn3.Models.HubRegistrationException;
import ca.uvic.seng330.assn3.Models.Mediator;

public class SmartPlug extends Device implements SwitchableDevice {

    private final Mediator aMed;
    private boolean isOn = false;

    public SmartPlug(Mediator med) {
        super();
        aMed = med;
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
        String status = "plug is now ";
        aMed.alert(this, status + isOn);
    }

    @Override
    public String toString() {
        return "Smartplug id " + super.getIdentifier().toString();
    }
}
