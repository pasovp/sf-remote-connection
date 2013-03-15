package sfrc.application.server.test;

import shadow.renderer.data.utils.SFDataUtility;
import shadow.renderer.data.utils.SFViewerDatasetFactory;
import shadow.system.data.SFDataCenter;
import shadow.system.data.SFObjectsLibrary;
import shadow.system.data.remote.wip.SFDatasetReplacement;

public class DatasetReplacementTest_01 {
	
	private static SFObjectsLibrary defaultReplacementsLibrary;
	private static final String ROOT = "appdata/replacement_tests";
	private static final String FILENAME = "replacement_test01";
	
	/** 
	 * @param args
	 */
	public static void main(String[] args) {
		SFViewerDatasetFactory factory = new SFViewerDatasetFactory();
		factory.addSFDataset(new SFDatasetReplacement());
		SFDataCenter.setDatasetFactory(factory);
		
		// generating a new library with one SFDatasetReplacement
		defaultReplacementsLibrary = new SFObjectsLibrary();
		SFDatasetReplacement replacement = new SFDatasetReplacement("Mushroom", System.currentTimeMillis());
		defaultReplacementsLibrary.put("RedMushroom", replacement);
		
		// store the library in a xml file
		SFDataUtility.saveXMLFile(ROOT, FILENAME, defaultReplacementsLibrary);
	}

}
