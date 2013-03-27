package sfrc.application.client.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JMenuBar;

import sfrc.application.client.IClientCommunicationProtocolTask;
import sfrc.application.client.RemoteDataCenterRequestsCreationTask;
import sfrc.base.communication.CommunicationProtocol;
import shadow.image.SFRenderedTexturesSet;
import shadow.image.SFTexture;
import shadow.renderer.SFNode;
import shadow.renderer.SFObjectModel;
import shadow.renderer.contents.tests.common.CommonPipeline;
import shadow.renderer.contents.tests.common.CommonTextures;
import shadow.renderer.data.SFDataAsset;
import shadow.renderer.data.utils.SFViewerDatasetFactory;
import shadow.renderer.viewer.SFFrameController;
import shadow.renderer.viewer.SFViewer;
import shadow.renderer.viewer.wip.SFTextureViewerN;
import shadow.system.data.SFDataCenter;
import shadow.system.data.SFDataCenterListener;
import shadow.system.data.remote.wip.SFDatasetReplacement;
import shadow.system.data.remote.wip.SFRemoteDataCenter;

public abstract class AbstractClient {
	
	private static ExecutorService threadExecutor = Executors.newCachedThreadPool();
	private Boolean windowOpened = false;
	private SFViewer viewer = null;
	private SFTextureViewerN txviewer;
	private CommunicationProtocol<IClientCommunicationProtocolTask> protocol = new CommunicationProtocol<IClientCommunicationProtocolTask>();
	
	private String host = "acquarius";
	private int	port = 4444;
	
	public static void execute(AbstractClient test){
		test.execute();
	}

	private void execute() {
		setupAmbient();
		
		viewTestData();
	}
	
	public abstract void viewTestData();
	public abstract void setupProtocol();
	
	public void setupAmbient() {
		threadExecutor = Executors.newCachedThreadPool();
		
		setupProtocol();
		threadExecutor.execute(new RemoteDataCenterRequestsCreationTask(protocol,host,port));
		
		SFViewerDatasetFactory factory = new SFViewerDatasetFactory();
		factory.addSFDataset(new SFDatasetReplacement());
		SFDataCenter.setDatasetFactory(factory);
		SFRemoteDataCenter implementation = new SFRemoteDataCenter();
		SFDataCenter.setDataCenterImplementation(implementation);
		
		
		implementation.loadDefaultData();
		CommonPipeline.prepare();
	}
	
	public CommunicationProtocol<IClientCommunicationProtocolTask> getProtocol() {
		return protocol;
	}
	
	/**
	 * Show a model already loaded into DataCenter in the SFViewer Frame , using default Frame Controllers.
	 * @param nodeName the name of the model
	 */
	public void viewNode(String nodeName){
		viewNode(nodeName, 
				false,
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
	public void viewNode(String nodeName,final boolean colorController, final boolean textureController ,final SFFrameController... controllers){
		
		viewer = SFViewer.generateFrame(new SFObjectModel(),controllers);
		
		SFDataCenter.getDataCenter().makeDatasetAvailable(nodeName, new SFDataCenterListener<SFDataAsset<SFNode>>() {
			@Override
			public void onDatasetAvailable(String name, SFDataAsset<SFNode> dataset) {
				viewer.setNode(dataset.getResource());
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
