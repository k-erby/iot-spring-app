package ca.uvic.seng330.assn3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ca.uvic.seng330.assn3.models.Hub;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.views.WebClient;

@Configuration
public class HubConfig {

    @Bean
    public Mediator mediator() {
        Hub hub = new Hub();
       // WebClient INSTANCE = new WebClient(hub);

        return hub;
    }

}
