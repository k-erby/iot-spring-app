package ca.uvic.seng330.assn3.views;

import org.json.JSONObject;
import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.models.Mediator;
import ca.uvic.seng330.assn3.models.User;

public class WebClient extends Client {

  private final Mediator aMed;

  public WebClient(Mediator pMed) {
    aMed = pMed;
    try {
      aMed.register(this);
    } catch (HubRegistrationException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void notify(User u, JSONObject json) {
    super.notify(u, json);

    if (u.signedIn()) display(u.getNotif());
  }

  // displays notification immediately for current user
  private void display(JSONObject json) {
    System.out.println("WebClient is displaying content from : " + json.getString("node_id"));
    String s =
        String.format(
            "%s\n%s\n%s",
            json.getString("node_id"), json.getString("created_at"), json.getString("payload"));
    aMed.setRecentNotification(s);
  }

  // display all pending notifications for a user
  public void displayAll(User u) {

    do {

      display(u.getNotif());

    } while (u.getNotif() != null);
  }

  @Override
  public String toString() {
    String className = getClass().getSimpleName();
    return className + " " + getIdentifier().toString();
  }
}
