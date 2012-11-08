/**
 * 
 */
package shadow.system.data;

import java.io.IOException;

import shadow.underdevelopment.SFClientConnection;

/**
 * @author Luigi Pasotti
 * This is an implementation of a SFIDataCenter for a client program
 *
 */
public class SFRemoteDataCenter implements SFIDataCenter {
	
	private SFObjectsLibrary library;
	private SFClientConnection connection;

	/**
	 * 
	 */
	public SFRemoteDataCenter(SFClientConnection connection) {
		this.library = new SFObjectsLibrary();
		this.connection = connection;
		//TODO: make sure that the connection is active
	}

	/* (non-Javadoc)
	 * @see shadow.system.data.SFIDataCenter#makeDatasetAvailable(java.lang.String, shadow.system.data.SFDataCenterListener)
	 */
	@Override
	public void makeDatasetAvailable(String name, SFDataCenterListener<?> listener) {
		SFDataset dataset=library.retrieveDataset(name);
		if (dataset == null){
			
		}
		((SFDataCenterListener<SFDataset>)listener).onDatasetAvailable(name, dataset);
	}
	
	private SFDataset requestDataSet(String name) {
		SFDataset dataset = null;
		
		return dataset;
	}

}
