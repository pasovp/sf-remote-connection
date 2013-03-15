package sfrc.application.server.test;

import shadow.renderer.data.java.SFObjectsLibraryDecoder;
import shadow.renderer.data.java.SFXMLDataInterpreter;
import shadow.renderer.data.utils.SFDataUtility;
import shadow.renderer.data.utils.SFViewerDatasetFactory;
import shadow.system.data.SFDataCenter;
import shadow.system.data.SFObjectsLibrary;
import shadow.system.data.remote.wip.SFDatasetReplacement;

public class DatasetReplacementTest_02 {
	
	private static SFObjectsLibrary defaultReplacementsLibrary;
	private static final String ROOT = "appdata/replacement_tests";
	private static final String ORIGINALFILE = "replacement_test01";
	private static final String FILENAME = "replacement_test02";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SFViewerDatasetFactory factory = new SFViewerDatasetFactory();
		factory.addSFDataset(new SFDatasetReplacement());
		SFDataCenter.setDatasetFactory(factory);
		
		// load a library with one SFDatasetReplacement within 
		defaultReplacementsLibrary = new SFObjectsLibrary();
		SFObjectsLibraryDecoder decoder = new SFObjectsLibraryDecoder(defaultReplacementsLibrary);
		SFXMLDataInterpreter interpreter = new SFXMLDataInterpreter(decoder);
		interpreter.generateInterpretation(ROOT + "/" + ORIGINALFILE + ".xml" );
		// store the library in another xml file
		SFDataUtility.saveXMLFile(ROOT, FILENAME, defaultReplacementsLibrary);
	}

}
