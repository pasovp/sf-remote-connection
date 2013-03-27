package sfrc.application.client.tasks;

import sfrc.application.client.ClientCommunicationSessionData;
import sfrc.application.client.IClientCommunicationProtocolTask;

public class ReplyEndClientCommunicationTask implements IClientCommunicationProtocolTask {
	
	public ReplyEndClientCommunicationTask() {
		super();
	}
	
	@Override
	public String doTask(ClientCommunicationSessionData data) {
		System.err.println(Thread.currentThread().getName() + " state: reply-end");

		return "closing";
	}
	
	public static String getTaskName() {
		return "reply-end";
	}

}
