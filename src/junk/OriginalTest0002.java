
package junk;

import java.io.IOException;
import java.net.UnknownHostException;


import shadow.renderer.contents.tests.common.CommonPipeline;
import shadow.renderer.data.SFObjectModelData;
import shadow.renderer.data.utils.SFViewerDatasetFactory;
import shadow.system.data.SFDataCenter;
import shadow.system.data.remote.wip.SFRemoteDataCenter;

/**
 * @author Luigi Pasotti
 *
 */
public class OriginalTest0002 {
	
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
		OriginalTest0002 client = new OriginalTest0002();
		client.configureDataCenter();
		ClientDataCenterListener<SFObjectModelData> listener = new ClientDataCenterListener<SFObjectModelData>();
		
		SFDataCenter.getDataCenter().makeDatasetAvailable("RedMushroom", listener);
	}
}
