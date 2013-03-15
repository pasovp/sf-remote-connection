package sfrc.application.server.test;

import shadow.renderer.data.java.SFObjectsLibraryDecoder;
import shadow.renderer.data.java.SFXMLDataInterpreter;
import shadow.renderer.data.utils.SFDataUtility;
import shadow.renderer.data.utils.SFViewerDatasetFactory;
import shadow.system.data.SFDataCenter;
import shadow.system.data.SFObjectsLibrary;
import shadow.system.data.remote.wip.SFDatasetReplacement;

public class DatasetReplacementTest_03 {
	
	private static SFObjectsLibrary defaultReplacementsLibrary;
	private static final String ROOT = "appdata/replacement_tests";
	private static final String ORIGINALFILE = "replacement_test01";
	private static final String FILENAME = "replacement_test03";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SFViewerDatasetFactory factory = new SFViewerDatasetFactory();
		factory.addSFDataset(new SFDatasetReplacement());
		SFDataCenter.setDatasetFactory(factory);
		
		// load the original library from  DatasetReplacementTest_01
		defaultReplacementsLibrary = new SFObjectsLibrary();
		SFObjectsLibraryDecoder decoder = new SFObjectsLibraryDecoder(defaultReplacementsLibrary);
		SFXMLDataInterpreter interpreter = new SFXMLDataInterpreter(decoder);
		interpreter.generateInterpretation(ROOT + "/" + ORIGINALFILE + ".xml" );
		// store the library in a sf file
		SFDataUtility.saveDataset(ROOT, FILENAME + ".sf", defaultReplacementsLibrary);
		
		
		// load the library from the sf file 
		defaultReplacementsLibrary = new SFObjectsLibrary();
		SFObjectsLibrary lib;
		lib = (SFObjectsLibrary) SFDataUtility.loadDataset(ROOT, FILENAME + ".sf");
		defaultReplacementsLibrary.addLibrary(lib);
		
		// store the library in a new xml file
		SFDataUtility.saveXMLFile(ROOT, FILENAME, defaultReplacementsLibrary);
		
	}

}
