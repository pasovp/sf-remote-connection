package sfrc.base.communication;

import java.util.HashMap;

public class CommunicationProtocol<T extends ICommunicationProtocolTask> {
	private HashMap<String, T> protocol = new HashMap<String, T>();
	
	public HashMap<String, T> getProtocolMap() {
		return protocol;
	}
}
