/**
 * 
 */
package shadow.system.data.wip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import shadow.system.SFUpdater;
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

	private Boolean loadDefaultRefs = false;
	private Boolean loadDefaultAssets = false;
	private Boolean defaultLibrariesError = false;
	
	/**
	 * 
	 */
	public SFRemoteDataCenter() throws SFDataCenterCreationException {
		library = new SFObjectsLibrary();
		defaultReferencesLibrary = new SFObjectsLibrary();
	}
	
	public void loadDefaultData() {
		
		SFRemoteDataCenterRequests.getRequest().addUpdateListener("DefaultReferences", new DataCenterRequest(
			
			new SFDataCenterListener<SFDataset>() {
				@Override
				public void onDatasetAvailable(String name, SFDataset dataset) {
					loadDefaultRefs = true;
					defaultReferencesLibrary.addLibrary((SFObjectsLibrary)dataset);
				}
			},
			new IFailedRequestListener() {
				
				@Override
				public void onFailedRequest() {
					defaultLibrariesError = true;
					
				}
			}
		));
		
		SFRemoteDataCenterRequests.getRequest().addUpdateListener("DefaultAssetLibrary", new DataCenterRequest(
			
			new SFDataCenterListener<SFDataset>() {
				@Override
				public void onDatasetAvailable(String name, SFDataset dataset) {
					loadDefaultAssets = true;
					library.addLibrary((SFObjectsLibrary)dataset);
				}
			},
			new IFailedRequestListener() {
				
				@Override
				public void onFailedRequest() {
					defaultLibrariesError = true;
					
				}
			}
		));
		
		SFRemoteDataCenterRequests.getRequest().addRequest("DefaultReferences");
		SFRemoteDataCenterRequests.getRequest().addRequest("DefaultAssetLibrary");
		System.err.println("Time:" + System.currentTimeMillis() + " default libraries requested");
		
		while(!loadDefaultRefs || !loadDefaultAssets){
			SFUpdater.refresh();
			
			try {
				Thread.sleep(100);
				System.err.println("Time:" + System.currentTimeMillis() + " waiting for default libraries ");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(defaultLibrariesError){
			throw new SFDataCenterCreationException("SFRemoteDataCenter: can't download default assets libraries");
		}
	}
	
	public synchronized void addDatasetToLibraty(String name, SFDataset dataset) {
		this.library.put(name, dataset);
	}

	/* (non-Javadoc)
	 * @see shadow.system.data.wip.SFIDataCenter#makeDatasetAvailable(java.lang.String, shadow.system.data.wip.SFDataCenterListener)
	 */
	@Override
	public void makeDatasetAvailable(String name, SFDataCenterListener<?> listener) {
		SFDataset dataset;
		synchronized (SFRemoteDataCenterRequests.getRequest()) {
			dataset = library.retrieveDataset(name);
			if (dataset == null) {
				SFDataset tmp = library.retrieveDataset(((SFDefaultDatasetReference) defaultReferencesLibrary.retrieveDataset(name)).getName().getString());
				dataset = tmp.generateNewDatasetInstance();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				tmp.getSFDataObject().writeOnStream(new SFOutputStreamJava(out, null));
				dataset.getSFDataObject().readFromStream(new SFInputStreamJava(new ByteArrayInputStream(out.toByteArray()), null));
				
				this.addDatasetToLibraty(name, dataset);
				
				SFRemoteDataCenterRequests.getRequest().addUpdateListener(name, new DataCenterRequest( (SFDataCenterListener<SFDataset>)listener));
				SFRemoteDataCenterRequests.getRequest().addRequest(name);
	
			} else {
				if(SFRemoteDataCenterRequests.getRequest().updatePending(name)) {
					SFRemoteDataCenterRequests.getRequest().addUpdateListener(name, new DataCenterRequest( (SFDataCenterListener<SFDataset>)listener));
				}
			}
		}
		
		((SFDataCenterListener<SFDataset>)listener).onDatasetAvailable(name, dataset);
	}
	
	protected SFDataset getUpdatedDataset(String name) {
		return library.retrieveDataset(name);
	}

}
