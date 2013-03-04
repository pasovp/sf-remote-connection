package shadow.application.server;

import java.util.ArrayList;

import shadow.underdevelopment.ICommunicationProtocolTask;

public interface IServerCommunicationTask extends ICommunicationProtocolTask{
	public String doTask(ArrayList<String> requests);
}
