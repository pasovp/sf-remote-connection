/**
 * 
 */
package shadow.application.client;

import java.io.IOException;
import java.net.UnknownHostException;

import shadow.renderer.viewer.SFViewer;
import shadow.underdevelopment.SFConnection;

/**
 * @author Luigi Pasotti
 *
 */
public class ClientViewer {

	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		SFViewer.prepare();
		SFViewer viewer = new SFViewer();
		SFConnection connection = new SFConnection("acquarius", 4444);
		connection.openConnection();
		

	}

}
