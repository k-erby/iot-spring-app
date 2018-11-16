package ca.uvic.seng330.assn3.models.devices;

import ca.uvic.seng330.assn3.exceptions.CameraFullException;
import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.util.DeviceType;
import ca.uvic.seng330.assn3.util.Status;

public class Camera extends Device {

    private boolean isRecording;
    private int diskSize;
    private DeviceType aDeviceType;

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
        aMed.alert(this, "Started recording");
        if(Math.random()*1000 > diskSize) {
            throw new CameraFullException("Camera Full");
        }
    }

    @Override
    public Status getStatus() {
        return Status.ERROR;
    }

    @Override
    public String toString() {
        return "Camera id " + super.getIdentifier().toString();
    }
}

