package entities;

import interfaces.ConvertSerializable;
import java.io.Serializable;

public class RequestBody
  extends ConvertSerializable<RequestBody>
  implements Serializable {

  private static final long serialVersionUID = 1L;
  public String protocol;
  public String body;
  public String clientId;

  public RequestBody(String protocol, String clientId, String body) {
    this.body = body;
    this.protocol = protocol;
    this.clientId = clientId;
  }
  
  public RequestBody(String protocol, String clientId) {
	    this.body = null;
	    this.protocol = protocol;
	    this.clientId = clientId;
	  }
}
