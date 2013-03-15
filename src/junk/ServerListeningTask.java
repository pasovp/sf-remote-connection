/**
 * 
 */
package junk;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sfrc.application.server.IServerCommunicationProtocolTask;
import sfrc.application.server.IServerDataLibrary;
import sfrc.base.communication.CommunicationProtocol;
import sfrc.base.communication.CommunicatorExceptionListener;
import sfrc.base.communication.ServerConnection;

/**
 * @author Luigi Pasotti
 * A task that listen on a server socket for incoming connection requests and and allocates threads that handle each request 
 */
public class ServerListeningTask implements Runnable {
	private ServerConnection serverConnection;
	private CommunicatorExceptionListener listener;
	private IServerDataLibrary library;
	private CommunicationProtocol<IServerCommunicationProtocolTask> protocol;
	
	private ExecutorService threadExecutor;

	/**
	 * @param serverConnection
	 */
	public ServerListeningTask(ServerConnection serverConnection, CommunicatorExceptionListener listener, IServerDataLibrary library, CommunicationProtocol<IServerCommunicationProtocolTask> protocol) {
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
