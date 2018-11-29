package ca.uvic.seng330.assn3;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("login");
    registry.addViewController("/hub").setViewName("hub");
    registry.addViewController("/hub_admin").setViewName("hub_admin");
    registry.addViewController("/camera").setViewName("camera");
    registry.addViewController("/lightbulb").setViewName("lightbulb");
    registry.addViewController("/smartplug").setViewName("smartplug");
    registry.addViewController("/thermostat").setViewName("thermostat");
    registry.addViewController("/unregistered").setViewName("unregistered");
    registry.addViewController("/registered").setViewName("registered");
    registry.addViewController("/new_user").setViewName("new_user");
    registry.addViewController("/new_user_created").setViewName("new_user_created");
    registry.addViewController("/new_device").setViewName("new_device");
    registry.addViewController("/device_error").setViewName("device_error");
  }
}
