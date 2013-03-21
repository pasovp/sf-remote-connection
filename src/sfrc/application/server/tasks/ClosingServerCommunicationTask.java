package sfrc.application.server.tasks;

import java.util.ArrayList;

import sfrc.application.server.IServerCommunicationProtocolTask;
import sfrc.application.server.ServerCommunicator;

public class ClosingServerCommunicationTask implements IServerCommunicationProtocolTask{

	private ServerCommunicator communicator;
	
	public ClosingServerCommunicationTask(ServerCommunicator communicator) {
		super();
		this.communicator = communicator;
	}

	@Override
	public String doTask(ArrayList<String> requests) {
		System.out.println(Thread.currentThread().getName() + " state: closing");
		communicator.closeCommunication();
		return "close";
	}

	
	public static String getTaskName() {
		return "closing";
	}
}
