/**
 * 
 */
package shadow.system.data.wip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;

import shadow.application.client.ClientCommunicator;
import shadow.renderer.data.SFAsset;
import shadow.system.data.SFDataset;
import shadow.system.data.java.SFInputStreamJava;
import shadow.system.data.java.SFOutputStreamJava;
import shadow.underdevelopment.SFConnection;

/**
 * @author Luigi Pasotti
 *
 */
public class SFRemoteDataCenterRequestTask implements Runnable {
	private HashMap<String, SFDataset> requests;
	private ClientCommunicator communicator;
	
	public SFRemoteDataCenterRequestTask(HashMap<String,SFDataset> requests) {
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
			request = request.concat(","+req);
		}
		
		communicator.sendLine(request);
		
		while (true) {
			String input = communicator.readLine();
			
			if (input.compareTo("idle") == 0) {
				communicator.sendLine("close");
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
					communicator.sendLine("ok");
					SFDataset dataset = communicator.readDataset();
					synchronized (requests) {
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						dataset.getSFDataObject().writeOnStream(new SFOutputStreamJava(out , null));
						requests.get(token).getSFDataObject().readFromStream(new SFInputStreamJava(new ByteArrayInputStream(out.toByteArray()), null));
						dataset = requests.get(token);
						requests.remove(token);
					}
					synchronized (dataset) {
						if(dataset instanceof SFAsset) {
							SFAsset asset = (SFAsset)(dataset);
							asset.getResource();
						}
						dataset.notifyAll();
					}
				}
			}
		}
	}
}
