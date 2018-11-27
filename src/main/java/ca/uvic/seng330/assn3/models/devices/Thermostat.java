package ca.uvic.seng330.assn3.models.devices;

import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.models.Hub;
import ca.uvic.seng330.assn3.models.Hub.LogLevel;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.util.DeviceType;
import ca.uvic.seng330.assn3.util.Status;
import ca.uvic.seng330.assn3.util.Temperature;
import ca.uvic.seng330.assn3.util.Temperature.TemperatureOutofBoundsException;
import ca.uvic.seng330.assn3.util.Temperature.Unit;

public class Thermostat extends Device {
  
    private final Mediator aMed;
    private Temperature temp;
    private Temperature.Unit mode;
    private DeviceType aDeviceType;
   
    public Thermostat(Mediator mediator) {
        this.aMed = mediator;
        this.aDeviceType = DeviceType.THERMOSTAT;
        temp = new Temperature(); //default sets to room temp celsius
        mode = temp.getScale();
        try {
            aMed.register(this);
        } catch (HubRegistrationException e) {
            e.printStackTrace();
        }
    }

    //this should be named something different but dont wanna break your VIEW
    //or just change your call to getTemp.toString();
    public String getTemperature() {
        return temp.toString();
    }
    
    public Temperature getTemp() {
      Temperature t = new Temperature(temp.getTemperature(), temp.getScale());
      return t;
    }
    
    public Unit getMode() {
      return mode;
    }
    
    //switch between C/F
    public void scaleSwitch() throws Temperature.TemperatureOutofBoundsException {
     /*
      * it would be easier just to do assignments for temp = and mode =
      * but setTemp handles alerts and exceptions juuust in case
      * 
      */
     if (mode == Unit.CELSIUS) {
       setTemp(new Temperature(temp.getFahrenheit(), Unit.FAHRENHEIT));
     }else if(mode == Unit.FAHRENHEIT) {
       setTemp(new Temperature(temp.getCelsius(), Unit.CELSIUS));
     }
    }
    
    /**
     * Set the temperature of the Thermostat.
     *
     * @param t the new Temperature to set to the Thermostat
     * @pre the scale used for the temperature of t must be the same as mode
     * @pre the temperature must be reasonable
     * @pre t.getTemperature() < 1000
     */
    public void setTemp(Temperature t) throws Temperature.TemperatureOutofBoundsException {

      //assert t.getTemperature() < 1000; //assignment 2 test
      //assert reasonableTemp(t.getTemperature(), t.getScale()); //redundant because of throw
      try {
        if (!reasonableTemp(t.getTemperature(), t.getScale()))
          throw new Temperature.TemperatureOutofBoundsException();
        temp = t;
        mode = t.getScale();
        state.setFunctionState(Status.NORMAL);
        aMed.alert(LogLevel.NOTIFY, this, "Temperature set to " + temp.toString());

      }catch (Temperature.TemperatureOutofBoundsException e) {
          state.setFunctionState(Status.ERROR);
          
          aMed.alert(LogLevel.ERROR, null, e.message());
    
          aMed.alert(
              LogLevel.WARN, null, "Mode switch to 'Safety'. No unreasonable temperatures may be input");
    
          state.setFunctionState(Status.SAFETY);    
        }
    }

    /**
     * Asserts if the temperature being set is reasonable for the scale.
     *
     * @param s the mode of the Thermostat
     * @param t the temperature to check
     * @return the result of the assertion
     */
    private boolean reasonableTemp(double t, Temperature.Unit s) {
      
        switch (s) {
          case CELSIUS:
            if (t >= 40 || t <= -10) return false;
            break;
          case FAHRENHEIT:
            if (t >= 110 || t <= -10) return false;
            break;
          case KELVIN:
            if (t >= 320 || t <= 0) return false;
            break;
        }
        
        return true;
    }
    
    /**
     * Temp automatically changed from outside climate
     * @param t -> the outside temperature
     * @throws TemperatureOutofBoundsException 
     */
    public void dynamicActivity(Temperature t) throws TemperatureOutofBoundsException {
      
      double factor = this.getTemp().compareTemp(t);
      if(activityDetected) {
       
        setTemp(new Temperature(getTemp().getTemperature() * factor, getMode()));
        Hub.log(LogLevel.INFO, "Setting temperature dynamically.");
      }
    }

    public void shutdown() {
      
      super.shutdown();
      aMed.alert(LogLevel.NOTIFY, this, ("Current Temperature is " + temp.toString()));
    }    

    public String getDeviceType() {
        return aDeviceType.toString().toLowerCase();
    }

    public DeviceType getDeviceTypeEnum() {
        return aDeviceType;
    }
    
    @Override
    public String toString() {
        return this.getIdentifier().toString();
    }

    public static void main(String[] args) throws Temperature.TemperatureOutofBoundsException {

      ca.uvic.seng330.assn3.models.Hub h = new ca.uvic.seng330.assn3.models.Hub();
      Thermostat t = new Thermostat(h);
      System.out.println(t.state);
      System.out.println("Starting up...");
      h.startup();
      System.out.println(t.getState());
      System.out.println(t.getTemperature());
      System.out.println("Setting new temperature...");
      t.setTemp(new Temperature(67, Temperature.Unit.FAHRENHEIT)); // needs Hub for logging
      System.out.println(t.getTemperature());
      System.out.println("Switching scale...");
      t.scaleSwitch();
      System.out.println(t.getTemperature());
      System.out.println("Shutting down device " + t + "...");
      h.shutdown();
      System.out.println(t.getState());
    }
}
