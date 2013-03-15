package sfrc.application.server;

import java.util.ArrayList;

import sfrc.base.communication.ICommunicationProtocolTask;

public interface IServerCommunicationProtocolTask extends ICommunicationProtocolTask{
	public String doTask(ArrayList<String> requests);
}
