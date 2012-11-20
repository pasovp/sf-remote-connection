/**
 * 
 */
package shadow.system.data.wip;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	private SFObjectsLibrary defaultReferencesLibrary;
	private ExecutorService threadExecutor;
	private HashMap<String,SFDataset> requests;

	/**
	 * 
	 */
	public SFRemoteDataCenter() {
		this.library = new SFObjectsLibrary();
		this.requests = new HashMap<String, SFDataset>();
	}

	/* (non-Javadoc)
	 * @see shadow.system.data.wip.SFIDataCenter#makeDatasetAvailable(java.lang.String, shadow.system.data.wip.SFDataCenterListener)
	 */
	@Override
	public void makeDatasetAvailable(String name, SFDataCenterListener<?> listener) {
//		Type[] t = listener.getClass().getGenericInterfaces();
//		ParameterizedType pt = (ParameterizedType) t[0];
//		Type[] ptt = pt.getActualTypeArguments();
		
		SFDataset dataset = library.retrieveDataset(name);
		if (dataset == null){
			//dataset = SFDataCenter.getDataCenter().createDataset(((Class) ptt[0]).getSimpleName());
			dataset = library.retrieveDataset(((SFDefaultDatasetReference)defaultReferencesLibrary.retrieveDataset(name)).getName().getString()).generateNewDatasetInstance();
			synchronized (requests) {
				requests.put(name, dataset);
			}
			library.put(name, dataset);
			if (threadExecutor == null) {
				threadExecutor = Executors.newCachedThreadPool();
				threadExecutor.execute(new SFRemoteDataCenterRequestsCreationTask(requests));
			}
		}
		synchronized (dataset) {
			try {
				dataset.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		((SFDataCenterListener<SFDataset>)listener).onDatasetAvailable(name, dataset);
	}
}
