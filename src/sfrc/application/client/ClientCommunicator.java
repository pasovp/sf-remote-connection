/**
 * 
 */
package sfrc.application.client;

import java.io.IOException;

import sfrc.base.communication.CommunicatorExceptionListener;
import sfrc.base.communication.GenericCommunicator;
import shadow.system.data.SFDataCenter;
import shadow.system.data.SFDataset;
import shadow.system.data.remote.wip.SFConnection;

/**
 * @author Luigi Pasotti
 * Class ClientCommunicator implements the communication logic of the client.
 */
public class ClientCommunicator extends GenericCommunicator {
	
	private SFConnection connection;
	
	/**
	 * @param connection
	 * @param listener the listener of the exceptions of the communicator, or null when there isn't an external management for exceptions.
	 */
	public ClientCommunicator(SFConnection connection, CommunicatorExceptionListener listener ) {
		super(connection, listener);
		this.connection = connection; 
		try {
			this.connection.openConnection();
		} catch (IOException e) {
			this.handleIOException(e);
		}
	}
	
	public SFDataset readDataset() {
		SFDataset dataset = null;
		try {
			dataset = SFDataCenter.getDataCenter().readDataset(this.connection.getSFInputStream());
		} catch (IOException e) {
			this.handleIOException(e);
		}
		return dataset;
	}
}
