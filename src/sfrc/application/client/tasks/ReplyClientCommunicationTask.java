package sfrc.application.client.tasks;

import java.util.ArrayList;

import sfrc.application.client.ClientCommunicator;
import sfrc.application.client.IClientCommunicationProtocolTask;
import shadow.system.data.SFDataset;
import shadow.system.data.remote.wip.SFRemoteDataCenterRequests;

public class ReplyClientCommunicationTask implements IClientCommunicationProtocolTask {
	
	public ReplyClientCommunicationTask() {
		super();
	}
	
	@Override
	public String doTask(ArrayList<String> requests, ClientCommunicator communicator) {
		
		String name = communicator.readLine();
		communicator.sendLine("ok");
		SFDataset dataset = communicator.readDataset();
		SFRemoteDataCenterRequests.getRequest().onRequestUpdate(name,dataset);
		requests.remove(name);
		return "analize-reply";
	}
	
	public static String getTaskName() {
		return "reply";
	}

}
