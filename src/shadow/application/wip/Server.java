/**
 * 
 */
package shadow.application.wip;

import java.io.IOException;

import shadow.underdevelopment.SFServerConnection;

/**
 * @author Luigi Pasotti
 *
 */
public class Server {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		SFServerConnection connection = new SFServerConnection();
		connection.openConnections(4444);
		connection.closeConnection();
	}

}
