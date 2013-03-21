package sfrc.application.server.tasks;

import java.util.ArrayList;

import sfrc.application.server.IServerCommunicationProtocolTask;
import sfrc.application.server.IServerDataLibrary;
import sfrc.application.server.ServerCommunicator;
import shadow.system.data.SFDataset;

public class ReplyServerCommunicationTask implements IServerCommunicationProtocolTask{

	private ServerCommunicator communicator;
	private IServerDataLibrary library;
	
	public ReplyServerCommunicationTask(ServerCommunicator communicator,
			IServerDataLibrary library) {
		super();
		this.communicator = communicator;
		this.library = library;
	}

	@Override
	public String doTask(ArrayList<String> requests) {
		System.out.println(Thread.currentThread().getName() + " state: reply");
		while (!requests.isEmpty()) {
			String datasetName = requests.remove(0);
			SFDataset dataset = library.getDataset(datasetName);
			if (dataset!=null) {
				communicator.sendLine("reply");
				
				System.out.println(Thread.currentThread().getName() + " sent: reply ");
				//System.out.println(Thread.currentThread().getName() + " client response: " + communicator.readLine());
				
				communicator.readLine();
				communicator.sendLine(datasetName);
				System.out.println(Thread.currentThread().getName() + " sending: "+datasetName);
				
				
				communicator.readLine();
				
				communicator.sendDataset(dataset);
				System.out.println(Thread.currentThread().getName() + " sent: "+datasetName);
				
			} else {
				communicator.sendLine("fail");
				
				System.out.println(Thread.currentThread().getName() + " sent: fail ");
				//System.out.println(Thread.currentThread().getName() + " client response: " + communicator.readLine());
				
				communicator.readLine();
				communicator.sendLine(datasetName);
				System.out.println(Thread.currentThread().getName() + " fail: "+ datasetName);
			}
		}
		communicator.sendLine("reply-end");
		
		System.out.println(Thread.currentThread().getName() + " sent: reply-end ");
		communicator.readLine();
		return "idle";
	}
	
	public static String getTaskName() {
		return "reply";
	}
}
