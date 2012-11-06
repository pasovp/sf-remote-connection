/**
 * 
 */
package shadow.application.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import shadow.system.data.SFDataCenter;
import shadow.system.data.SFDataset;
import shadow.underdevelopment.SFConnection;

/**
 * @author Luigi Pasotti
 *
 */
public class ServerCommunicator {
	private SFConnection connection;
	private SFDataCenter datacenter;
	
	public ServerCommunicator(SFConnection connection) {
		this.connection = connection;
		this.datacenter = SFDataCenter.getDataCenter(); 
	}
	
	/**
	 * Reads a line of text. A line is considered to be terminated by any one of a line feed ('\n'), a carriage return ('\r'), or a carriage return followed immediately by a linefeed.
	 * @return A String containing the contents of the line, not including any line-termination characters, or null if the end of the stream has been reached 
	 */
	public String readLine() {
		BufferedReader in;
		String line = null;
		try {
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			line = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}
	
	public void sendDataset(SFDataset dataset) {
		// TODO Control correct send of the dataset 
		try {
			this.datacenter.writeDataset(connection.getSFOutputStream(), dataset);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendLine(String line) {
		PrintWriter out;
		try {
			out = new PrintWriter(connection.getOutputStream(), true);
			out.println(line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeComunication() {
		connection.closeConnection();
	}

}
