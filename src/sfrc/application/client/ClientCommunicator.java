/**
 * 
 */
package sfrc.application.client;

import java.io.IOException;

import sfrc.base.communication.CommunicatorExceptionListener;
import sfrc.base.communication.GenericCommunicator;
import sfrc.base.communication.SFConnection;
import shadow.system.data.SFDataCenter;
import shadow.system.data.SFDataset;

/**
 * @author Luigi Pasotti
 * Class ClientCommunicator implements the communication logic of the client.
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
		try {
			super.getConnection().openConnection();
		} catch (IOException e) {
			this.handleIOException(e);
		}
	}
	
	public SFDataset readDataset() {
		SFDataset dataset = null;
		try {
			dataset = this.datacenter.readDataset(super.getConnection().getSFInputStream());
		} catch (IOException e) {
			this.handleIOException(e);
		}
		return dataset;
	}
}
