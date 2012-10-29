/**
 * 
 */
package shadow.application.wip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import shadow.system.data.SFDataset;
import shadow.system.data.objects.SFDatasetObject;
import shadow.underdevelopment.SFClientConnection;

/**
 * @author Luigi Pasotti
 * Class {@link ClientComunicator} implements the communication logic of the client.
 *
 */
public class ClientComunicator {
	
	private SFClientConnection connection;
	
	/**
	 * @param connection
	 */
	public ClientComunicator(SFClientConnection connection) {
		super();
		this.connection = connection;
	}
	
	public SFDataset requestDataset(String name) throws IOException {
		SFDataset dataset;
		BufferedReader in;
		PrintWriter out;
		
		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		out = new PrintWriter(connection.getOutputStream(), true);
		
		
		
		return dataset;
	}

	
}
