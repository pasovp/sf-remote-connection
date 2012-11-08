/**
 * 
 */
package shadow.system.data.wip;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;

import shadow.application.client.ClientCommunicator;
import shadow.system.data.SFDataset;
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
		
		while (true) {
			String input = communicator.readLine();
			
			if (input.compareTo("idle") == 0) {
				communicator.closeComunication();
				break;
			} else {
				StringTokenizer tokenizer = new StringTokenizer(input, ",");
				String token = tokenizer.nextToken();
				if (token.compareTo("fail") == 0) {
					token = tokenizer.nextToken();
					synchronized (requests) {
						requests.remove(token);
						// TODO add a notification of the failed request
					} 
				} else {
					SFDataset dataset = communicator.readDataset();
					synchronized (requests) {
						requests.get(token).setSFDataset(dataset);
						requests.remove(token);
					}
				}
			}
		}
	}
}
