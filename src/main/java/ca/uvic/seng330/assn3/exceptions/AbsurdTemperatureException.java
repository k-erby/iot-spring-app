package ca.uvic.seng330.assn3.exceptions;

public class AbsurdTemperatureException extends Exception {

  public String message = null;

  public AbsurdTemperatureException() {}

  public AbsurdTemperatureException(String message) {
    super(message);
    this.message = message;
  }

  public String message() {

    if (message != null) return message();
    else return toString();
  }

  @Override
  public String toString() {
    return "Error: The temperature you are trying to set is absurd!.Please input below 1000 degrees";
  }
}
