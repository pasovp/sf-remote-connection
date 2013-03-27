package sfrc.application.client.tasks;

import sfrc.application.client.ClientCommunicationSessionData;
import sfrc.application.client.IClientCommunicationProtocolTask;
import shadow.system.data.remote.wip.SFRemoteDataCenterRequests;

public class RequestClientCommunicationTask implements IClientCommunicationProtocolTask {
	
	public RequestClientCommunicationTask() {
		super();
	}

	@Override
	public String doTask(ClientCommunicationSessionData data) {
		System.err.println(Thread.currentThread().getName() + " state: request");

		String requestString = new String("request:");
		
		data.getRequests().addAll(SFRemoteDataCenterRequests.getRequest().removeRequests());
		
		
		requestString = requestString.concat(data.getRequests().remove(0));
		
		for (String req : data.getRequests()) {
			requestString = requestString.concat("," + req);
		}
		data.getCommunicator().sendLine(requestString);
		return "analize-reply";
	}
	
	public static String getTaskName() {
		return "request";
	}
}
