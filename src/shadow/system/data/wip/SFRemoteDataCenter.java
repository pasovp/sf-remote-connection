/**
 * 
 */
package shadow.system.data.wip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import shadow.geometry.geometries.data.SFMeshGeometryData;
import shadow.system.data.SFDataCenterListener;
import shadow.system.data.SFDataset;
import shadow.system.data.SFIDataCenter;
import shadow.system.data.SFObjectsLibrary;
import shadow.system.data.java.SFInputStreamJava;
import shadow.system.data.java.SFOutputStreamJava;

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
	public SFRemoteDataCenter() throws SFDataCenterCreationException {
		library = new SFObjectsLibrary();
		defaultReferencesLibrary = new SFObjectsLibrary();
		requests = new HashMap<String, SFDataset>();
		
		requests.put("DefaultReferences", defaultReferencesLibrary);
		requests.put("DefaultAssetLibrary", library);
		Thread thread = new Thread(new SFRemoteDataCenterRequestTask(requests));
		thread.start();
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ((library == null) || (defaultReferencesLibrary == null)) {
			throw new SFDataCenterCreationException("SFRemoteDataCenter: can't download default assets library");
		}
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
			
			// FIXME Wishing a clone-like generateNewDatasetInstance() or a method for using a simple  
			SFDataset tmp = library.retrieveDataset(((SFDefaultDatasetReference)defaultReferencesLibrary.retrieveDataset(name)).getName().getString());
			dataset = tmp.generateNewDatasetInstance();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			tmp.getSFDataObject().writeOnStream(new SFOutputStreamJava(out , null));
			dataset.getSFDataObject().readFromStream(new SFInputStreamJava(new ByteArrayInputStream(out.toByteArray()), null));
			
			synchronized (requests) {
				requests.put(name, dataset);
			}
			library.put(name, dataset);
			
			if (threadExecutor == null) {
				threadExecutor = Executors.newCachedThreadPool();
				threadExecutor.execute(new SFRemoteDataCenterRequestsCreationTask(requests));
			}
			//threadExecutor.execute(new SFRemoteDataCenterRequestsCreationTask(requests));
		}
		((SFDataCenterListener<SFDataset>)listener).onDatasetAvailable(name, dataset);
	}
}
