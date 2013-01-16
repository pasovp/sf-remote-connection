/**
 * 
 */
package shadow.application.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import shadow.renderer.SFNode;
import shadow.renderer.data.SFDataAsset;
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
	private String[] fileList = new String[] {	"test0002",
												"test0004",
												"test0005",
												"test0006"};
	
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
	
	public void generateSFLibraryFromXML(String fileName) {
		SFObjectsLibrary lib;
		lib = new SFObjectsLibrary();
		SFObjectsLibraryDecoder decoder = new SFObjectsLibraryDecoder(lib);
		SFXMLDataInterpreter interpreter = new SFXMLDataInterpreter(decoder);
		interpreter.generateInterpretation(ROOT + "/" + fileName );
		SFDataUtility.saveDataset(ROOT, fileName.replace(".xml", "") + ".sf", lib);
	}
	
	public void loadSFLibrary(String fileName) {
		SFObjectsLibrary lib;
		lib = (SFObjectsLibrary) SFDataUtility.loadDataset(ROOT, fileName);
		this.library.addLibrary(lib);
	}
	
	public void loadDefaultAssetLibrary(String fileName) {
		SFObjectsLibrary lib;
		lib = (SFObjectsLibrary) SFDataUtility.loadDataset(ROOT, fileName );
		this.library.put("DefaultAssetLibrary",lib);
	}
	
	public void loadDefaultReferences(String fileName) {
		SFObjectsLibrary lib;
		lib = (SFObjectsLibrary) SFDataUtility.loadDataset(ROOT, fileName );
		this.defaultReferencesLibrary.addLibrary(lib);
		this.library.put("DefaultReferences", this.defaultReferencesLibrary);
	}
	
	public void loadDefaultData() {
		loadDefaultAssetLibrary("default.sf");
		loadDefaultReferences("defaultReferences.sf");
	}
	
	public void generateDefaultReferencesLibrary() {
		//test0002
		this.defaultReferencesLibrary.put("BasicMatColours", new SFDefaultDatasetReference("MatColours", System.currentTimeMillis()));
		this.defaultReferencesLibrary.put("Mushroom", new SFDefaultDatasetReference("Cube", System.currentTimeMillis()));
		this.defaultReferencesLibrary.put("RedMushroom", new SFDefaultDatasetReference("GreenCube", System.currentTimeMillis()));
		//test0004
		this.defaultReferencesLibrary.put("PerlinMushroom", new SFDefaultDatasetReference("GreenCube", System.currentTimeMillis()));
		this.defaultReferencesLibrary.put("TexturedMushroom", new SFDefaultDatasetReference("Cube", System.currentTimeMillis()));
		this.defaultReferencesLibrary.put("PerlinTexture", new SFDefaultDatasetReference("Texture", System.currentTimeMillis()));
		//test0005
		this.defaultReferencesLibrary.put("ReddishGrayAndBrightMushroom", new SFDefaultDatasetReference("GreenCube", System.currentTimeMillis()));
		this.defaultReferencesLibrary.put("TexturedMushroom", new SFDefaultDatasetReference("Cube", System.currentTimeMillis()));
		this.defaultReferencesLibrary.put("PerlinTexture", new SFDefaultDatasetReference("Texture", System.currentTimeMillis()));
		this.defaultReferencesLibrary.put("ReddishGrayAndBrightPerlinNoise", new SFDefaultDatasetReference("Texture", System.currentTimeMillis()));
		//test0006
		this.defaultReferencesLibrary.put("MushroomObject01", new SFDefaultDatasetReference("GreenCube", System.currentTimeMillis()));
		this.defaultReferencesLibrary.put("MushroomObject02", new SFDefaultDatasetReference("GreenCube", System.currentTimeMillis()));
		this.defaultReferencesLibrary.put("MushroomObject03", new SFDefaultDatasetReference("GreenCube", System.currentTimeMillis()));
		this.defaultReferencesLibrary.put("PerlinTexture", new SFDefaultDatasetReference("Texture", System.currentTimeMillis()));
		this.defaultReferencesLibrary.put("ReddishGrayAndBrightPerlinNoise", new SFDefaultDatasetReference("Texture", System.currentTimeMillis()));
		this.defaultReferencesLibrary.put("Scene01", new SFDefaultDatasetReference("GreenCube", System.currentTimeMillis()));
		
		SFDataUtility.saveDataset(ROOT, "defaultReferences.sf", this.defaultReferencesLibrary);
		defaultReferencesLibrary = new SFObjectsLibrary();
	}
	
	public void generateTestLibrariesFromXML() {
		for (String file : fileList) {
			generateSFLibraryFromXML(file + ".xml");
		}
	}
	
	public void loadTestLibraries() {
		for (String file : fileList) {
			loadSFLibrary(file + ".sf");
		}
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