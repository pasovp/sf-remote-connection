/**
 * 
 */
package shadow.system.data.wip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	//private ArrayList<String> requests;
	private SFRemoteRequests requests;
	
	
	/**
	 * 
	 */
	public SFRemoteDataCenter() throws SFDataCenterCreationException {
		library = new SFObjectsLibrary();
		defaultReferencesLibrary = new SFObjectsLibrary();
		//requests = new ArrayList<String>();
		requests = new SFRemoteRequests();
	}
	
	public void loadDefaultData() {
		//requests.add("DefaultReferences");
		//requests.add("DefaultAssetLibrary");
		requests.addRequest("DefaultReferences");
		requests.addRequest("DefaultAssetLibrary");
		
		Thread thread = new Thread(new SFRemoteDataCenterRequestTask());
		thread.start();
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SFDataset assetLib = library.retrieveDataset("DefaultAssetLibrary");
		SFDataset defRef = library.retrieveDataset("DefaultReferences");
		
		if ((assetLib != null) && (defRef!=null)) {
			defaultReferencesLibrary.addLibrary((SFObjectsLibrary)defRef);
			library.addLibrary((SFObjectsLibrary)assetLib);
		} else {
			throw new SFDataCenterCreationException("SFRemoteDataCenter: can't download default assets library");
		}
	}
	
	public synchronized void addDatasetToLibraty(String name, SFDataset dataset) {
		this.library.put(name, dataset);
	}
	
//	public synchronized void removeRequest(String name) {
//		this.requests.remove(name);
//	}
//	
//	public synchronized void addRequest(String name) {
//		this.requests.add(name);
//	}
	
//	public synchronized ArrayList<String> getRequests() {
//		return requests;
//	}
	
	public SFRemoteRequests getRequests() {
		return this.requests;
	}

	/* (non-Javadoc)
	 * @see shadow.system.data.wip.SFIDataCenter#makeDatasetAvailable(java.lang.String, shadow.system.data.wip.SFDataCenterListener)
	 */
	@Override
	public void makeDatasetAvailable(String name, SFDataCenterListener<?> listener) {
		
		SFDataset dataset = library.retrieveDataset(name);
		if (dataset == null){
			SFDataset tmp = library.retrieveDataset(((SFDefaultDatasetReference)defaultReferencesLibrary.retrieveDataset(name)).getName().getString());
			dataset = tmp.generateNewDatasetInstance();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			tmp.getSFDataObject().writeOnStream(new SFOutputStreamJava(out , null));
			dataset.getSFDataObject().readFromStream(new SFInputStreamJava(new ByteArrayInputStream(out.toByteArray()), null));
			
			this.addDatasetToLibraty(name, dataset);
			synchronized (requests) {
				//requests.add(name);
				requests.addUpdateListener(name, listener);
				requests.addRequest(name);
			}
			
			if (threadExecutor == null) {
				threadExecutor = Executors.newCachedThreadPool();
				threadExecutor.execute(new SFRemoteDataCenterRequestsCreationTask());
			}
		}
		((SFDataCenterListener<SFDataset>)listener).onDatasetAvailable(name, dataset);
	}
	
	public <T extends SFDataset> void makeUpdatableDatasetAvailable(String name, SFUpdatableDatasetListener<T> listener){
		
		requests.addUpdateListenerOld(name, listener);
		makeDatasetAvailable(name, listener);
		
	}
}
