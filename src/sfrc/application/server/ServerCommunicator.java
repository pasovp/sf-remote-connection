/**
 * 
 */
package sfrc.application.server;

import java.io.IOException;

import sfrc.base.communication.CommunicatorExceptionListener;
import sfrc.base.communication.GenericCommunicator;
import sfrc.base.communication.sfutil.SFConnection;
import shadow.system.data.SFDataCenter;
import shadow.system.data.SFDataset;

/**
 * @author Luigi Pasotti
 *
 */
public class ServerCommunicator extends GenericCommunicator {
	private SFConnection connection;
	
	public ServerCommunicator(SFConnection connection) {
		super(connection);
		
		this.connection = connection;
	}
	
	public ServerCommunicator(SFConnection connection, CommunicatorExceptionListener listener) {
		super(connection,listener);
		this.connection = connection;
	}
	
	public void sendDataset(SFDataset dataset) {
		// TODO Control correct send of the dataset 
		try {
			SFDataCenter.getDataCenter().writeDataset(connection.getSFOutputStream(), dataset);
		} catch (IOException e) {
			this.handleIOException(e);
		}
	}

}
