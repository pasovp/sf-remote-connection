package sfrc.application.client.tasks;

import sfrc.application.client.ClientCommunicationSessionData;
import sfrc.application.client.IClientCommunicationProtocolTask;
import shadow.system.data.SFDataset;
import shadow.system.data.remote.wip.SFRemoteDataCenterRequests;

public class ReplyClientCommunicationTask implements IClientCommunicationProtocolTask {
	
	public ReplyClientCommunicationTask() {
		super();
	}
	
	@Override
	public String doTask(ClientCommunicationSessionData data) {
		System.err.println(Thread.currentThread().getName() + " state: reply");
		String name = data.getMessage();
		
		System.err.println(Thread.currentThread().getName() + " ready for: " + name);
		data.getCommunicator().sendLine("ok");
		
		System.err.println(Thread.currentThread().getName() + " receiving: " + name);
		SFDataset dataset = data.getCommunicator().readDataset();
		
		System.err.println(Thread.currentThread().getName() + " received: " + name);
		SFRemoteDataCenterRequests.getRequest().onRequestUpdate(name,dataset);
		data.getRequests().remove(name);
		data.setMessage("");
		System.err.println(Thread.currentThread().getName() + " end: " + name);
		return "analize-reply";
	}
	
	public static String getTaskName() {
		return "reply";
	}

}
