package ca.uvic.seng330.assn3.controllers.users;

public interface UserController {
    void register();

    void unregister();

    boolean signIn();

    boolean signOut();
}
