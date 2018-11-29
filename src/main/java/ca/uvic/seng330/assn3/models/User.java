package ca.uvic.seng330.assn3.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.UUID;
import org.json.JSONObject;
import ca.uvic.seng330.assn3.models.devices.Device;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID uuid;

    private boolean isAdmin;
    private String username;
    private String password;

    @Transient
    private boolean isSignedIn = false;

    @Transient
    private ArrayList<Device> aDevices = new ArrayList<Device>();

    @Transient
    private Queue<JSONObject> notifications;

    // username, password
    @Transient
    private HashMap<String, String> aUsers = new HashMap<String, String>();

    protected User() {}
    
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

    public String getPassword() { return password; }
    
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
      if (b) {
        //TODO: register all devices to user
      } else {
        //TODO: unregister devices? manually?
      }
    }

    public void manageDevices(User u, Device d, boolean remove) {
      
      if (this.getIsAdmin()) {
        if (remove) {
          u.unregisterDevice(d); //TODO: make this happen in hub too. make static? or move this method in Hub
          }
        } else {
          u.registerDevice(d);
      }
    }
    
    public void manageUsers(User u, boolean remove) {
      
      if (this.getIsAdmin()) {
        if (remove) {
          u.unregister(); //TODO: same as above
        } else {
          u.register();
        }
      }
    }

    public boolean getPrivileges() {
      return isAdmin;
    }
    
    public boolean equals(User that) {
      
      if (this.getIdentifier() == that.getIdentifier()) return true;
      else return false;
    }
    
    @Override
    public String toString() {
        return String.format(
                "User[username=%d, ID='%s']",
                username, getIdentifier().toString());
    }
 }
