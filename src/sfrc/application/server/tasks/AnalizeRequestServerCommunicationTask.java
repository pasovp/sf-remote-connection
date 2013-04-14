package sfrc.application.server.tasks;

import java.util.StringTokenizer;

import sfrc.application.server.IServerCommunicationProtocolTask;
import sfrc.application.server.ServerCommunicationSessionData;
import sfrc.application.server.ServerCommunicator;

public class AnalizeRequestServerCommunicationTask implements IServerCommunicationProtocolTask{

	private ServerCommunicator communicator;
	
	public AnalizeRequestServerCommunicationTask(ServerCommunicator communicator) {
		super();
		this.communicator = communicator;
	}
	
	@Override
	public String doTask(ServerCommunicationSessionData data){

		System.out.println(Thread.currentThread().getName() + " state: analize-request");
		String message = communicator.readLine();
		
		StringTokenizer tokenizer = new StringTokenizer(message,":");
		String token = tokenizer.nextToken();
		
		//communicator.sendLine(input+":ack");
		System.out.println(Thread.currentThread().getName() + " input: " + token);
		data.setMessage(message.replaceFirst(token+":", ""));
		
		return token;
	}
	
	public static String getTaskName() {
		return "analize-request";
	}
}
