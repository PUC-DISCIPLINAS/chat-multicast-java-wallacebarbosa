package entities;

import java.io.Serializable;

import enums.ResponseCodes;
import interfaces.ConvertSerializable;

public class ResponseBody extends ConvertSerializable<ResponseBody>
implements Serializable {
	
	private static final long serialVersionUID = 1L;
	  private ResponseCodes ResponseMsg;
	  private String body;
	  
	public ResponseBody(ResponseCodes responseMsg, String body) {
		super();
		ResponseMsg = responseMsg;
		this.body = body;
	}
	
	public ResponseBody() {
		super();
		
	}

	public ResponseCodes getResponseMsg() {
		return ResponseMsg;
	}

	public void setResponseMsg(ResponseCodes responseMsg) {
		ResponseMsg = responseMsg;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
	  
	  

}
