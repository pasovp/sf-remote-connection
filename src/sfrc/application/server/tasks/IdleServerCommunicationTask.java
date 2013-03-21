package sfrc.application.server.tasks;

import java.util.ArrayList;

import sfrc.application.server.IServerCommunicationProtocolTask;
import sfrc.application.server.ServerCommunicator;

public class IdleServerCommunicationTask implements IServerCommunicationProtocolTask{

	private ServerCommunicator communicator;
	
	public IdleServerCommunicationTask(ServerCommunicator communicator) {
		super();
		this.communicator = communicator;
	}

	public String doTask(ArrayList<String> requests){

		
		System.out.println(Thread.currentThread().getName() + " state: idle");
		String input = communicator.readLine();
		communicator.sendLine(input+":ack");
		System.out.println(Thread.currentThread().getName() + " input: " + input);
		
		return input;
	}
	
	public static String getTaskName() {
		return "idle";
	}
}
