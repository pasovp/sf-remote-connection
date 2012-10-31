/**
 * 
 */
package shadow.application.server;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import shadow.underdevelopment.SFConnection;
import shadow.underdevelopment.SFServerConnection;

/**
 * @author Luigi Pasotti
 * A task that listen on a server socket for incoming connection requests and and allocates threads that handle each request 
 */
public class ServerListeningTask implements Runnable {
	private SFServerConnection serverConnection;
	//private ExecutorService threadExecutor;

	/**
	 * @param serverConnection
	 */
	public ServerListeningTask(SFServerConnection serverConnection) {
		super();
		this.serverConnection = serverConnection;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		boolean listening = true;
		ExecutorService threadExecutor = Server.getThreadExecutor();
		while(listening){
			try {
				threadExecutor.execute(new ServerComunicationTask(new SFConnection(serverConnection.acceptConnection())));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
