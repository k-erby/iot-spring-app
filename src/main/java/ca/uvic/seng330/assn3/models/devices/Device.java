package ca.uvic.seng330.assn3.models.devices;

import java.util.UUID;
import ca.uvic.seng330.assn3.models.Hub;
import ca.uvic.seng330.assn3.util.DeviceType;
import ca.uvic.seng330.assn3.util.State;
import ca.uvic.seng330.assn3.util.Status;

public abstract class Device {

    private final UUID aUuid = UUID.randomUUID();
    protected Status aStatus; // This can't be NULL!
    protected State state = new State();
    protected boolean isOn = false;
    protected boolean activityDetected = false;
    public DeviceType aDeviceType;

    public UUID getIdentifier() {
        return aUuid;
    }

    public Status getStatus() {
        // Since the status can't be NULL, then check IF NULL and IF return dummy
        // status.
        return aStatus == null ? Status.NOT_AVAILABLE : aStatus;
    }
    
    public State getState() {

      State s = new State(state.getPowerState(), state.getFunctionState());
      return s;
    }
    
    public void setState(Status ps, Status fs) {
      
      state.setFunctionState(fs);
      state.setPowerState(ps);
      isOn = state.getPowerOn();
      
    }

    public void setStatus(Status status) {
        this.aStatus = status;
    }
    
    public boolean isPowerOn() {
      return isOn;
    }
    
    public boolean equals(Device that) {
      
      if(this.getIdentifier() == that.getIdentifier()) return true;
      else return false;
    }
    
    public void startup() {
      
      setState(Status.ON, Status.NORMAL);
      Hub.log(Hub.LogLevel.INFO, "Starting up "+ this + "...");
      isOn = state.getPowerOn();
    }
    
    public void shutdown() {
      
      setState(Status.OFF, Status.NOT_AVAILABLE);
      Hub.log(Hub.LogLevel.INFO, "Shutting down "+ this + "...");
      isOn = state.getPowerOn();
    }
    
    public void toggle() {
      isOn ^= true;
    }

    @Override
    public String toString() {
        return aUuid.toString();
    }

    public String getDeviceType() {
        return aDeviceType.toString().toLowerCase();
    }

    public DeviceType getDeviceTypeEnum() {
        return aDeviceType;
    }

}