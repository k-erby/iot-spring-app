package ca.uvic.seng330.assn3.models.devices;

import ca.uvic.seng330.assn3.exceptions.CameraFullException;
import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.util.DeviceType;
import ca.uvic.seng330.assn3.util.Status;

public class Camera extends Device {

    private boolean isRecording;
    private int diskSize;
    private Status status = Status.OFF;
    public DeviceType aDeviceType;

    private final Mediator aMed;

    public Camera(Mediator med) {
        super();
        aMed = med;
        diskSize = 999;
        aDeviceType = DeviceType.CAMERA;

        try {
            aMed.register(this);
        } catch (HubRegistrationException e) {
            // in future, log this
        }
    }

    public String startup() {
        isRecording = false;
        return "started";
    }

    public void record() throws CameraFullException {
        isRecording = true;
        status = Status.ON;
        aMed.alert(this, "Started recording");
        if(Math.random()*1000 > diskSize) {
            status = Status.ERROR;
            throw new CameraFullException("Camera Full");
        }
    }

    @Override
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

