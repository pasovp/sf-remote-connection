package sfrc.application.client.tasks;

import java.util.ArrayList;

import sfrc.application.client.ClientCommunicator;
import sfrc.application.client.IClientCommunicationProtocolTask;

public class ReplyEndClientCommunicationTask implements IClientCommunicationProtocolTask {
	
	public ReplyEndClientCommunicationTask() {
		super();
	}
	
	@Override
	public String doTask(ArrayList<String> requests, ClientCommunicator communicator) {
		
		return "closing";
	}
	
	public static String getTaskName() {
		return "reply-end";
	}

}
