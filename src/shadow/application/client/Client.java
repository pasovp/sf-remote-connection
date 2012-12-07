
package shadow.application.client;

import java.io.IOException;
import java.net.UnknownHostException;

import shadow.geometry.geometries.data.SFMeshGeometryData;
import shadow.renderer.contents.tests.common.CommonPipeline;
import shadow.renderer.data.SFObjectModelData;
import shadow.renderer.data.utils.SFViewerDatasetFactory;
import shadow.renderer.viewer.SFViewer;
import shadow.system.data.SFDataCenter;
import shadow.system.data.wip.SFDefaultDatasetReference;
import shadow.system.data.wip.SFRemoteDataCenter;

/**
 * @author Luigi Pasotti
 *
 */
public class Client {
	
	public void configureDataCenter(){
		SFViewerDatasetFactory factory = new SFViewerDatasetFactory();
		factory.addSFDataset(new SFDefaultDatasetReference());
		SFDataCenter.setDatasetFactory(factory);
		SFRemoteDataCenter implementation = new SFRemoteDataCenter();
		SFDataCenter.setDataCenterImplementation(implementation);
	}

	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		//SFViewer.prepare();
		CommonPipeline.prepare();
		Client client = new Client();
		client.configureDataCenter();
		ClientDataCenterListener<SFObjectModelData> listener = new ClientDataCenterListener<SFObjectModelData>();
		
		SFDataCenter.getDataCenter().makeDatasetAvailable("RedMushroom", listener);
	}
}
