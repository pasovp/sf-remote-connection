package shadow.application.server;

import java.util.ArrayList;

import shadow.underdevelopment.ICommunicationProtocolTask;

public interface IServerCommunicationProtocolTask extends ICommunicationProtocolTask{
	public String doTask(ArrayList<String> requests);
}
