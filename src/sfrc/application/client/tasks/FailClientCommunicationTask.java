package sfrc.application.client.tasks;

import java.util.ArrayList;

import sfrc.application.client.ClientCommunicator;
import sfrc.application.client.IClientCommunicationProtocolTask;
import shadow.system.data.remote.wip.SFRemoteDataCenterRequests;

public class FailClientCommunicationTask implements IClientCommunicationProtocolTask {

	public FailClientCommunicationTask() {
		super();	
	}
	
	@Override
	public String doTask(ArrayList<String> requests, ClientCommunicator communicator) {
		
		String name = communicator.readLine();
		SFRemoteDataCenterRequests.getRequest().onRequestFail(name);
		requests.remove(name);
		return "analize-reply";
	}
	
	public static String getTaskName() {
		return "fail";
	}

}
