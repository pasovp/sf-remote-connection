package sfrc.application.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sfrc.base.communication.CommunicationProtocol;
import sfrc.base.communication.sfutil.SFConnection;
import shadow.system.data.remote.wip.SFRemoteDataCenterRequests;

/**
 * @author Luigi Pasotti
 *
 */
public class RemoteDataCenterRequestsCreationTask implements Runnable {
	private ExecutorService threadExecutor = Executors.newCachedThreadPool();
	private CommunicationProtocol<IClientCommunicationProtocolTask> protocol;
	int port = -1;
	InetAddress address;

	public RemoteDataCenterRequestsCreationTask(CommunicationProtocol<IClientCommunicationProtocolTask> protocol, String host, int port) {
		super();
		this.protocol = protocol;
		try {
			this.address = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.port = port;
	}
	
	public RemoteDataCenterRequestsCreationTask(CommunicationProtocol<IClientCommunicationProtocolTask> protocol, InetAddress address, int port) {
		super();
		this.protocol = protocol;
		this.address = address;
		this.port = port;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
			if (SFRemoteDataCenterRequests.getRequest().hasNewRequests()) {
				threadExecutor.execute(new RemoteDataCenterRequestTask(this.protocol, new ClientCommunicator(new SFConnection(address, port), null)));
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
