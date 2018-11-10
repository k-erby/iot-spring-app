package ca.uvic.seng330.assn3.Models;

import ca.uvic.seng330.assn3.Controllers.Devices;
import ca.uvic.seng330.assn3.Views.Client;

public interface Mediator {

    void unregister(Device device) throws HubRegistrationException;

    void unregister(Client client) throws HubRegistrationException;

    //not in spec, do not test
    void register(Device pDevice) throws HubRegistrationException;

    void register(Client pClient) throws HubRegistrationException;

    void alert(Device pDevice, String pMessage);
}