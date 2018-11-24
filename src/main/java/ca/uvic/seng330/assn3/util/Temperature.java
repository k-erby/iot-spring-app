package ca.uvic.seng330.assn3.util;

public class Temperature {

  private double kelvin;
  private double fahrenheit;
  private double celsius;
  private double temp;
  private String unit;
  private Unit scale;
  final String DEGREE = "\u00b0";

  public enum Unit {
    KELVIN,
    FAHRENHEIT,
    CELSIUS
  }

  protected static final String[] UNITS = {"Kelvin", "Fahrenheit", "Celsius", "K", "F", "C"};

  public static class TemperatureOutofBoundsException extends Exception {

    public String message = null;

    public TemperatureOutofBoundsException() {}

    public TemperatureOutofBoundsException(String message) {
      super(message);
      this.message = message;
    }

    public String message() {

      if (message != null) return message();
      else return toString();
    }

    @Override
    public String toString() {
      return "Error: The temperature you are trying to set is unreasonable. In Celsius scale, please input above -10 degrees and below 40 degrees";
    }
  }

  /** Default constructor for Temperature. Room temperature in Celsius. */
  public Temperature() {

    temp = 25.0;
    unit = UNITS[5];
    scale = Unit.CELSIUS;
    convert(scale);
  }
  /**
   * Constructor for Temperature.
   *
   * @param t Temperature passed to constructor as value only
   */
  public Temperature(double t) {

    temp = t;
  }

  /**
   * Constructor for Temperature.
   *
   * @param s The scale used for the temperature
   * @param t Default variable for storing value of temperature
   */
  public Temperature(double t, Unit s) {

    temp = t;
    unit = UNITS[s.ordinal() + 3];
    scale = s;
    convert(s);
  }

  private void convert(Unit s) {

    switch (s) {
      case KELVIN:
        kelvin = temp;
        celsius = temp - 273.15;
        fahrenheit = celsius * 9 / 5 + 32;
        break;
      case FAHRENHEIT:
        fahrenheit = temp;
        celsius = ((temp - 32) * 5 / 9);
        kelvin = celsius + 273.15;
        break;
      case CELSIUS:
        celsius = temp;
        fahrenheit = ((temp * 9 / 5) + 32);
        kelvin = temp + 273.15;
        break;
    }
  }
  /**
   * Set the temperature.
   *
   * @param t temperature to set
   * @param s unit of temperature
   * @pre t < 1000 temperature must be below 1000 units
   */
  public void setTemperature(double t, Unit s) {
    assert t < 1000;
    temp = t;
    unit = UNITS[s.ordinal() + 3];
    scale = s;
    convert(s);
  }

  public double getTemperature() {

    return temp;
  }

  public Unit getScale() {
    return scale;
  }

  public double getCelsius() {

    return celsius;
  }

  public double getFahrenheit() {

    return fahrenheit;
  }

  public double getKelvin() {

    return kelvin;
  }

  
  public double compareTemp(Temperature that) {
    
    return this.getCelsius()/that.getCelsius();
   
  }
  
  public boolean equalMode(Temperature t) {
    
    if(this.getScale() == t.getScale()) return true;
    else return false;
    
  }
  
  @Override
  public String toString() {
    return String.format("%.1f %s%s",temp, DEGREE, unit);
  }

  /**
   * Unit Test
   *
   * @param args
   */
  public static void main(String[] args) {

    Temperature t = new Temperature();
    Temperature t1 = new Temperature(255.0, Unit.KELVIN);
    Temperature t2 = new Temperature(77.0, Unit.FAHRENHEIT);
    System.out.println("Temp t " + t);
    System.out.println("In Celcius: " + t.getCelsius());
    System.out.println("In Fahrenheit: " + t.getFahrenheit());
    System.out.println("In Kelvin: " + t.getKelvin());
    System.out.println("Temp t1 " + t1);
    System.out.println("In Celcius: " + t1.getCelsius());
    System.out.println("In Fahrenheit: " + t1.getFahrenheit());
    System.out.println("In Kelvin: " + t1.getKelvin());
    System.out.println("Temp t2 " + t2);
    System.out.println("In Celcius: " + t2.getCelsius());
    System.out.println("In Fahrenheit: " + t2.getFahrenheit());
    System.out.println("In Kelvin: " + t2.getKelvin());
  }
}
