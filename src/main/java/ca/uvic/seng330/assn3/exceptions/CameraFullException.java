package ca.uvic.seng330.assn3.exceptions;

public class CameraFullException extends Exception {

  private String message = null;

  public CameraFullException() {}

  public CameraFullException(String message) {
    super(message);
    this.message = message;
  }

  public String message() {

    if (message != null) return message();
    else return toString();
  }

  @Override
  public String toString() {
    return "Error: Cannot Record. Memory Exceeded";
  }
}
