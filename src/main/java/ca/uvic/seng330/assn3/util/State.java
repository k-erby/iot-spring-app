package ca.uvic.seng330.assn3.util;

public class State {

  private static final String[] STATES = {"Normal", "Error", "Off", "On", "Not Available", "Safety"};
  private Status powerState;
  private Status functionState;
  private boolean powerOn = false;
  
  public State() {
    powerState = Status.OFF;
    functionState = Status.NORMAL;
  }

  public State(Status ps, Status fs) {
    powerState = ps;
    functionState = fs;
  }
  
  public Status getPowerState() {
    return powerState;
  }

  public void setPowerState(Status powerState) {
    this.powerState = powerState;
    powerOn = (this.powerState == Status.ON);
  }

  public Status getFunctionState() {
    return functionState;
  }

  public void setFunctionState(Status functionState) {
    this.functionState = functionState;
  }

  //to be displayed in Views
  public String stateView() {
    return powerStateToString() + functionStateToString();
  }
  
  public String powerStateToString() {
    return "Device is " + STATES[powerState.ordinal()];
  }

  public String functionStateToString() {
    String s = "";
    if (powerState == Status.ON) {
      s = "\n";
      switch (functionState) {
        case NORMAL:
          s += "Working Normally";
          break;
        case ERROR:
          s += "Malfunctioning";
          break;
        case SAFETY:
          s += "in Safety Mode";
          break;
        case OFF:
          break;
        case ON:
          break;
        default:
          break;
      }
    }
    return s;
  }

  public boolean getPowerOn() {
    return powerOn;
  }

  @Override
  public String toString() {

    return "Current Status: Device is "
        + STATES[powerState.ordinal()]
        + " and in "
        + STATES[functionState.ordinal()]
        + " mode.";
  }
  /**
   * Unit Test
   *
   * @param args
   */
  public static void main(String[] args) {

    State s = new State();
    System.out.println(s);
  }
}
