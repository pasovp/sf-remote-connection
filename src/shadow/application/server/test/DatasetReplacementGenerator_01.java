package shadow.application.server.test;

import shadow.renderer.data.utils.SFDataUtility;
import shadow.renderer.data.utils.SFViewerDatasetFactory;
import shadow.system.data.SFDataCenter;
import shadow.system.data.SFObjectsLibrary;
import shadow.system.data.wip.SFDatasetReplacement;

public class DatasetReplacementGenerator_01 {
	
	private static SFObjectsLibrary defaultReplacementsLibrary = new SFObjectsLibrary();
	private static final String ROOT = "appdata";
	private static final String FILENAME = "defaultReplacements";
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SFViewerDatasetFactory factory = new SFViewerDatasetFactory();
		factory.addSFDataset(new SFDatasetReplacement());
		SFDataCenter.setDatasetFactory(factory);
		
		defaultReplacementsLibrary.put("BasicMatColours", new SFDatasetReplacement("MatColours", System.currentTimeMillis()));
		defaultReplacementsLibrary.put("Mushroom", new SFDatasetReplacement("Cube", System.currentTimeMillis()));
		defaultReplacementsLibrary.put("RedMushroom", new SFDatasetReplacement("GreenCube", System.currentTimeMillis()));
		//test0004
		defaultReplacementsLibrary.put("PerlinMushroom", new SFDatasetReplacement("TexturedCube", System.currentTimeMillis()));
		defaultReplacementsLibrary.put("TexturedMushroom", new SFDatasetReplacement("CubeTx", System.currentTimeMillis()));
		defaultReplacementsLibrary.put("PerlinTexture", new SFDatasetReplacement("Texture", System.currentTimeMillis()));
		//test0005
		defaultReplacementsLibrary.put("ReddishGrayAndBrightMushroom", new SFDatasetReplacement("GreenCube", System.currentTimeMillis()));
		defaultReplacementsLibrary.put("TexturedMushroom", new SFDatasetReplacement("CubeTx", System.currentTimeMillis()));
		defaultReplacementsLibrary.put("PerlinTexture", new SFDatasetReplacement("Texture", System.currentTimeMillis()));
		defaultReplacementsLibrary.put("ReddishGrayAndBrightPerlinNoise", new SFDatasetReplacement("Texture", System.currentTimeMillis()));
		//test0006
		defaultReplacementsLibrary.put("MushroomObject01", new SFDatasetReplacement("TexturedCube", System.currentTimeMillis()));
		defaultReplacementsLibrary.put("MushroomObject02", new SFDatasetReplacement("TexturedCube", System.currentTimeMillis()));
		defaultReplacementsLibrary.put("MushroomObject03", new SFDatasetReplacement("TexturedCube", System.currentTimeMillis()));
		defaultReplacementsLibrary.put("PerlinTexture", new SFDatasetReplacement("Texture", System.currentTimeMillis()));
		defaultReplacementsLibrary.put("ReddishGrayAndBrightPerlinNoise", new SFDatasetReplacement("Texture01", System.currentTimeMillis()));
		defaultReplacementsLibrary.put("Scene01", new SFDatasetReplacement("TexturedCube", System.currentTimeMillis()));
		
		SFDataUtility.saveXMLFile(ROOT, FILENAME, defaultReplacementsLibrary);

	}

}
