package sfrc.application.client.tasks;

import java.util.ArrayList;

import sfrc.application.client.ClientCommunicator;
import sfrc.application.client.IClientCommunicationProtocolTask;
import shadow.system.data.remote.wip.SFRemoteDataCenterRequests;

public class RequestClientCommunicationTask implements IClientCommunicationProtocolTask {
	
	public RequestClientCommunicationTask() {
		super();
	}

	@Override
	public String doTask(ArrayList<String> requests, ClientCommunicator communicator) {
		communicator.sendLine("request");
		communicator.readLine();
		
		requests.addAll(SFRemoteDataCenterRequests.getRequest().removeRequests());
		String requestString = requests.remove(0);
		
		for (String req : requests) {
			requestString = requestString.concat("," + req);
		}
		communicator.sendLine(requestString);
		return "analize-reply";
	}
	
	public static String getTaskName() {
		return "request";
	}
}
