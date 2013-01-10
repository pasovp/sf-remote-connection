/**
 * 
 */
package shadow.application.server;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import shadow.underdevelopment.SFServerConnection;

/**
 * @author Luigi Pasotti
 *
 */
public class Server {

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
		dataHandler.generateLibraryFromXML();
		dataHandler.loadLibrary();
		dataHandler.generateDefaultReferences();
		dataHandler.loadDefaultReferences();
		//dataHandler.testCopy();
		SFServerConnection serverConnection = new SFServerConnection(4444);
		ServerListeningTask listeningTask = new ServerListeningTask(serverConnection, dataHandler);
		threadExecutor.execute(listeningTask);
		//TODO: can add a gui or a command line for interaction with the server application

	}

}
