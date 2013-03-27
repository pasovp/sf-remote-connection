/**
 * 
 */
package sfrc.application.server;

import sfrc.base.communication.CommunicationProtocol;

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
		ServerCommunicationSessionData data= new ServerCommunicationSessionData();
		while (!state.equalsIgnoreCase("close")) {
			
			IServerCommunicationProtocolTask task=protocol.getProtocolMap().get(state);
			if(task!=null){
				state = task.doTask(data);
			} else {
				//state = "idle";
				throw new RuntimeException(this.getClass().getSimpleName()+" - no task defined for state:" + state + " in communication protocol.");
			}
		}

	}
}
