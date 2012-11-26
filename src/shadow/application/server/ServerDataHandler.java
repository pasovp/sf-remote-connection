/**
 * 
 */
package shadow.application.server;

import shadow.renderer.data.java.SFObjectsLibraryDecoder;
import shadow.renderer.data.java.SFXMLDataInterpreter;
import shadow.renderer.data.utils.SFDataUtility;
import shadow.system.data.SFDataset;
import shadow.system.data.SFObjectsLibrary;
import shadow.system.data.SFObjectsLibrary.RecordData;

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
		SFObjectsLibrary lib;
		lib = (SFObjectsLibrary) SFDataUtility.loadDataset(ROOT, FILENAME + ".sf");
		this.library = lib;
		
		lib = (SFObjectsLibrary) SFDataUtility.loadDataset(ROOT, "default.sf");
		if(lib == null){
			lib = new SFObjectsLibrary();
			SFObjectsLibraryDecoder decoder = new SFObjectsLibraryDecoder(lib);
			SFXMLDataInterpreter interpreter = new SFXMLDataInterpreter(decoder);
			interpreter.generateInterpretation(ROOT + "/default.xml" );
			SFDataUtility.saveDataset(ROOT, "default.sf", lib);
		} 
		for (RecordData recordData : lib) {
			this.library.put(recordData);
		}
		
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
