package ca.uvic.seng330.assn3.models;

public class User {

    private Boolean isAdmin;
    private String name;

    public User (String name) {
        this.name = name;
        this.isAdmin = false;
    }

    public User (String name, Boolean isAdmin) {
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public String getName() {
        return name;
    }

    public void register() {

    }

    public void unregister() {

    }

    public boolean signIn() {
        return true;
    }

    public boolean signOut() {
        return true;
    }
 }
