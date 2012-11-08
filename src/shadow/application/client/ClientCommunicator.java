/**
 * 
 */
package shadow.application.client;

import java.io.IOException;

import shadow.system.data.SFDataCenter;
import shadow.system.data.SFDataset;
import shadow.underdevelopment.CommunicatorExceptionListener;
import shadow.underdevelopment.GenericCommunicator;
import shadow.underdevelopment.SFConnection;

/**
 * @author Luigi Pasotti
 * Class {@link ClientCommunicator} implements the communication logic of the client.
 */
public class ClientCommunicator extends GenericCommunicator {
	
	private SFDataCenter datacenter;
	

	/**
	 * @param connection
	 * @param listener the listener of the exceptions of the communicator, or null when there isn't an external management for exceptions.
	 */
	public ClientCommunicator(SFConnection connection, CommunicatorExceptionListener listener ) {
		super(connection, listener);
		this.datacenter = SFDataCenter.getDataCenter(); 
	}

	
	public SFDataset readDataset() {
		SFDataset dataset = null;
		try {
			dataset = this.datacenter.readDataset(super.getConnection().getSFInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataset;
	}
}
