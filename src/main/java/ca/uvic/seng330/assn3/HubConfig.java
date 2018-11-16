package ca.uvic.seng330.assn3;

import ca.uvic.seng330.assn3.models.Hub;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.devices.Camera;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HubConfig {

    @Bean
    public Mediator mediator() {
        Hub hub = new Hub();
        Camera camera1 = new Camera(hub);
        return hub;
    }

}
