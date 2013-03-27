/**
 * 
 */
package sfrc.application.client;

import sfrc.base.communication.CommunicationProtocol;

/**
 * @author Luigi Pasotti
 *
 */
public class RemoteDataCenterRequestTask implements Runnable {

	private CommunicationProtocol<IClientCommunicationProtocolTask> protocol;
	private ClientCommunicator communicator;
	private String state = "request";
	
	
	public RemoteDataCenterRequestTask(CommunicationProtocol<IClientCommunicationProtocolTask> protocol,  ClientCommunicator communicator) {
		super();
		this.protocol = protocol;
		this.communicator = communicator;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {		
		
		ClientCommunicationSessionData data = new ClientCommunicationSessionData();
		data.setCommunicator(communicator);
		
		while (!state.equalsIgnoreCase("close")) {
			
			
			IClientCommunicationProtocolTask task=protocol.getProtocolMap().get(state);
			if(task!=null){
				state = task.doTask(data);
			} else {
				state = "closing";
				throw new RuntimeException(this.getClass().getSimpleName()+" - no task defined for state:" + state + " in comuunication protocol.");
			}
		}
	}
}
