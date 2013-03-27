package sfrc.application.client.tasks;

import sfrc.application.client.ClientCommunicationSessionData;
import sfrc.application.client.IClientCommunicationProtocolTask;

public class ClosingClientCommunicationTask implements IClientCommunicationProtocolTask {

	public ClosingClientCommunicationTask() {
		super();
	}

	@Override
	public String doTask(ClientCommunicationSessionData data) {
		System.err.println(Thread.currentThread().getName() + " state: closing");

		data.getCommunicator().sendLine("closing");
		
		
		data.getCommunicator().closeCommunication();
		
		System.err.println(Thread.currentThread().getName() + " state: communication closed");
		return "close";
	}
	
	public static String getTaskName() {
		return "closing";
	}

}
