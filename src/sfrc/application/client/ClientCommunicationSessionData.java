package sfrc.application.client;

import java.util.ArrayList;


public class ClientCommunicationSessionData {
	private ArrayList<String> requests = new ArrayList<String>();
	private ClientCommunicator communicator;
	private String message = new String();
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ArrayList<String> getRequests() {
		return requests;
	}
	public void setRequests(ArrayList<String> requests) {
		this.requests = requests;
	}
	public ClientCommunicator getCommunicator() {
		return communicator;
	}
	public void setCommunicator(ClientCommunicator communicator) {
		this.communicator = communicator;
	}
}
