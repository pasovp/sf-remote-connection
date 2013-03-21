package sfrc.application.client.tasks;

import java.util.ArrayList;

import sfrc.application.client.ClientCommunicator;
import sfrc.application.client.IClientCommunicationProtocolTask;

public class ClosingClientCommunicationTask implements IClientCommunicationProtocolTask {

	public ClosingClientCommunicationTask() {
		super();
	}

	@Override
	public String doTask(ArrayList<String> requests, ClientCommunicator communicator) {
		communicator.sendLine("closing");
		communicator.readLine();
		communicator.closeCommunication();
		return "close";
	}
	
	public static String getTaskName() {
		return "closing";
	}

}
