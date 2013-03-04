package shadow.underdevelopment;

import java.util.HashMap;

import shadow.application.server.IServerCommunicationTask;

public class CommunicationProtocol<T extends ICommunicationProtocolTask> {
	private HashMap<String, T> protocol = new HashMap<String, T>();
	
	public HashMap<String, T> getProtocolMap() {
		return protocol;
	}
}
