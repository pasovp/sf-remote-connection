package sfrc.base.communication;

import java.util.HashMap;

public class CommunicationProtocol<T extends ICommunicationProtocolTask> {
	private HashMap<String, T> protocol = new HashMap<String, T>();
	private String initialState;
	
	public HashMap<String, T> getProtocolMap() {
		return protocol;
	}
	
	public void setInitialState(String initialState){
		if(protocol.containsKey(initialState)){
			this.initialState = initialState;
		} else {
			throw new RuntimeException("Illegal initial state: " + initialState + ", not already set in protocol map.");
		}
	}

	public String getInitialState() {
		return initialState;
	}
	
}
