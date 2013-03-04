/**
 * 
 */
package shadow.application.server;

import java.util.ArrayList;

import shadow.application.server.tasks.CloseServerCommunicationTask;
import shadow.application.server.tasks.IdleServerCommunicationTask;
import shadow.application.server.tasks.ReplyServerCommunicationTask;
import shadow.underdevelopment.CommunicationProtocol;
import shadow.underdevelopment.SFConnection;

/**
 * @author Luigi Pasotti
 */
public class ServerCommunicationTask implements Runnable {
	
	private String state = "idle";

	private CommunicationProtocol<IServerCommunicationTask> protocol = new CommunicationProtocol<IServerCommunicationTask>();
	/**
	 * @param connection
	 */
	public ServerCommunicationTask(SFConnection connection, IServerDataLibrary library) {
		super();
		ServerCommunicator communicator = new ServerCommunicator(connection);
		
		protocol.getProtocolMap().put("idle",  new IdleServerCommunicationTask(communicator));
		protocol.getProtocolMap().put("reply",  new ReplyServerCommunicationTask(communicator, library));
		protocol.getProtocolMap().put("closing",  new CloseServerCommunicationTask(communicator));
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		ArrayList<String> requests = new ArrayList<String>();
	
		while (!state.equalsIgnoreCase("close")) {
			
			IServerCommunicationTask task=protocol.getProtocolMap().get(state);
			
			if(task!=null){
				state = task.doTask(requests);
			}
		}

	}
}
