package ca.uvic.seng330.assn3.models.devices;

import ca.uvic.seng330.assn3.util.DeviceType;
import ca.uvic.seng330.assn3.util.Status;

import java.util.UUID;

public abstract class Device {

    private final UUID aUuid = UUID.randomUUID();
    private Status aStatus; // This can't be NULL!
    private DeviceType aDeviceType;

    public UUID getIdentifier() {
        return aUuid;
    }

    public Status getStatus() {
        // Since the status can't be NULL, then check IF NULL and IF return dummy
        // status.
        return aStatus == null ? Status.NOT_AVAILABLE : aStatus;
    }

    public void setStatus(Status status) {
        this.aStatus = status;
    }

    @Override
    public String toString() {
        return aUuid.toString();
    }

    public DeviceType getDeviceType() { return aDeviceType; }

}