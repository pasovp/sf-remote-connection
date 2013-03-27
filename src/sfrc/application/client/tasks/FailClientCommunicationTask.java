package sfrc.application.client.tasks;

import sfrc.application.client.ClientCommunicationSessionData;
import sfrc.application.client.IClientCommunicationProtocolTask;
import shadow.system.data.remote.wip.SFRemoteDataCenterRequests;

public class FailClientCommunicationTask implements IClientCommunicationProtocolTask {

	public FailClientCommunicationTask() {
		super();	
	}
	
	@Override
	public String doTask(ClientCommunicationSessionData data) {
		System.err.println(Thread.currentThread().getName() + " state: fail");

		String name = data.getMessage();
		SFRemoteDataCenterRequests.getRequest().onRequestFail(name);
		data.getRequests().remove(name);
		data.setMessage("");
		return "analize-reply";
	}
	
	public static String getTaskName() {
		return "fail";
	}

}
