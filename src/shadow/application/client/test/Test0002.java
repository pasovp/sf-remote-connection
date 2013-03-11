
package shadow.application.client.test;

import java.io.IOException;
import java.net.UnknownHostException;

import shadow.renderer.viewer.SFViewer;

/**
 * @author Luigi Pasotti
 *
 */
public class Test0002 extends AbstractClient{
	
	

	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		execute(new Test0002());
		
		
		
	}

	@Override
	public void viewTestData() {
		
	//	SFDataCenter.getDataCenter().makeDatasetAvailable("RedMushroom", listener);
		
		viewNode("RedMushroom",true,SFViewer.getLightStepController());
	}
}
