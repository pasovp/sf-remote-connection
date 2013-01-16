
package shadow.application.client.test;

import java.io.IOException;
import java.net.UnknownHostException;

import shadow.application.client.ClientDataCenterListener;
import shadow.renderer.contents.tests.common.CommonPipeline;
import shadow.renderer.data.SFObjectModelData;
import shadow.renderer.data.utils.SFViewerDatasetFactory;
import shadow.system.data.SFDataCenter;
import shadow.system.data.wip.SFDefaultDatasetReference;
import shadow.system.data.wip.SFRemoteDataCenter;

/**
 * @author Luigi Pasotti
 *
 */
public class Test0005 {
	
	public void configureDataCenter(){
		SFViewerDatasetFactory factory = new SFViewerDatasetFactory();
		factory.addSFDataset(new SFDefaultDatasetReference());
		SFDataCenter.setDatasetFactory(factory);
		SFRemoteDataCenter implementation = new SFRemoteDataCenter();
		SFDataCenter.setDataCenterImplementation(implementation);
		implementation.loadDefaultData();
	}

	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		//SFViewer.prepare();
		CommonPipeline.prepare();
		Test0005 client = new Test0005();
		client.configureDataCenter();
		ClientDataCenterListener<SFObjectModelData> listener = new ClientDataCenterListener<SFObjectModelData>();
		
		SFDataCenter.getDataCenter().makeDatasetAvailable("ReddishGrayAndBrightMushroom", listener);
	}
}
