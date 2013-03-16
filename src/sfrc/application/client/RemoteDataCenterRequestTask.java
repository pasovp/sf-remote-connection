/**
 * 
 */
package sfrc.application.client;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import sfrc.base.communication.sfutil.SFConnection;
import shadow.system.data.SFDataset;
import shadow.system.data.remote.wip.SFRemoteDataCenterRequests;

/**
 * @author Luigi Pasotti
 *
 */
public class RemoteDataCenterRequestTask implements Runnable {

	private ArrayList<String> requestList;
	private ClientCommunicator communicator;
	
	public RemoteDataCenterRequestTask() {
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
		requestList = SFRemoteDataCenterRequests.getRequest().removeRequests();
		
		for (String req : requestList) {
			requestString = requestString.concat(","+req);
		}
		
		communicator.sendLine(requestString);
		
		while (true) {
			String input = communicator.readLine();
			
			if (input.compareTo("idle") == 0) {
				communicator.sendLine("close");
				communicator.closeCommunication();
				break;
			} else {
				StringTokenizer tokenizer = new StringTokenizer(input, ",");
				String token = tokenizer.nextToken();
				if (token.compareTo("fail") == 0) {
					token = tokenizer.nextToken();
					SFRemoteDataCenterRequests.getRequest().onRequestFail(token);
					
				} else {
					communicator.sendLine("ok");
					SFDataset dataset = communicator.readDataset();
					
//					synchronized (dataset) {
//						if(dataset instanceof SFAsset) {
//							SFAsset asset = (SFAsset)(dataset);
//							//FIXME Non sono sicuro che sia più necessario usare l'UpdateMode
//							//SFDataAsset.setUpdateMode(true);
//							asset.getResource();
//							//SFDataAsset.setUpdateMode(false);
//						}
//					}
//					((SFRemoteDataCenter)SFDataCenter.getDataCenter().getDataCenterImplementation()).addDatasetToLibraty(token, dataset);
					SFRemoteDataCenterRequests.getRequest().onRequestUpdate(token,dataset);
				}
					
			}
		}
	}
}
