package sfrc.application.server.tasks;

import java.util.ArrayList;
import java.util.StringTokenizer;

import sfrc.application.server.IServerCommunicationProtocolTask;
import sfrc.application.server.ServerCommunicator;

public class RequestServerCommunicationTask implements IServerCommunicationProtocolTask{

	private ServerCommunicator communicator;
	
	public RequestServerCommunicationTask(ServerCommunicator communicator) {
		super();
		this.communicator = communicator;
	}

	@Override
	public String doTask(ArrayList<String> requests) {
		System.out.println(Thread.currentThread().getName() + " state: request");
		String input = communicator.readLine();
		
		System.out.println(Thread.currentThread().getName() + " input: " + input);
		
		StringTokenizer tokenizer = new StringTokenizer(input, ",");
		if (tokenizer.hasMoreTokens()) {
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				requests.add(token);
			}
		} else {
			System.out.println(Thread.currentThread().getName() + " error: empty request");
		}
		return "reply";
	}
	
	public static String getTaskName() {
		return "request";
	}
}
