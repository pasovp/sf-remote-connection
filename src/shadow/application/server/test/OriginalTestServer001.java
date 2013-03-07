package shadow.application.server.test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import shadow.application.server.IServerCommunicationProtocolTask;
import shadow.application.server.ServerCommunicationTask;
import shadow.application.server.ServerCommunicator;
import shadow.application.server.tasks.CloseServerCommunicationTask;
import shadow.application.server.tasks.IdleServerCommunicationTask;
import shadow.application.server.tasks.ReplyServerCommunicationTask;
import shadow.underdevelopment.CommunicationProtocol;
import shadow.underdevelopment.SFConnection;
import shadow.underdevelopment.SFServerConnection;

public class OriginalTestServer001 {

	private static ExecutorService threadExecutor = Executors.newCachedThreadPool();
	
	public static ExecutorService getThreadExecutor() {
		return threadExecutor;
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ServerDataHandler dataHandler = new ServerDataHandler();
		
		dataHandler.generateTestLibrariesFromXML();
		dataHandler.generateDefaultDataFromXML();
		
		dataHandler.loadTestLibraries();
		dataHandler.loadDefaultData();
		
		SFServerConnection serverConnection = new SFServerConnection(4444);
		
		/*
		 * TODO: can add a gui or a command line for interaction with the server application
		 * in a way like this:
		 * threadExecutor.execute(gui_runnable);
		 * threadExecutor.execute(cli_runnable);
		 * 
		 */
		
		
		boolean listening = true;
		while(listening){
			try {
				SFConnection connection = new SFConnection(serverConnection.acceptConnection());
				
				ServerCommunicator communicator = new ServerCommunicator(connection);
				CommunicationProtocol<IServerCommunicationProtocolTask> protocol = new CommunicationProtocol<IServerCommunicationProtocolTask>();
				protocol.getProtocolMap().put("idle",  new IdleServerCommunicationTask(communicator));
				protocol.getProtocolMap().put("reply",  new ReplyServerCommunicationTask(communicator, dataHandler));
				protocol.getProtocolMap().put("closing",  new CloseServerCommunicationTask(communicator));
				
				threadExecutor.execute(new ServerCommunicationTask(protocol));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
