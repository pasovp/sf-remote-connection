package sfrc.application.server.test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sfrc.application.server.IServerCommunicationProtocolTask;
import sfrc.application.server.ServerCommunicationTask;
import sfrc.application.server.ServerCommunicator;
import sfrc.base.communication.CommunicationProtocol;
import sfrc.base.communication.ServerConnection;
import shadow.system.data.remote.wip.SFConnection;


public abstract class AbstractTestServer {

	private static ExecutorService threadExecutor = Executors.newCachedThreadPool();
	private ServerDataHandler dataHandler = new ServerDataHandler();
	private CommunicationProtocol<IServerCommunicationProtocolTask> protocol = new CommunicationProtocol<IServerCommunicationProtocolTask>();
	
	
	
	public void execute() {
		/*
		 * TODO: can add a gui or a command line for interaction with the server application
		 * in a way like this:
		 * threadExecutor.execute(gui_runnable);
		 * threadExecutor.execute(cli_runnable);
		 * 
		 */
		
		loadLibraries();
		listenForConnection();
	}
	
	public static void execute(AbstractTestServer test){
		test.execute();
	}
	
	public void loadLibraries() {
		dataHandler.generateTestLibrariesFromXML();
		dataHandler.generateDefaultDataFromXML();
		dataHandler.loadTestLibraries();
		dataHandler.loadDefaultData();
	}
	
	public void listenForConnection() {
		try {
			ServerConnection serverConnection = new ServerConnection(4444);
			while(true){
				SFConnection connection = new SFConnection(serverConnection.acceptConnection());
				ServerCommunicator communicator = new ServerCommunicator(connection);
				setupProtocol(communicator);
				threadExecutor.execute(new ServerCommunicationTask(protocol));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void setupProtocol(ServerCommunicator communicator);
	
	public CommunicationProtocol<IServerCommunicationProtocolTask> getProtocol(){
		return protocol;
	}
	
	public ServerDataHandler getDataHandler(){
		return dataHandler;
	}
	
	public static ExecutorService getThreadExecutor() {
		return threadExecutor;
	}
}