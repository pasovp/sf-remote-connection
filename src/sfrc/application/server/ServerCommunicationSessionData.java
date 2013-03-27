package sfrc.application.server;

import java.util.ArrayList;

public class ServerCommunicationSessionData {
	private ArrayList<String> requests = new ArrayList<String>();
	private String message = new String();
	
	public ArrayList<String> getRequests() {
		return requests;
	}
	public void setRequests(ArrayList<String> requests) {
		this.requests = requests;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
