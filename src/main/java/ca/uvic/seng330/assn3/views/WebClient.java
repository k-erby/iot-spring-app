package ca.uvic.seng330.assn3.views;

import ca.uvic.seng330.assn3.exceptions.HubRegistrationException;
import ca.uvic.seng330.assn3.models.Mediator;
import org.json.JSONObject;

public class WebClient extends Client {

    private final Mediator aMed;
    private JSONObject aJsonObj;

    public WebClient(Mediator pMed) {
        aMed = pMed;
        try {
            aMed.register(this);
        } catch (HubRegistrationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notify(JSONObject json) {
        super.notify(json);
        this.aJsonObj = json;
        display();
    }

    private void display() {
        System.out.println("WebClient is displaying content from : " + aJsonObj.getString("node_id"));
        //TODO  should be on web page
    }

    @Override
    public String toString() {
        return "WebClient: " + getIdentifier().toString();
    }
}
