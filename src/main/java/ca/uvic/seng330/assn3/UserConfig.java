package ca.uvic.seng330.assn3;

import ca.uvic.seng330.assn3.models.User;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public Map<String, User> user() {
    	Map<String, User> users = new HashMap<>();
        User user = new User("admin", "seng330", true);
        User trent = new User("amy", "iliketea", false);
        User nate = new User("cole", "123", false);
        users.put(user.getUsername(), user);
        users.put(trent.getUsername(), trent);
        users.put(nate.getUsername(), nate);
        return users;
    }

}
