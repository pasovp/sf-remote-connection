package sfrc.application.server.tasks;

import sfrc.application.server.IServerCommunicationProtocolTask;
import sfrc.application.server.IServerDataLibrary;
import sfrc.application.server.ServerCommunicationSessionData;
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
	public String doTask(ServerCommunicationSessionData data) {
		System.out.println(Thread.currentThread().getName() + " state: reply");
		
		while (!data.getRequests().isEmpty()) {
			String datasetName = data.getRequests().remove(0);
			
			SFDataset dataset = library.getDataset(datasetName);
			if (dataset!=null) {
				communicator.sendLine("reply:"+datasetName);
				System.out.println(Thread.currentThread().getName() + " sending: "+datasetName);
				
				communicator.readLine();
				
				communicator.sendDataset(dataset);
				System.out.println(Thread.currentThread().getName() + " sent: "+datasetName);
				
			} else {
				communicator.sendLine("fail:"+datasetName);
				System.out.println(Thread.currentThread().getName() + " sent: fail "+ datasetName);
			}
		}
		communicator.sendLine("reply-end");
		
		System.out.println(Thread.currentThread().getName() + " sent: reply-end ");
		
		return AnalizeRequestServerCommunicationTask.getTaskName();
	}
	
	public static String getTaskName() {
		return "reply";
	}
}
