package ca.uvic.seng330.assn3;

import ca.uvic.seng330.assn3.models.Hub;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.devices.Camera;
import ca.uvic.seng330.assn3.models.devices.Lightbulb;
import ca.uvic.seng330.assn3.models.devices.SmartPlug;
import ca.uvic.seng330.assn3.models.devices.Thermostat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HubConfig {

    @Bean
    public Mediator mediator() {
        Hub hub = new Hub();
        Camera cm = new Camera(hub);
        Lightbulb lb = new Lightbulb(hub);
        SmartPlug sp = new SmartPlug(hub);
        Thermostat th = new Thermostat(hub);
        return hub;
    }

}
