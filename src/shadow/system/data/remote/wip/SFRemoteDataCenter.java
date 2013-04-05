/**
 * 
 */
package shadow.system.data.remote.wip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

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
	private SFObjectsLibrary defaultReplacementsLibrary;
	
	/**
	 * 
	 */
	public SFRemoteDataCenter() throws SFDataCenterCreationException {
		library = new SFObjectsLibrary();
		defaultReplacementsLibrary = new SFObjectsLibrary();
	}
	
	public SFObjectsLibrary getLibrary() {
		return library;
	}

	public void setLibrary(SFObjectsLibrary library) {
		this.library = library;
	}

	public SFObjectsLibrary getDefaultReplacementsLibrary() {
		return defaultReplacementsLibrary;
	}

	public void setDefaultReplacementsLibrary(	SFObjectsLibrary defaultReplacementsLibrary) {
		this.defaultReplacementsLibrary = defaultReplacementsLibrary;
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
				SFDataset tmp = library.retrieveDataset(((SFDatasetReplacement) defaultReplacementsLibrary.retrieveDataset(name)).getReplacementName());
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

	
}
