/**
 * 
 */
package sfrc.application.client;

import java.util.ArrayList;

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
		ArrayList<String> requests = new ArrayList<String>();
		
		while (!state.equalsIgnoreCase("close")) {
			
			
			IClientCommunicationProtocolTask task=protocol.getProtocolMap().get(state);
			if(task!=null){
				state = task.doTask(requests,communicator);
			} else {
				state = "closing";
				throw new RuntimeException(this.getClass().getSimpleName()+" - no task defined for state:" + state + " in comuunication protocol.");
			}
		}
	}
}
