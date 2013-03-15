/**
 * 
 */
package sfrc.application.server;

import java.io.IOException;

import sfrc.base.communication.CommunicatorExceptionListener;
import sfrc.base.communication.GenericCommunicator;
import sfrc.base.communication.SFConnection;
import shadow.system.data.SFDataCenter;
import shadow.system.data.SFDataset;

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
	
	public ServerCommunicator(SFConnection connection, CommunicatorExceptionListener listener) {
		super(connection,listener);
		this.datacenter = SFDataCenter.getDataCenter(); 
	}
	
	public void sendDataset(SFDataset dataset) {
		// TODO Control correct send of the dataset 
		try {
			this.datacenter.writeDataset(super.getConnection().getSFOutputStream(), dataset);
		} catch (IOException e) {
			this.handleIOException(e);
		}
	}

}
