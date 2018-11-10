package ca.uvic.seng330.assn3.Models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ca.uvic.seng330.assn3.Controllers.Device;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uvic.seng330.assn3..WebClient;

public class Hub extends Device implements Mediator {

    private HashMap<UUID, Device> aDevices = new HashMap<UUID, Device>();
    private HashMap<UUID, Client> aClients = new HashMap<UUID, Client>();
    private Logger logger = LoggerFactory.getLogger(Hub.class);

    public void startup() {
        // some logic about sending init messages or somethng.
    }

    public void shutdown() {}

    @Override
    public void register(Device pDevice) throws HubRegistrationException {
        if (!aDevices.containsKey(pDevice.getIdentifier())) {
            aDevices.put(pDevice.getIdentifier(), pDevice);
        } else {
            throw new HubRegistrationException(pDevice + " was already registered");
        }
    }

    @Override
    public void register(Client pClient) throws HubRegistrationException {
        if (!aClients.containsKey(pClient.getIdentifier())) {
            aClients.put(pClient.getIdentifier(), pClient);
        } else {
            throw new HubRegistrationException(pClient + " was already registered");
        }
    }

    @Override
    public void unregister(Device device) throws HubRegistrationException {
        if (!aDevices.containsKey(device.getIdentifier())) {
            log("Unknown Device unregister");
            throw new HubRegistrationException("Device does not exists!");
        }
        aDevices.remove(device.getIdentifier());
        log("Device removed " + device);
    }

    @Override
    public void unregister(Client client) throws HubRegistrationException {
        if (!aClients.containsKey(client.getIdentifier())) {
            throw new HubRegistrationException("Client does not exists!");
        }
        aClients.remove(client.getIdentifier());
    }

    /**
     * Logging. Use SLF4J to write all message traffic to the log file.
     *
     * @param logMsg
     */
    public void log(String logMsg) {
        logger.info(logMsg);
    }

    /**
     * Alerts are events that happen at the Device level. They send the alert to the hUb, which
     * redistributes to the clients
     *
     * @param pMessage
     */
    @Override
    public void alert(Device pDevice, String pMessage) {

        // initialize the map
        JSONObject jsonMessage = new JSONMessaging(pDevice, pMessage).invoke();

        // ordinarily, we would have logic for which clients to notify
        notifyClients(jsonMessage);
        log("ALERT msg: " + pMessage + " - from Device " + pDevice.toString());
    }

    private void notifyClients(JSONObject pMsg) {
        for (Client c : aClients.values()) {
            c.notify(pMsg);
            log("Notified: " + c.toString());
        }
    }

    public Map<UUID, Device> getDevices() {
        return new HashMap<UUID, Device>(aDevices);
    }
}