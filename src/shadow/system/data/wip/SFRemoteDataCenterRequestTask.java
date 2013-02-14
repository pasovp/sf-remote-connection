/**
 * 
 */
package shadow.system.data.wip;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import shadow.application.client.ClientCommunicator;
import shadow.renderer.data.SFAsset;
import shadow.renderer.data.SFDataAsset;
import shadow.system.data.SFDataCenter;
import shadow.system.data.SFDataset;
import shadow.underdevelopment.SFConnection;

/**
 * @author Luigi Pasotti
 *
 */
public class SFRemoteDataCenterRequestTask implements Runnable {
	//private ArrayList<String> requests;
	SFRemoteRequests requests;
	private ArrayList<String> requestList;
	private ClientCommunicator communicator;
	
	public SFRemoteDataCenterRequestTask() {
		this.requests = ((SFRemoteDataCenter)SFDataCenter.getDataCenter().getDataCenterImplementation()).getRequests();
		//this.buffer = new ArrayList<String>();
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

		String requestString = "request";
		requestList = requests.removeRequests();
		
//		synchronized (requests) {
//			for (String req : requests) {
//				request = request.concat(","+req);
//				buffer.add(req);
//			}
//			requests.clear();
//		}
		for (String req : requestList) {
			requestString = requestString.concat(","+req);
		}
		
		communicator.sendLine(requestString);
		
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
					// TODO add a notification of the failed request
 
				} else {
					communicator.sendLine("ok");
					SFDataset dataset = communicator.readDataset();
					
					synchronized (dataset) {
						if(dataset instanceof SFAsset) {
							SFAsset asset = (SFAsset)(dataset);
							//FIXME Non sono sicuro che sia più necessario usare l'UpdateMode
							//SFDataAsset.setUpdateMode(true);
							asset.getResource();
							//SFDataAsset.setUpdateMode(false);
						}
					}
					((SFRemoteDataCenter)SFDataCenter.getDataCenter().getDataCenterImplementation()).addDatasetToLibraty(token, dataset);
					
					requests.onRequestUpdate(token);
				}
					
			}
		}
	}
}
