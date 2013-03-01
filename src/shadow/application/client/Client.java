
package shadow.application.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import shadow.renderer.contents.tests.common.CommonPipeline;
import shadow.renderer.data.SFObjectModelData;
import shadow.renderer.data.utils.SFViewerDatasetFactory;
import shadow.system.data.SFDataCenter;
import shadow.system.data.wip.SFDefaultDatasetReference;
import shadow.system.data.wip.SFRemoteDataCenter;
import shadow.system.data.wip.SFRemoteDataCenterRequestsCreationTask;

/**
 * @author Luigi Pasotti
 *
 */
public class Client {
	
	private ExecutorService threadExecutor;
	
	public void configureDataCenter(){
		SFViewerDatasetFactory factory = new SFViewerDatasetFactory();
		factory.addSFDataset(new SFDefaultDatasetReference());
		SFDataCenter.setDatasetFactory(factory);
		SFRemoteDataCenter implementation = new SFRemoteDataCenter();
		SFDataCenter.setDataCenterImplementation(implementation);
		
		threadExecutor = Executors.newCachedThreadPool();
		threadExecutor.execute(new SFRemoteDataCenterRequestsCreationTask());
		implementation.loadDefaultData();
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
