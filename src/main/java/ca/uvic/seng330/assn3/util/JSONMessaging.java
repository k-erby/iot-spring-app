package ca.uvic.seng330.assn3.util;

import java.util.Date;
import org.json.JSONObject;
import ca.uvic.seng330.assn3.models.devices.Device;

public class JSONMessaging extends JSONObject {

  private JSONObject json;

  public JSONMessaging(Device d, String s) {

    json = new JSONObject();
    json.put("msg_id", 1);
    json.put("node_id", d.getIdentifier());
    json.put("status", d.getStatus());
    json.put("payload", s);
    json.put("created_at", new Date().toString());
  }
  
  public JSONMessaging(String s) {

    json = new JSONObject();
    json.put("msg_id", 1);
    json.put("payload", s);
    json.put("created_at", new Date().toString());
  }

  public JSONObject invoke() {

    return json;
  }
}