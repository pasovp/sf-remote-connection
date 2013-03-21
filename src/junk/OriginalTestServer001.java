package junk;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sfrc.application.server.IServerCommunicationProtocolTask;
import sfrc.application.server.ServerCommunicationTask;
import sfrc.application.server.ServerCommunicator;
import sfrc.application.server.tasks.ClosingServerCommunicationTask;
import sfrc.application.server.tasks.IdleServerCommunicationTask;
import sfrc.application.server.tasks.ReplyServerCommunicationTask;
import sfrc.application.server.test.ServerDataHandler;
import sfrc.base.communication.CommunicationProtocol;
import sfrc.base.communication.ServerConnection;
import sfrc.base.communication.sfutil.SFConnection;

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
		
		ServerConnection serverConnection = new ServerConnection(4444);
		
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
				protocol.getProtocolMap().put("closing",  new ClosingServerCommunicationTask(communicator));
				
				threadExecutor.execute(new ServerCommunicationTask(protocol));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
