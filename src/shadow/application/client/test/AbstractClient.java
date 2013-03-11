package shadow.application.client.test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JMenuBar;

import shadow.image.SFRenderedTexturesSet;
import shadow.image.SFTexture;
import shadow.renderer.SFNode;
import shadow.renderer.SFObjectModel;
import shadow.renderer.contents.tests.common.CommonMaterial;
import shadow.renderer.contents.tests.common.CommonPipeline;
import shadow.renderer.contents.tests.common.CommonTextures;
import shadow.renderer.data.SFDataAsset;
import shadow.renderer.data.utils.SFViewerDatasetFactory;
import shadow.renderer.viewer.SFFrameController;
import shadow.renderer.viewer.SFViewer;
import shadow.renderer.viewer.wip.SFTextureViewerN;
import shadow.system.data.SFDataCenter;
import shadow.system.data.SFDataCenterListener;
import shadow.system.data.wip.SFDatasetReplacement;
import shadow.system.data.wip.SFRemoteDataCenter;
import shadow.system.data.wip.SFRemoteDataCenterRequestsCreationTask;

public abstract class AbstractClient {
	
	private static ExecutorService threadExecutor = Executors.newCachedThreadPool();
	private boolean windowOpened = false;
	private SFViewer viewer;
	private SFTextureViewerN txviewer;
	
	public static void execute(AbstractClient test){
		test.execute();
	}

	private void execute() {
		setupAmbient();
		
		viewTestData();
	}
	
	public abstract void viewTestData();

	public void setupAmbient() {
		threadExecutor = Executors.newCachedThreadPool();
		threadExecutor.execute(new SFRemoteDataCenterRequestsCreationTask());
		
		SFViewerDatasetFactory factory = new SFViewerDatasetFactory();
		factory.addSFDataset(new SFDatasetReplacement());
		SFDataCenter.setDatasetFactory(factory);
		SFRemoteDataCenter implementation = new SFRemoteDataCenter();
		SFDataCenter.setDataCenterImplementation(implementation);
		
		
		implementation.loadDefaultData();
		CommonPipeline.prepare();
	}
	
	/**
	 * Show a model already loaded into DataCenter in the SFViewer Frame , using default Frame Controllers.
	 * @param nodeName the name of the model
	 */
	public void viewNode(String nodeName){
		viewNode(nodeName, 
				false,
				SFViewer.getLightStepController(),
				SFViewer.getRotationController(),
				SFViewer.getWireframeController(),
				SFViewer.getZoomController());
	}
	
	/**
	 * Show a model already loaded into DataCenter in the SFViewer Frame , using assigned Frame Controllers.
	 * @param nodeName the name of the model
	 * @param controllers the controllers this frame should use
	 */
	public void viewNode(String nodeName,final boolean colorController ,final SFFrameController... controllers){
		SFDataCenter.getDataCenter().makeDatasetAvailable(nodeName, new SFDataCenterListener<SFDataAsset<SFNode>>() {
			@Override
			public void onDatasetAvailable(String name, SFDataAsset<SFNode> dataset) {
				ArrayList<SFFrameController> controls = new ArrayList<SFFrameController>(Arrays.asList(controllers));
				if(colorController) {
					SFFrameController cController = CommonMaterial.generateColoursController((SFObjectModel)dataset.getResource());
					controls.add(cController);
				}
				
				if(windowOpened == false) {
					viewer = SFViewer.generateFrame(dataset.getResource(),controls.toArray(new SFFrameController[controls.size()]));
					windowOpened = true;
				} else {
					
					JMenuBar bar=new JMenuBar();
					for (SFFrameController sfFrameController : controls) {
						bar.add(viewer.getFrame().generateMenu(sfFrameController));
					}
					viewer.getFrame().setJMenuBar(bar);
					viewer.setNode(dataset.getResource());
				}
			}
		});
	}
	
	
	/**
	 * Show a textures Set already loaded into DataCenter in the SFViewer Frame , using default Frame Controllers.
	 * @param nodeName the name of the model
	 * @param startingTextureIndex the texture index to be used when the frame is shown
	 */
	public void viewTextureSet(String textureSetName,final int startingTextureIndex){
		
		SFDataCenter.getDataCenter().makeDatasetAvailable(textureSetName, new SFDataCenterListener<SFDataAsset<SFRenderedTexturesSet>>() {
			@Override
			public void onDatasetAvailable(String name, SFDataAsset<SFRenderedTexturesSet> dataset) {
				SFRenderedTexturesSet set=dataset.getResource();
				SFTexture texture=new SFTexture(set, startingTextureIndex);
				if(windowOpened == false) {
					txviewer = SFTextureViewerN.generateFrame(texture,CommonTextures.generateTextureSelectionController(texture, set.getTextureSize()));
					windowOpened = true;
				} else {
					SFFrameController selectionController = CommonTextures.generateTextureSelectionController(texture, set.getTextureSize());
					
					JMenuBar bar=new JMenuBar();
					bar.add(txviewer.getFrame().generateMenu(selectionController));
					txviewer.getFrame().setJMenuBar(bar);
					
					txviewer.setTexture(texture);
					
				}
			}
		});	
	}
	
}
