package ca.uvic.seng330.assn3.models;

import java.util.Map;
import java.util.UUID;
import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.models.Hub.LogLevel;
import ca.uvic.seng330.assn3.models.devices.Device;
import ca.uvic.seng330.assn3.views.Client;

public interface Mediator {

    //not in spec, do not test
    void register(Device pDevice) throws HubRegistrationException;

    void register(Client pClient) throws HubRegistrationException;
    
    void registerUser(Client instance, User currentUser) throws HubRegistrationException;
    
    void registerDevice(User currentUser, Device device) throws HubRegistrationException; 

    void unregisterDevice(User currentUser, Device device) throws HubRegistrationException;
    
    void unregisterUser(Client instance, User currentUser) throws HubRegistrationException;
    
    void unregister(Device device) throws HubRegistrationException;

    void unregister(Client client) throws HubRegistrationException;

    void alert(Device pDevice, String pMessage);
    
    void alert(LogLevel l, Device pDevice, String pMessage);

    Map<UUID, Device> getDevices();

    void dynamicActivity(boolean b, Device d);

    void shutdown();

    Client getInstance();
}
