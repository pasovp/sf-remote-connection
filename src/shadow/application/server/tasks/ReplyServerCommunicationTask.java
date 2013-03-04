package shadow.application.server.tasks;

import java.util.ArrayList;

import shadow.application.server.IServerCommunicationTask;
import shadow.application.server.IServerDataLibrary;
import shadow.application.server.ServerCommunicator;
import shadow.system.data.SFDataset;

public class ReplyServerCommunicationTask implements IServerCommunicationTask{

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
				communicator.sendLine(datasetName);
				System.out.println(Thread.currentThread().getName() + " sending: "+datasetName);
				
				communicator.readLine();
				
				communicator.sendDataset(dataset);
				System.out.println(Thread.currentThread().getName() + " sent: "+datasetName);
				
			} else {
				communicator.sendLine("fail," + datasetName);
				System.out.println(Thread.currentThread().getName() + " fail: "+ datasetName);
			}
		}
		communicator.sendLine("idle");
		
		return "idle";
	}
}
