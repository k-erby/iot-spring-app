package ca.uvic.seng330.assn3.models;

import java.util.Map;
import java.util.UUID;
import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.models.Hub.LogLevel;
import ca.uvic.seng330.assn3.models.devices.Camera;
import ca.uvic.seng330.assn3.models.devices.Device;
import ca.uvic.seng330.assn3.models.devices.Lightbulb;
import ca.uvic.seng330.assn3.views.Client;

public interface Mediator {

    void unregister(Device device) throws HubRegistrationException;

    void unregister(Client client) throws HubRegistrationException;

    //not in spec, do not test
    void register(Device pDevice) throws HubRegistrationException;

    void register(Client pClient) throws HubRegistrationException;

    void alert(Device pDevice, String pMessage);
    
    void alert(LogLevel l, Device pDevice, String pMessage);

    Map<UUID, Device> getDevices();

    void dynamicActivity(boolean b, Device d);

    void setRecentNotification(String s);

    String getRecentNotification();

    void registerDevice(User currentUser, Device device);

    void unregisterDevice(User currentUser, Device device) throws HubRegistrationException;

    void shutdown();

    void shutdown(User currentUser);
}
