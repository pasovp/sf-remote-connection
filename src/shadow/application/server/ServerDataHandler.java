/**
 * 
 */
package shadow.application.server;

import shadow.renderer.data.utils.SFDataUtility;
import shadow.system.data.SFDataset;
import shadow.system.data.SFObjectsLibrary;

/**
 * @author Luigi Pasotti
 *
 */
public class ServerDataHandler implements InterfServerDataLibrary {

	private SFObjectsLibrary library;
	private String ROOT = "appdata";
	private String FILENAME = "test0002";
	
	/**
	 * 
	 */
	public ServerDataHandler() {
		super();
		this.library = new SFObjectsLibrary();
	}
	
	public void loadLibrary() {		
		SFDataset dataset = SFDataUtility.loadDataset(ROOT, FILENAME + ".sf");
		this.library = (SFObjectsLibrary)(dataset);
	}


	/* (non-Javadoc)
	 * @see shadow.application.server.InterfServerDataLibrary#getDataset(java.lang.String)
	 */
	@Override
	public SFDataset getDataset(String datasetName) {
		SFDataset dataset = library.retrieveDataset(datasetName);
		return dataset;
	}

}
