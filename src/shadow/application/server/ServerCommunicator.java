/**
 * 
 */
package shadow.application.server;

import java.io.IOException;

import shadow.system.data.SFDataCenter;
import shadow.system.data.SFDataset;
import shadow.underdevelopment.GenericCommunicator;
import shadow.underdevelopment.SFConnection;

/**
 * @author Luigi Pasotti
 *
 */
public class ServerCommunicator extends GenericCommunicator {
	private SFDataCenter datacenter;
	
	public ServerCommunicator(SFConnection connection) {
		super(connection);
		this.datacenter = SFDataCenter.getDataCenter(); 
	}
	
	public void sendDataset(SFDataset dataset) {
		// TODO Control correct send of the dataset 
		try {
			this.datacenter.writeDataset(super.getConnection().getSFOutputStream(), dataset);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
