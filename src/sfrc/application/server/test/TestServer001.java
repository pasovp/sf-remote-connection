package sfrc.application.server.test;

import java.io.IOException;

import sfrc.application.server.ServerCommunicator;
import sfrc.application.server.tasks.CloseServerCommunicationTask;
import sfrc.application.server.tasks.IdleServerCommunicationTask;
import sfrc.application.server.tasks.ReplyServerCommunicationTask;

public class TestServer001 extends AbstractTestServer {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		execute(new TestServer001());
	}

	@Override
	public void setupProtocol(ServerCommunicator communicator) {
		getProtocol().getProtocolMap().put("idle",  new IdleServerCommunicationTask(communicator));
		getProtocol().getProtocolMap().put("reply",  new ReplyServerCommunicationTask(communicator, getDataHandler()));
		getProtocol().getProtocolMap().put("closing",  new CloseServerCommunicationTask(communicator));
	}

}
