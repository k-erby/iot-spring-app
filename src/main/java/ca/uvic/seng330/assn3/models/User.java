package ca.uvic.seng330.assn3.models;

import ca.uvic.seng330.assn3.views.Client;

import java.util.HashMap;
import java.util.UUID;

public class User {

    private Boolean isAdmin;
    private String username;
    private String password;

    // username, password
    private HashMap<String, String> aUsers = new HashMap<String, String>();

    public User (String username, String password) {
        this.username = username;
        this.password = password;
        this.isAdmin = false;
        register();
    }

    public User (String username, String password, Boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        register();
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public Boolean checkPassword(String possiblePassword) {
        return possiblePassword.equals(this.password);
    }

    public String getUsername() {
        return username;
    }

    public void register() {
        this.aUsers.put(this.username, this.password);
    }

    public void unregister() {
        this.aUsers.remove(this.username, this.password);
    }

    public boolean signIn() {
        return true;
    }

    public boolean signOut() {
        return true;
    }
 }
