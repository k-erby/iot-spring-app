package ca.uvic.seng330.assn3.exceptions;

public class HubRegistrationException extends Exception {

  public String message = null;

  public HubRegistrationException() {}

  public HubRegistrationException(String msg) {
    super(msg);
    message = msg;
  }

  public String message() {

    if (message != null) return message();
    else return toString();
  }

  @Override
  public String toString() {
    return "Error: Either there was no Hub to register to or the Device was never registered.";
  }
}
