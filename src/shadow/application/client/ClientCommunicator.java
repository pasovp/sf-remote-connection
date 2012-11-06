/**
 * 
 */
package shadow.application.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import shadow.system.data.SFDataCenter;
import shadow.system.data.SFDataset;
import shadow.underdevelopment.SFConnection;

/**
 * @author Luigi Pasotti
 * Class {@link ClientComunicator} implements the communication logic of the client.
 *
 */
public class ClientComunicator {
	
	private SFConnection connection;
	private ClientComunicatorExceptionListener listener;
	
	/**
	 * @param connection
	 */
	public ClientComunicator(SFConnection connection, ClientComunicatorExceptionListener listener ) {
		super();
		this.connection = connection;
		this.listener = listener;
	}
	
	public SFDataset requestDataset(String name) {
		SFDataset dataset = null;
		BufferedReader in;
		PrintWriter out;
		
		try {
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			out = new PrintWriter(connection.getOutputStream(), true);
			
			out.println("request");
			if (in.readLine()=="listen") {
				out.println(name);
				dataset = SFDataCenter.getDataCenter().readDataset(connection.getSFInputStream());
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
			listener.exceptionCatched(e);
		}
		
		
		
		
		return dataset;
	}

	
}
