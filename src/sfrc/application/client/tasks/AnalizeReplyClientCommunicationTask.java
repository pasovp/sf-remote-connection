package sfrc.application.client.tasks;

import java.util.StringTokenizer;

import sfrc.application.client.ClientCommunicationSessionData;
import sfrc.application.client.IClientCommunicationProtocolTask;

public class AnalizeReplyClientCommunicationTask implements IClientCommunicationProtocolTask {
	
	public AnalizeReplyClientCommunicationTask() {
		super();
	}
	
	@Override
	public String doTask(ClientCommunicationSessionData data) {
		// TODO: add better control on protocol messages 
		System.err.println(Thread.currentThread().getName() + " state: analize-reply");
		String message = data.getCommunicator().readLine();
		
		StringTokenizer tokenizer = new StringTokenizer(message,":");
		String token = tokenizer.nextToken();
		
		data.setMessage(message.replaceFirst(token+":", ""));
		
		return token;
	}
	
	public static String getTaskName() {
		return "analize-reply";
	}
}
