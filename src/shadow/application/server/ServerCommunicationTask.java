/**
 * 
 */
package shadow.application.server;

import java.util.ArrayList;

import shadow.underdevelopment.CommunicationProtocol;

/**
 * @author Luigi Pasotti
 */
public class ServerCommunicationTask implements Runnable {
	
	private String state = "idle";
	private CommunicationProtocol<IServerCommunicationProtocolTask> protocol;
	
	
	/**
	 * @param connection
	 */
	public ServerCommunicationTask(CommunicationProtocol<IServerCommunicationProtocolTask> protocol) {
		super();
		this.protocol = protocol;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		ArrayList<String> requests = new ArrayList<String>();
	
		while (!state.equalsIgnoreCase("close")) {
			
			IServerCommunicationProtocolTask task=protocol.getProtocolMap().get(state);
			if(task!=null){
				state = task.doTask(requests);
			}
		}

	}
}
