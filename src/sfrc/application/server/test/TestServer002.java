package sfrc.application.server.test;

import java.io.IOException;

import sfrc.application.server.ServerCommunicator;
import sfrc.application.server.tasks.ClosingServerCommunicationTask;
import sfrc.application.server.tasks.IdleServerCommunicationTask;
import sfrc.application.server.tasks.ReplyServerCommunicationTask;
import sfrc.application.server.tasks.RequestServerCommunicationTask;

public class TestServer002 extends AbstractTestServer {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		execute(new TestServer002());
	}

	@Override
	public void setupProtocol(ServerCommunicator communicator) {
		getProtocol().getProtocolMap().put(IdleServerCommunicationTask.getTaskName(),  new IdleServerCommunicationTask(communicator));
		getProtocol().getProtocolMap().put(ReplyServerCommunicationTask.getTaskName(),  new ReplyServerCommunicationTask(communicator, getDataHandler()));
		getProtocol().getProtocolMap().put(ClosingServerCommunicationTask.getTaskName(),  new ClosingServerCommunicationTask(communicator));
		getProtocol().getProtocolMap().put(RequestServerCommunicationTask.getTaskName(),  new RequestServerCommunicationTask());
	}

}
