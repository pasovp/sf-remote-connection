package sfrc.application.server.tasks;

import java.util.StringTokenizer;

import sfrc.application.server.IServerCommunicationProtocolTask;
import sfrc.application.server.ServerCommunicationSessionData;

public class RequestServerCommunicationTask implements IServerCommunicationProtocolTask{

	
	public RequestServerCommunicationTask() {
		super();
		
	}

	@Override
	public String doTask(ServerCommunicationSessionData data) {
		System.out.println(Thread.currentThread().getName() + " state: request");
		//String input = communicator.readLine();
		StringTokenizer tokenizer = new StringTokenizer(data.getMessage(),":");
		String reqString = tokenizer.nextToken();
		
		System.out.println(Thread.currentThread().getName() + " input: " + reqString);
		
		tokenizer = new StringTokenizer(reqString, ",");
		if (tokenizer.hasMoreTokens()) {
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				data.getRequests().add(token);
			}
		} else {
			System.out.println(Thread.currentThread().getName() + " error: empty request");
		}
		
		data.setMessage(data.getMessage().replaceFirst(reqString+":", ""));
		return ReplyServerCommunicationTask.getTaskName();
	}
	
	public static String getTaskName() {
		return "request";
	}
}
