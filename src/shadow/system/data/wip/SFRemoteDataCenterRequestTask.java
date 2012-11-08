/**
 * 
 */
package shadow.system.data.wip;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Set;

import shadow.application.client.ClientCommunicator;
import shadow.underdevelopment.SFConnection;

/**
 * @author Luigi Pasotti
 *
 */
public class SFRemoteDataCenterRequestTask implements Runnable {
	private HashMap<String, SFProxyDataset> requests;
	private ClientCommunicator communicator;
	
	public SFRemoteDataCenterRequestTask(HashMap<String, SFProxyDataset> requests) {
		this.requests = requests;
		
		//TODO: add a way to request the connection from an external class
		try {
			this.communicator = new ClientCommunicator(new SFConnection("acquarius", 4444), null);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Set<String> reqKeys;
		synchronized (requests) {
			reqKeys = requests.keySet();
		}
		String request = "request";
		for (String req : reqKeys) {
			request.concat(","+req);
		}
		communicator.sendLine(request);
	}

}
