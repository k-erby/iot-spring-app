package ca.uvic.seng330.assn3.views;

import java.util.ArrayList;
import java.util.UUID;
import org.json.JSONObject;
import ca.uvic.seng330.assn3.models.User;

public abstract class Client {

  private final UUID uuid = UUID.randomUUID();
  private ArrayList<User> users;

  public UUID getIdentifier() {
    return uuid;
  }

  public void notify(User u, JSONObject json) {
    assert users.contains(u);
    u.newNotif(json);
  }

  public void registerUser(User u) {
    assert !users.contains(u);
    users.add(u);
  }

  public void unregisterUser(User u) {
    assert users.contains(u);
    users.remove(u);
  }

  public void setUsrPrivs(User u, boolean b) {

    assert users.contains(u);
    u.setPrivileges(b);
  }

  public boolean getUsrPrivs(User u) {

    assert users.contains(u);
    return u.getPrivileges();
  }

  public ArrayList<User> getUsers() {

    return new ArrayList<User>(users);
  }

  public boolean equals(Client that) {

    if (this.getIdentifier() == that.getIdentifier()) return true;
    else return false;
  }

  @Override
  public String toString() {
    String className = getClass().getSimpleName();
    return className + " " + getIdentifier().toString();
  }
}
