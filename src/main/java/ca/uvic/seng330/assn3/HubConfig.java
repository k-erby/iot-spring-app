package ca.uvic.seng330.assn3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ca.uvic.seng330.assn3.models.Hub;
import ca.uvic.seng330.assn3.models.Mediator;

@Configuration
public class HubConfig {

    @Bean
    public Mediator mediator() {
        Hub hub = new Hub();
        return hub;
    }

}
