
package shadow.application.client;

import java.io.IOException;
import java.net.UnknownHostException;

import shadow.renderer.data.SFObjectModelData;
import shadow.renderer.data.utils.SFViewerDatasetFactory;
import shadow.renderer.viewer.SFViewer;
import shadow.system.data.SFDataCenter;
import shadow.system.data.wip.SFRemoteDataCenter;

/**
 * @author Luigi Pasotti
 *
 */
public class Client {
	
	public void configureDataCenter(){
		SFDataCenter.setDatasetFactory(new SFViewerDatasetFactory());
		SFRemoteDataCenter implementation = new SFRemoteDataCenter();
		SFDataCenter.setDataCenterImplementation(implementation);
	}

	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		Client client = new Client();
		client.configureDataCenter();
		ClientDataCenterListener<SFObjectModelData> listener = new ClientDataCenterListener<SFObjectModelData>();
		SFViewer.prepare();
		SFDataCenter.getDataCenter().makeDatasetAvailable("RedMushroom", listener);
		
	}
	
	
	

}
