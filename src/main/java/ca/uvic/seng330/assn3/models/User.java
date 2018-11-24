package ca.uvic.seng330.assn3.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.UUID;
import org.json.JSONObject;
import ca.uvic.seng330.assn3.models.devices.Device;

public class User {

    private boolean isAdmin;
    private boolean isSignedIn = false;
    private String username;
    private String password;
    private UUID uuid;
    private ArrayList<Device> aDevices = new ArrayList<Device>(); 
    //private Queue<String> notifications;
    private Queue<JSONObject> notifications;

    // username, password
    private HashMap<String, String> aUsers = new HashMap<String, String>();
    
    public User (String username) {
      this.username = username;
      this.password = UUID.randomUUID().toString(); //autogenerate password
      uuid = UUID.randomUUID();
      this.isAdmin = false;
      register();
    }


    public User (String username, String password) {
        this.username = username;
        this.password = password;
        uuid = UUID.randomUUID();
        this.isAdmin = false;
        register();
    }

    public User (String username, String password, Boolean isAdmin) {
        this.username = username;
        this.password = password;
        uuid = UUID.randomUUID();
        this.isAdmin = isAdmin;
        register();
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public boolean checkPassword(String possiblePassword) {
        return possiblePassword.equals(this.password);
    }

    public String getUsername() {
        return username;
    }
    
    public UUID getIdentifier() {
      return uuid;
    }

    public void register() {
        this.aUsers.put(this.username, this.password);
    }

    public void unregister() {
        this.aUsers.remove(this.username, this.password);
    }
    
    public void registerDevice(Device d) {
      assert !aDevices.contains(d);
      aDevices.add(d);
    }
    
    public void unregisterDevice(Device d) {
      assert aDevices.contains(d);
      aDevices.remove(d);
    }
    
    public ArrayList<Device> getDevices(){
      return new ArrayList<Device>(aDevices);
    }
    
    public void newNotif(JSONObject j) {
      
      notifications.add(j);
    }
    
    public JSONObject getNotif() {
      return notifications.poll();
    }

    public void signIn() {
        isSignedIn = true;
    }

    public void signOut() {
        isSignedIn = false;
    }

    public boolean signedIn() {
        return isSignedIn;
    }

    public void setPrivileges(boolean b) {
      isAdmin = b;
    }


    public boolean getPrivileges() {
      return isAdmin;
    }
    
    public boolean equals(User that) {
      
      if(this.getIdentifier() == that.getIdentifier()) return true;
      else return false;
    }
    
    @Override
    public String toString() {
      return "User: "+username+" ID: "+ getIdentifier().toString();
    }


   
    
 }
