package ca.uvic.seng330.assn3;

import ca.uvic.seng330.assn3.models.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public User user() {
        User user = new User("admin", "seng330", true);
        return user;
    }

}
