/**
 * 
 */
package shadow.application.server.test;

import shadow.application.server.IServerDataLibrary;
import shadow.renderer.data.java.SFObjectsLibraryDecoder;
import shadow.renderer.data.java.SFXMLDataInterpreter;
import shadow.renderer.data.utils.SFDataUtility;
import shadow.renderer.data.utils.SFViewerDatasetFactory;
import shadow.system.data.SFDataCenter;
import shadow.system.data.SFDataset;
import shadow.system.data.SFObjectsLibrary;
import shadow.system.data.wip.SFDatasetReplacement;

/**
 * @author Luigi Pasotti
 *
 */
public class ServerDataHandler implements IServerDataLibrary {

	private SFObjectsLibrary library;
	//private SFObjectsLibrary defaultReplacementsLibrary;
	
	private final String REPLACEMENTSLIB = "DefaultReplacements";
	private final String DEFAULTASSETSLIB = "DefaultAssetLibrary";
	
	private String ROOT = "appdata";
	private String DefaultAssetFILE = "default";
	private String ReplacementsFILE = "defaultReplacements";
	private String[] fileList = new String[] {	"test0002",
												"test0004",
												"test0005",
												"test0006"
												//"test0014"
												};
	
	/**
	 * 
	 */
	public ServerDataHandler() {
		super();
		this.library = new SFObjectsLibrary();
		//this.defaultReplacementsLibrary = new SFObjectsLibrary();
		
		SFViewerDatasetFactory factory = new SFViewerDatasetFactory();
		factory.addSFDataset(new SFDatasetReplacement());
		SFDataCenter.setDatasetFactory(factory);
	}

	public void generateTestLibrariesFromXML() {
		for (String file : fileList) {
			generateSFLibraryFromXML(file);
		}
	}
	
	public void generateDefaultDataFromXML() {
		generateSFLibraryFromXML(DefaultAssetFILE);
		generateSFLibraryFromXML(ReplacementsFILE);
	}
	
	public void loadTestLibraries() {
		for (String file : fileList) {
			loadSFLibrary(file + ".sf");
		}
	}
	
	public void loadDefaultData() {
		loadDefaultAssetLibrary(DefaultAssetFILE);
		loadDefaultReferences(ReplacementsFILE);
	}
	
	
	public void loadSFLibrary(String fileName) {
		SFObjectsLibrary lib;
		lib = (SFObjectsLibrary) SFDataUtility.loadDataset(ROOT, fileName);
		this.library.addLibrary(lib);
	}
	
	public void loadDefaultAssetLibrary(String fileName) {
		SFObjectsLibrary lib;
		lib = (SFObjectsLibrary) SFDataUtility.loadDataset(ROOT, fileName + ".sf" );
		this.library.put(DEFAULTASSETSLIB,lib);
	}
	
	public void loadDefaultReferences(String fileName) {
		SFObjectsLibrary lib;
		lib = (SFObjectsLibrary) SFDataUtility.loadDataset(ROOT, fileName + ".sf" );
		//this.defaultReplacementsLibrary.addLibrary(lib);
		this.library.put(REPLACEMENTSLIB, lib);
	}
	
	
	public void generateSFLibraryFromXML(String fileName) {
		SFObjectsLibrary lib;
		lib = new SFObjectsLibrary();
		SFObjectsLibraryDecoder decoder = new SFObjectsLibraryDecoder(lib);
		SFXMLDataInterpreter interpreter = new SFXMLDataInterpreter(decoder);
		interpreter.generateInterpretation(ROOT + "/" + fileName  + ".xml" );
		SFDataUtility.saveDataset(ROOT, fileName + ".sf", lib);
	}
	
	/* (non-Javadoc)
	 * @see shadow.application.server.IServerDataLibrary#getDataset(java.lang.String)
	 */
	@Override
	public SFDataset getDataset(String datasetName) {
		SFDataset dataset = library.retrieveDataset(datasetName);
		return dataset;
	}
}