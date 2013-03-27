package sfrc.application.server;

import sfrc.base.communication.ICommunicationProtocolTask;

public interface IServerCommunicationProtocolTask extends ICommunicationProtocolTask {
	public String doTask(ServerCommunicationSessionData data);
}
