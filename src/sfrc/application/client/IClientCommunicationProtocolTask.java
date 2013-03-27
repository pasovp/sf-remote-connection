package sfrc.application.client;

import sfrc.base.communication.ICommunicationProtocolTask;

public interface IClientCommunicationProtocolTask extends ICommunicationProtocolTask {
	public String doTask(ClientCommunicationSessionData data);
}
