package ca.uvic.seng330.assn3.controllers.auth;

public interface AuthController {
    void register();

    void unregister();

    boolean signIn();

    boolean signOut();
}
