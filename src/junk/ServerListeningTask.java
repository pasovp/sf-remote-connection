/**
 * 
 */
package junk;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import shadow.application.server.IServerCommunicationProtocolTask;
import shadow.application.server.IServerDataLibrary;
import shadow.underdevelopment.CommunicationProtocol;
import shadow.underdevelopment.CommunicatorExceptionListener;
import shadow.underdevelopment.SFServerConnection;

/**
 * @author Luigi Pasotti
 * A task that listen on a server socket for incoming connection requests and and allocates threads that handle each request 
 */
public class ServerListeningTask implements Runnable {
	private SFServerConnection serverConnection;
	private CommunicatorExceptionListener listener;
	private IServerDataLibrary library;
	private CommunicationProtocol<IServerCommunicationProtocolTask> protocol;
	
	private ExecutorService threadExecutor;

	/**
	 * @param serverConnection
	 */
	public ServerListeningTask(SFServerConnection serverConnection, CommunicatorExceptionListener listener, IServerDataLibrary library, CommunicationProtocol<IServerCommunicationProtocolTask> protocol) {
		super();
		this.serverConnection = serverConnection;
		this.listener = listener;
		this.library = library;
		this.protocol = protocol;
		this.threadExecutor = Executors.newCachedThreadPool();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		boolean listening = true;
		while(listening){
//			try {
//				threadExecutor.execute(new ServerCommunicationTask(new SFConnection(serverConnection.acceptConnection()), this.listener, this.library, this.protocol));
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
	}
}
