package sfrc.application.client;

import java.util.ArrayList;

import sfrc.base.communication.ICommunicationProtocolTask;

public interface IClientCommunicationProtocolTask extends ICommunicationProtocolTask {
	public String doTask(ArrayList<String> requests, ClientCommunicator communicator);
}
