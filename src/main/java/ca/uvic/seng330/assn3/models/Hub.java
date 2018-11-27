package ca.uvic.seng330.assn3.models;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.models.devices.Camera;
import ca.uvic.seng330.assn3.models.devices.Device;
import ca.uvic.seng330.assn3.util.DeviceType;
import ca.uvic.seng330.assn3.util.JSONMessaging;
import ca.uvic.seng330.assn3.views.Client;

public class Hub extends Device implements Mediator{

    private HashMap<UUID, Device> aDevices = new HashMap<UUID, Device>();
    private HashMap<UUID, Client> aClients = new HashMap<UUID, Client>();
    private HashMap<UUID, User> aUsers = new HashMap<UUID, User>();
    private final static Logger LOGGER = LoggerFactory.getLogger(Hub.class);
    private static PrintWriter LOGWRITER; //Log messages saved to file
   
    public static enum LogLevel {
      INFO,
      WARN,
      ERROR,
      DEBUG,
      TRACE,
      NOTIFY
    }
    
    public Hub() {
      
      try {
        log(LogLevel.DEBUG, "Opening file...");

        LOGWRITER = new PrintWriter("logfile.txt");
       }catch(FileNotFoundException e) {
         e.printStackTrace();
         log(LogLevel.DEBUG, "File could not be found.");
       }
      
       log(LogLevel.DEBUG, "...success!\nBegin logging to file...");
    }

    //turns on all devices
    public void startup() {
      log(LogLevel.INFO, "Starting up...");
      /* 
       * Not sure what we want for functionality
       * 
       *
      for(User u: aUsers.values(){
        startup(u);
      }
      */
      for(Device d: aDevices.values()) {
        d.startup();
        log(LogLevel.INFO, "...success!");
      }
       log(LogLevel.INFO, "...started up successfully.");

    }
    
    //starts up all devices registered to User u
    public void startup(User u) {
      
      for(Device d: u.getDevices()) {
        d.startup();
        log(LogLevel.INFO, "...success!");
      }
      
      u.signIn();
      log(LogLevel.INFO, String.format("%s has signed in.", u));
    }

    //shutsdown all devices and closes logfile.txt
    public void shutdown() {
      log(LogLevel.WARN, "Shutting down...");

      for(Device d: aDevices.values()) {
        d.shutdown();
        log(LogLevel.INFO, "...success!");

      }
      log(LogLevel.INFO, "...shutdown successfully.");      
      log(LogLevel.DEBUG, "End logging to file. Closing...");
      LOGWRITER.close();
      log(LogLevel.DEBUG, "...closed successfully.");
    }
    
    //turns off all devices registered to User u
    public void shutdown(User u) {

      for(Device d: u.getDevices()) {
        d.shutdown();
        log(LogLevel.INFO, "...success!");
      }
      u.signOut();
    }
    
    /*
     * Hard Reset.
     * Turn off and unregister all devices
     * Unregister all users
     * Unregister all connected clients
     * 
     */
    public void hardShutdown() throws HubRegistrationException {
      log(LogLevel.WARN, "Shutting down...");
      
      for(Client c: aClients.values()) {
        for(User u: aUsers.values()) {
          for(Device d: aDevices.values()) {
            unregisterDevice(u, d);
            d.shutdown();
            unregister(d);
          }
          unregisterUser(c, u);
          aUsers.remove(u.getIdentifier());
          u.signOut();
        }
        unregister(c);
      }
      LOGWRITER.close();
    }
    
    /*
     * Could've overloaded register/unregister a bunch 
     * but decided to name the methods differently for clarity
     * more accurately it is
     * 
     * [un]registerUser[To/From]Client
     * and
     * [un]registerDevice[To/From]User
     * 
     */

    @Override
    public void register(Device pDevice) throws HubRegistrationException {
      assert pDevice != null;
      
      try {
        if (!aDevices.containsKey(pDevice.getIdentifier())) {
            aDevices.put(pDevice.getIdentifier(), pDevice);
        } else {
            throw new HubRegistrationException(pDevice + " was already registered");
        }
        log(LogLevel.INFO, String.format("%s registered to Hub.", pDevice));
      }catch(HubRegistrationException e) {
        log(LogLevel.ERROR, e.message());
      }
    }

    @Override
    public void register(Client pClient) throws HubRegistrationException {
      assert pClient != null;
      
      try {
        if (!aClients.containsKey(pClient.getIdentifier())) {
            aClients.put(pClient.getIdentifier(), pClient);
        } else {
            throw new HubRegistrationException(pClient + " was already registered");
        }
        log(LogLevel.INFO, String.format("%s registered to Hub.", pClient));

      }catch(HubRegistrationException e) {
        log(LogLevel.ERROR, e.message());
      }
    }
    
    public void registerUser(Client c, User u) {
      assert c != null && u != null;
      
      try {
        if(aClients.containsKey(c.getIdentifier())) {
          if(!aUsers.containsKey(u.getIdentifier())) {
            aUsers.put(u.getIdentifier(), u);
          }
          c.registerUser(u);
        } else {
          throw new HubRegistrationException(String.format("Client %s is not registered.", c));
        }
        log(LogLevel.INFO, u + " has been registered to " + c);
      } catch(HubRegistrationException e) {
        log(LogLevel.ERROR, e.message());
      }
    }
    
    public void registerDevice(User u, Device d) {
      assert d != null && u != null;
      
      try {
        if(aUsers.containsKey(u.getIdentifier())) {
          if(!aDevices.containsKey(d.getIdentifier())) {
           throw new HubRegistrationException(String.format("%s is not registered.", d));
          }
          u.registerDevice(d);
        } else {
          throw new HubRegistrationException(String.format("%s is not registered.", u));
        }
        log(LogLevel.INFO, d + " has been registered to " + u);
      } catch(HubRegistrationException e) {
        log(LogLevel.ERROR, e.message());
      }
      
    }
    
    public void unregisterDevice(User u, Device d) throws HubRegistrationException {
      assert d != null && u != null;

      try {
        if(!aDevices.containsKey(d.getIdentifier())) throw new HubRegistrationException(String.format("%s is not registered.", d));
        if (!u.getDevices().contains(d))
          throw new HubRegistrationException(String.format("%s is not registered to %s.", d, u)) ;
        u.unregisterDevice(d);
        log(LogLevel.INFO, d + " has been unregistered from " + u);
      } catch (HubRegistrationException e) {
        log(LogLevel.ERROR, e.message());
      } 
    }
    
    public void unregisterUser(Client c, User u) throws HubRegistrationException {
      assert c != null && u != null;

      try {
        if(!aUsers.containsKey(u.getIdentifier())) throw new HubRegistrationException(String.format("%s is not registered.", u));
        if (!c.getUsers().contains(u))
          throw new HubRegistrationException(String.format("%s is not registered to %s.", u, c)) ;

        c.unregisterUser(u);
        log(LogLevel.INFO, u + " has been unregistered from " + c);
      } catch (HubRegistrationException e) {
        
        log(LogLevel.ERROR, e.message());
      } 
    }
    
    @Override
    public void unregister(Device device) throws HubRegistrationException {
      assert device != null;

      try {
        if (!aDevices.containsKey(device.getIdentifier())) {
          
            throw new HubRegistrationException(String.format("%s does not exists!", device));
        }
        aDevices.remove(device.getIdentifier());
        
        log(LogLevel.INFO, String.format("%s removed from Hub.",device));
      }catch(HubRegistrationException e) {
        
        log(LogLevel.ERROR, e.message());
      }
    }

    @Override
    public void unregister(Client client) throws HubRegistrationException {
      assert client != null;

      try {
        if (!aClients.containsKey(client.getIdentifier())) {
            throw new HubRegistrationException(String.format("%s does not exists!", client));
        }
        aClients.remove(client.getIdentifier());
        
        log(LogLevel.INFO, String.format("%s removed from Hub.", client));
      }catch(HubRegistrationException e) {
        
        log(LogLevel.ERROR, e.message());
      }
    }

    /**
     * Logging. Use SLF4J to write all message traffic to the log file.
     *
     * @param logMsg
     */
    public static void log(String logMsg) {
      
        LOGGER.info(logMsg);
    }
    
    public static void log(LogLevel l, String logMsg) {

      switch (l) {
        case INFO:
          LOGGER.info(logMsg);
          write(logMsg);
          break;
        case WARN:
          LOGGER.warn(logMsg);
          write(logMsg);
          break;
        case ERROR:
          LOGGER.error(logMsg);
          write(logMsg);
          break;
        case DEBUG:
          LOGGER.debug(logMsg);
          break;
        case TRACE:
          LOGGER.trace(logMsg);
          break;
        case NOTIFY:
          LOGGER.info("IMPORTANT: MAKE SURE NOTIFICATION WAS HANDLED PROPERLY\n"+logMsg);
          write(logMsg);
          break;
        default:
          break;
      }
    }
    
    private static void write(String s) {
      LOGWRITER.println(s);

    }

    @Override
    public void alert(Device pDevice, String pMessage) {
      
      for (Client c : aClients.values()) {
        for (User u : c.getUsers()) {
          if (u.getDevices().contains(pDevice)) {
            
            c.notify(u,
                new JSONMessaging(pDevice, pMessage).invoke());
          }
        }
      }
    }
    
    public void alert(LogLevel l, Device d, String message) {
      
      assert d != null;
      if (l == LogLevel.NOTIFY) {
        for (Client c : aClients.values()) {
          for (User u : c.getUsers()) {
            if (u.getDevices().contains(d)) {
              
              c.notify(u,
                  new JSONMessaging(d, message).invoke());
            }
          }
        }
      }
      log(l, message);
    }
    
    
    /**
     * 
     * If a device detects activity, it asks the Hub to check if any actions should be taken with the other devices
     * e.g. if Camera detects activity and Lightbulbs are off then turn lights on or 
     * if Camera doesn't detect activity and Lightbulbs are on then turn lights off or
     * User has settings that when Lightbulb turn on in a room and it is Winter, raise Thermostat or
     * etc.
     * 
     * @param activity -> was activityDetected?
     * @param pDevice -> the device that detected activity
     */
    public void dynamicActivity(boolean activity, Device pDevice) {
      
      for(User u: aUsers.values()) {
        if(u.getDevices().contains(pDevice)) {
          for(Device d: u.getDevices()) {
            if(pDevice.getDeviceTypeEnum() == DeviceType.CAMERA) {
                if(d.getDeviceTypeEnum() == DeviceType.LIGHTBULB) {
                  if(activity && d.isPowerOn()) {
                    log(LogLevel.WARN, "Lightbulb already on.");
                  }else if(activity && !d.isPowerOn()) {
                    d.toggle();
                  }else if(!activity && d.isPowerOn()) {
                    d.toggle();
                  }
                }
              }
            }
          }
        }
      }
    

    public Map<UUID, Device> getDevices() {
      
        return new HashMap<UUID, Device>(aDevices);
    }
}
