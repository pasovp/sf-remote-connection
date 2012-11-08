/**
 * 
 */
package shadow.system.data.wip;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import shadow.application.client.ClientCommunicatorOld;
import shadow.system.data.SFDataCenterListener;
import shadow.system.data.SFDataset;
import shadow.system.data.SFIDataCenter;
import shadow.system.data.SFObjectsLibrary;

/**
 * @author Luigi Pasotti
 * This is an implementation of a SFIDataCenter for a client program
 *
 */
public class SFRemoteDataCenter implements SFIDataCenter {
	
	private SFObjectsLibrary library;
	private ExecutorService threadExecutor;
	private HashMap<String, SFProxyDataset> requests;

	/**
	 * 
	 */
	public SFRemoteDataCenter(ClientCommunicatorOld comunicator) {
		this.library = new SFObjectsLibrary();
		this.requests = new HashMap<String, SFProxyDataset>();
	}

	/* (non-Javadoc)
	 * @see shadow.system.data.wip.SFIDataCenter#makeDatasetAvailable(java.lang.String, shadow.system.data.wip.SFDataCenterListener)
	 */
	@Override
	public void makeDatasetAvailable(String name, SFDataCenterListener<?> listener) {
		SFDataset dataset = library.retrieveDataset(name);
		if (dataset == null){
			SFProxyDataset proxy = new SFProxyDataset();
			dataset = proxy;
			synchronized (requests) {
				requests.put(name, proxy);
			}
			if (threadExecutor == null) {
				threadExecutor = Executors.newCachedThreadPool();
				threadExecutor.execute(new SFRemoteDataCenterRequestsCreationTask(requests));
			}
		}
		((SFDataCenterListener<SFDataset>)listener).onDatasetAvailable(name, dataset);
	}
	
	// TODO Delete this code?
//	private SFDataset requestDataSet(String name) {
//		SFDataset dataset = null;
//		
//		return dataset;
//	}

}
