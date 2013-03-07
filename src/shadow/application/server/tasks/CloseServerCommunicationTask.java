package shadow.application.server.tasks;

import java.util.ArrayList;

import shadow.application.server.IServerCommunicationProtocolTask;
import shadow.application.server.ServerCommunicator;

public class CloseServerCommunicationTask implements IServerCommunicationProtocolTask{

	private ServerCommunicator communicator;
	
	public CloseServerCommunicationTask(ServerCommunicator communicator) {
		super();
		this.communicator = communicator;
	}

	@Override
	public String doTask(ArrayList<String> requests) {
		System.out.println(Thread.currentThread().getName() + " state: closing");
		communicator.closeComunication();
		return "close";
	}
}
