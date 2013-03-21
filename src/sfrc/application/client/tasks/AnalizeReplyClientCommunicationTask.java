package sfrc.application.client.tasks;

import java.util.ArrayList;

import sfrc.application.client.ClientCommunicator;
import sfrc.application.client.IClientCommunicationProtocolTask;

public class AnalizeReplyClientCommunicationTask implements IClientCommunicationProtocolTask {
	
	public AnalizeReplyClientCommunicationTask() {
		super();
	}
	
	@Override
	public String doTask(ArrayList<String> requests, ClientCommunicator communicator) {
		// TODO: add better control on protocol messages 
		String input = communicator.readLine();
		communicator.sendLine(input+":ack");
		return input;
	}
	
	public static String getTaskName() {
		return "analize-reply";
	}

}
