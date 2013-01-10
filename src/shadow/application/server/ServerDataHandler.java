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
import shadow.system.data.java.SFInputStreamJava;
import shadow.system.data.java.SFOutputStreamJava;
import shadow.system.data.wip.SFDefaultDatasetReference;

/**
 * @author Luigi Pasotti
 *
 */
public class ServerDataHandler implements IServerDataLibrary {

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
	
	public void generateLibraryFromXML() {
		SFObjectsLibrary lib;
		lib = new SFObjectsLibrary();
		SFObjectsLibraryDecoder decoder = new SFObjectsLibraryDecoder(lib);
		SFXMLDataInterpreter interpreter = new SFXMLDataInterpreter(decoder);
		interpreter.generateInterpretation(ROOT + "/" + FILENAME + ".xml" );
		SFDataUtility.saveDataset(ROOT, FILENAME + ".sf", lib);
		
		lib = new SFObjectsLibrary();
		decoder = new SFObjectsLibraryDecoder(lib);
		interpreter = new SFXMLDataInterpreter(decoder);
		interpreter.generateInterpretation(ROOT + "/default.xml" );
		SFDataUtility.saveDataset(ROOT, "default.sf", lib);
		
	}
	
	public void loadLibrary() {
		SFObjectsLibrary lib;
		lib = (SFObjectsLibrary) SFDataUtility.loadDataset(ROOT, FILENAME + ".sf");
		this.library = lib;
		lib = (SFObjectsLibrary) SFDataUtility.loadDataset(ROOT, "default.sf");
		this.library.put("DefaultAssetLibrary",lib);
	}
	
	public void generateDefaultReferences() {
		this.defaultReferencesLibrary.put("BasicMatColours", new SFDefaultDatasetReference("MatColours", System.currentTimeMillis()));
		this.defaultReferencesLibrary.put("Mushroom", new SFDefaultDatasetReference("Cube", System.currentTimeMillis()));
		this.defaultReferencesLibrary.put("RedMushroom", new SFDefaultDatasetReference("GreenCube", System.currentTimeMillis()));
		
		SFDataUtility.saveDataset(ROOT, "defaultReferences.sf", this.defaultReferencesLibrary);
	}
	
	public void loadDefaultReferences() {
		SFObjectsLibrary lib;
		lib = (SFObjectsLibrary) SFDataUtility.loadDataset(ROOT, "defaultReferences.sf");
		this.defaultReferencesLibrary.addLibrary(lib);
		this.library.put("DefaultReferences", this.defaultReferencesLibrary);
	}

	/* (non-Javadoc)
	 * @see shadow.application.server.IServerDataLibrary#getDataset(java.lang.String)
	 */
	@Override
	public SFDataset getDataset(String datasetName) {
		SFDataset dataset = library.retrieveDataset(datasetName);
		return dataset;
	}

	//DEBUG METHOD
	public void testCopy() {
		SFDataset dataset1 = library.retrieveDataset("Cube");
		SFDataset dataset2 = dataset1.generateNewDatasetInstance();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		dataset1.getSFDataObject().writeOnStream(new SFOutputStreamJava(out , null));
		dataset2.getSFDataObject().readFromStream(new SFInputStreamJava(new ByteArrayInputStream(out.toByteArray()), null));
		
		//System.out.println();
		
	}

}