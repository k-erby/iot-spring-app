package ca.uvic.seng330.assn3.models;

import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.models.devices.Device;
import ca.uvic.seng330.assn3.views.Client;

import java.util.Map;
import java.util.UUID;

public interface Mediator {

    int getTest();

    void unregister(Device device) throws HubRegistrationException;

    void unregister(Client client) throws HubRegistrationException;

    //not in spec, do not test
    void register(Device pDevice) throws HubRegistrationException;

    void register(Client pClient) throws HubRegistrationException;

    void alert(Device pDevice, String pMessage);

    Map<UUID, Device> getDevices();
}
