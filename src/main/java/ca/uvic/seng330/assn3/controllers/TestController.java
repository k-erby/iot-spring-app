package ca.uvic.seng330.assn3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.devices.Camera;
import ca.uvic.seng330.assn3.models.devices.Device;
import ca.uvic.seng330.assn3.models.devices.Thermostat;
import ca.uvic.seng330.assn3.util.DeviceType;
import ca.uvic.seng330.assn3.util.Temperature;
import ca.uvic.seng330.assn3.util.Temperature.TemperatureOutofBoundsException;
import ca.uvic.seng330.assn3.util.Temperature.Unit;

@Controller
public class TestController {

  private Mediator hub;

  TestController(Mediator hub) {
    this.hub = hub;
  }

  @GetMapping("/hub/testC6")
  public String testC6() {

    for (Device d : hub.getDevices().values()) {
      if (d.getDeviceTypeEnum() == DeviceType.CAMERA) {
        d.setActivityDetected(true);
        ((Camera) d).dynamicCamActivity();
        d.setActivityDetected(false);
      }
    }
    return "hub";
  }

  @GetMapping("/hub/testD6")
  public String testD6() throws TemperatureOutofBoundsException {

    for (Device d : hub.getDevices().values()) {
      if (d.getDeviceTypeEnum() == DeviceType.THERMOSTAT) {
        ((Thermostat) d).dynamicActivity(new Temperature(29, Unit.CELSIUS));
      }
    }
    return "hub";
  }

  @GetMapping("/hub/testE3")
  public String testE3() {

    for (Device d : hub.getDevices().values()) {
      if (d.getDeviceTypeEnum() == DeviceType.CAMERA) {
        hub.dynamicActivity(true, d);
      }
    }
    return "hub";
  }

  @GetMapping("/hub/testE4")
  public String testE4() {

    for (Device d : hub.getDevices().values()) {
      if (d.getDeviceTypeEnum() == DeviceType.CAMERA) {
        hub.dynamicActivity(false, d);
      }
    }
    return "hub";
  }
}
