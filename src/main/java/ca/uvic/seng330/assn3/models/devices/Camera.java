package ca.uvic.seng330.assn3.models.devices;

import ca.uvic.seng330.assn3.exceptions.CameraFullException;
import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.models.Hub.LogLevel;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.util.DeviceType;
import ca.uvic.seng330.assn3.util.State;
import ca.uvic.seng330.assn3.util.Status;

public class Camera extends Device {

    private boolean memoryFull;
    private boolean isRecording;
    private double memoryCap;
    private double footageCaptured;
    private double diskSize;
    private State state;
    private double timer;
    private long timeStart;
    private final double bitrate = 1000/8;
    private final Mediator aMed;
    private DeviceType aDeviceType;

    public Camera(Mediator med) {
        aMed = med;
        memoryFull = false;
        isRecording = false;
        isOn = false;
        state = new State();
        memoryCap = 1024.0; // 1 GB
        footageCaptured = 0.0;
        diskSize = 0.0;
        aDeviceType = DeviceType.CAMERA;

        try {
            aMed.register(this);
        } catch (HubRegistrationException e) {
            e.printStackTrace(); //if things go really bad
        }
    }

    public double getDiskSize() {

      return diskSize;
    }

    public boolean isRecording() {
      return isRecording;
    }

    public boolean isMemoryFull() {
      return memoryFull;
    }
    
    public void resetMemory() {
      memoryFull = false;
      diskSize = 0;
      footageCaptured = 0;
      state.setFunctionState(Status.NORMAL);
    }

    public void forceStop() throws CameraFullException {
      record(); //finish recording
      isRecording = false;
    }
    
    public String memoryUsage() {

      return String.format("Memory Capacity: %.2f MB\nFootage Captured: %.2f MB\nMemory Used: %.2f%%\n", memoryCap, footageCaptured, diskSize);
    }

    public void Stopwatch(boolean start) {

      if (start) {
        timeStart = System.currentTimeMillis();
      } else {
        long timeEnd = System.currentTimeMillis();
        long took = timeEnd - timeStart;
        timer = (double) (took / 1000);
      }
    }

    /**
     * Starts/stops recoding of camera.
     *
     * @throws CameraFullException if memory is full, cannot start recording.
     * @pre powerOn power must be on
     */
    public void record() throws CameraFullException {

      assert isOn;
      memoryFull = (diskSize == 100);
      boolean wasRecording = isRecording; // state of recording when method is called
      try {

        if (memoryFull || diskSize >= 100) {

          state.setFunctionState(Status.ERROR);
          setStatus(Status.ERROR);
          isRecording = false;
          throw new CameraFullException();
        } else {
          state.setFunctionState(Status.NORMAL);
          setStatus(Status.NORMAL);
          isRecording ^= true;
          aMed.alert(LogLevel.NOTIFY, this, "Camera recording has been toggled");
        }
      } catch (CameraFullException e) {

        aMed.alert(LogLevel.ERROR, null, e.message());
        state.setFunctionState(Status.ERROR);
        setStatus(Status.ERROR);

      } finally {
        if (wasRecording && !memoryFull) {
          Stopwatch(false);
          aMed.alert(LogLevel.INFO, null, "...Timer stopped.");
          footageCaptured += bitrate * timer;
        } else if(!memoryFull){
          Stopwatch(true);
          aMed.alert(LogLevel.INFO, null, "Timer started...");
        }
        diskSize = footageCaptured / memoryCap * 100.0; // disk size used in percentage
        if(diskSize >= 80 && diskSize <= 100) {
          aMed.alert(
              LogLevel.WARN, null, "Mode switch to 'Safety'. Consider freeing up memory space");
          state.setFunctionState(Status.SAFETY);
          setStatus(Status.SAFETY);
        }else if(diskSize > 100) {
          aMed.alert(
              LogLevel.WARN, null, "Memory Exceeded. Extra Footage Captured Discarded.");
          state.setFunctionState(Status.SAFETY);
          setStatus(Status.SAFETY);
          footageCaptured = memoryCap;
          diskSize = 100;
        }
      }
    }

    //manual call for testing
    //will talk to hub regardless
    public void dynamicCamActivity() throws CameraFullException {
      
      /* Acceptance test doesn't ask to record..
       * 
      if(dynamicActivity && !isRecording) {
        aMed.alert(LogLevel.NOTIFY, this, "Activity detected. Begin Recording...");
        record();
      }else if(!dynamicActivity && isRecording){
        record();
      }else if(isRecording) {
        forceStop();
      }*/
      
      if(activityDetected) {
        aMed.alert(LogLevel.NOTIFY, this, "Activity detected in front of "+this.toString());
      }
      
      aMed.dynamicActivity(activityDetected, this);
      
    }
    
    /** Logs current state and shuts down device. */
    @Override
    public void shutdown() {
      super.shutdown();
      aMed.alert(
          LogLevel.NOTIFY, this, ("Current Memory is\n" + memoryUsage()));
      isRecording = false;
    }

    @Override
    public String toString() {
      String className = getClass().getSimpleName();
      return className + " " + getIdentifier();
    }


    public String getDeviceType() {
        return aDeviceType.toString().toLowerCase();
    }

    public DeviceType getDeviceTypeEnum() {
        return aDeviceType;
    }
    
    public static void main(String[] args) throws CameraFullException, InterruptedException {
      ca.uvic.seng330.assn3.models.Hub h = new ca.uvic.seng330.assn3.models.Hub();
      Camera c = new Camera(h);
      System.out.println(c.state);
      System.out.println("Starting up...");
      h.startup();
      System.out.println(c.state);
      System.out.println(c.memoryUsage());
      System.out.println("Recording test...");
      c.record(); // doesn't work without hub
      Thread.sleep(5000);
      c.record();
      System.out.println(
          "\n...successfully recorded for 5 seconds.\n"
              + c.memoryUsage()
              + "\nShutting down device "
              + c
              + "...");
      h.shutdown();
    }
    
   
}

