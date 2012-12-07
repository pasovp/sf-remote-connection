/**
 * 
 */
package shadow.application.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import shadow.renderer.data.java.SFObjectsLibraryDecoder;
import shadow.renderer.data.java.SFXMLDataInterpreter;
import shadow.renderer.data.utils.SFDataUtility;
import shadow.renderer.data.utils.SFViewerDatasetFactory;
import shadow.system.data.SFDataCenter;
import shadow.system.data.SFDataset;
import shadow.system.data.SFObjectsLibrary;
import shadow.system.data.SFObjectsLibrary.RecordData;
import shadow.system.data.java.SFInputStreamJava;
import shadow.system.data.java.SFOutputStreamJava;
import shadow.system.data.wip.SFDefaultDatasetReference;

/**
 * @author Luigi Pasotti
 *
 */
public class ServerDataHandler implements InterfServerDataLibrary {

	private SFObjectsLibrary library;
	private SFObjectsLibrary defaultReferencesLibrary;
	private String ROOT = "appdata";
	private String FILENAME = "test0002";
	
	/**
	 * 
	 */
	public ServerDataHandler() {
		super();
		this.library = new SFObjectsLibrary();
		this.defaultReferencesLibrary = new SFObjectsLibrary();
		
		SFViewerDatasetFactory factory = new SFViewerDatasetFactory();
		factory.addSFDataset(new SFDefaultDatasetReference());
		SFDataCenter.setDatasetFactory(factory);
	}
	
	public void loadLibrary() {
		SFObjectsLibrary lib;
		lib = (SFObjectsLibrary) SFDataUtility.loadDataset(ROOT, FILENAME + ".sf");
		this.library = lib;
		
		// FIXME default assets builded at runtime
		//lib = (SFObjectsLibrary) SFDataUtility.loadDataset(ROOT, "default.sf");
		lib=null;
		if(lib == null){
			lib = new SFObjectsLibrary();
			SFObjectsLibraryDecoder decoder = new SFObjectsLibraryDecoder(lib);
			SFXMLDataInterpreter interpreter = new SFXMLDataInterpreter(decoder);
			interpreter.generateInterpretation(ROOT + "/default.xml" );
			SFDataUtility.saveDataset(ROOT, "default.sf", lib);
		} 
		
		//this.library.addLibrary(lib);
		this.library.put("DefaultAssetLibrary",lib);
	}
	
	public void generateDefaultReferences() {
		this.defaultReferencesLibrary.put("BasicMatColours", new SFDefaultDatasetReference("MatColours", System.currentTimeMillis()));
		this.defaultReferencesLibrary.put("Mushroom", new SFDefaultDatasetReference("Cube", System.currentTimeMillis()));
		this.defaultReferencesLibrary.put("RedMushroom", new SFDefaultDatasetReference("RedCube", System.currentTimeMillis()));
		
		SFDataUtility.saveDataset(ROOT, "defaultReferences.sf", this.defaultReferencesLibrary);
	}
	
	// FIXME default references builded at runtime
	@SuppressWarnings("unused")
	public void loadDefaultReferences() {
		SFObjectsLibrary lib;
		//lib = (SFObjectsLibrary) SFDataUtility.loadDataset(ROOT, "defaultReferences.sf");
		lib = null;
		if(lib == null) {
			generateDefaultReferences();
		} else {
			this.defaultReferencesLibrary.addLibrary(lib);
		}
		this.library.put("DefaultReferences", this.defaultReferencesLibrary);
	}

	/* (non-Javadoc)
	 * @see shadow.application.server.InterfServerDataLibrary#getDataset(java.lang.String)
	 */
	@Override
	public SFDataset getDataset(String datasetName) {
		SFDataset dataset = library.retrieveDataset(datasetName);
		return dataset;
	}

	public void testCopy() {
		SFDataset dataset1 = library.retrieveDataset("Cube");
		SFDataset dataset2 = dataset1.generateNewDatasetInstance();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		dataset1.getSFDataObject().writeOnStream(new SFOutputStreamJava(out , null));
		dataset2.getSFDataObject().readFromStream(new SFInputStreamJava(new ByteArrayInputStream(out.toByteArray()), null));
		
		System.out.println();
		
	}

}