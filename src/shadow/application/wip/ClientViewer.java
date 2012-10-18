/**
 * 
 */
package shadow.application.wip;

import java.io.IOException;
import java.net.UnknownHostException;

import shadow.renderer.viewer.SFViewer;
import shadow.underdevelopment.SFClientConnection;

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
		SFClientConnection connection = new SFClientConnection("acquarius", 4444);
		connection.openConnection();
		

	}

}
