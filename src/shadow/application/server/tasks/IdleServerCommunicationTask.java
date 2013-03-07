package shadow.application.server.tasks;

import java.util.ArrayList;
import java.util.StringTokenizer;

import shadow.application.server.IServerCommunicationProtocolTask;
import shadow.application.server.ServerCommunicator;

public class IdleServerCommunicationTask implements IServerCommunicationProtocolTask{

	private ServerCommunicator communicator;
	
	public IdleServerCommunicationTask(ServerCommunicator communicator) {
		super();
		this.communicator = communicator;
	}

	public String doTask(ArrayList<String> requests){
		String nextState = "idle";
		System.out.println(Thread.currentThread().getName() + " state: idle");
		String input = communicator.readLine();
		System.out.println(Thread.currentThread().getName() + " input: "+input);
		if ( (input==null) || (input.compareTo("close") == 0) ) {	
			nextState = "closing";
		} else {
			StringTokenizer tokenizer = new StringTokenizer(input, ",");
			if (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token.compareTo("request") == 0) {
					while (tokenizer.hasMoreTokens()) {
						token = tokenizer.nextToken();
						requests.add(token);
						nextState = "reply";
					}
				} else {
					System.out.println(Thread.currentThread().getName() + " error: not a request");
				}
			}
		}
		
		return nextState;
	}
}
