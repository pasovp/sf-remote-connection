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
import shadow.system.SFUpdater;
import shadow.system.data.SFDataCenter;
import shadow.system.data.SFDataCenterListener;
import shadow.system.data.SFDataset;
import shadow.system.data.SFObjectsLibrary;
import shadow.system.data.remote.wip.DataCenterRequest;
import shadow.system.data.remote.wip.IFailedRequestListener;
import shadow.system.data.remote.wip.SFDataCenterCreationException;
import shadow.system.data.remote.wip.SFDatasetReplacement;
import shadow.system.data.remote.wip.SFRemoteDataCenter;
import shadow.system.data.remote.wip.SFRemoteDataCenterRequests;

public abstract class AbstractClient {
	
	private static ExecutorService threadExecutor = Executors.newCachedThreadPool();
	private Boolean windowOpened = false;
	private SFViewer viewer = null;
	private SFTextureViewerN txviewer;
	private CommunicationProtocol<IClientCommunicationProtocolTask> protocol = new CommunicationProtocol<IClientCommunicationProtocolTask>();
	
	private String host = "acquarius";
	private int	port = 4444;
	
	private Boolean loadDefaultReps = false;
	private Boolean loadDefaultAssets = false;
	private Boolean defaultLibrariesError = false;
	
	private final String REPLACEMENTSLIB = "DefaultReplacements";
	private final String DEFAULTASSETSLIB = "DefaultAssetLibrary";
	
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
		
		loadDefaultData();
		//implementation.loadDefaultData();
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
	
	
	public void loadDefaultData() {
		
		SFRemoteDataCenterRequests.getRequest().addUpdateListener(REPLACEMENTSLIB, new DataCenterRequest(
			new SFDataCenterListener<SFDataset>() {
				@Override
				public void onDatasetAvailable(String name, SFDataset dataset) {
					((SFRemoteDataCenter)(SFDataCenter.getDataCenter().getDataCenterImplementation())).setDefaultReplacementsLibrary((SFObjectsLibrary)dataset);
					
					//defaultReplacementsLibrary.addLibrary((SFObjectsLibrary)dataset);
					loadDefaultReps = true;
				}
			},
			new IFailedRequestListener() {
				
				@Override
				public void onFailedRequest() {
					defaultLibrariesError = true;
					loadDefaultReps = true;
				}
			}
		));
		
		SFRemoteDataCenterRequests.getRequest().addUpdateListener(DEFAULTASSETSLIB, new DataCenterRequest(
			new SFDataCenterListener<SFDataset>() {
				@Override
				public void onDatasetAvailable(String name, SFDataset dataset) {
					((SFRemoteDataCenter)(SFDataCenter.getDataCenter().getDataCenterImplementation())).setLibrary((SFObjectsLibrary)dataset);
					
					//library.addLibrary((SFObjectsLibrary)dataset);
					loadDefaultAssets = true;
				}
			},
			new IFailedRequestListener() {
				
				@Override
				public void onFailedRequest() {
					defaultLibrariesError = true;
					loadDefaultAssets = true;
				}
			}
		));
		
		SFRemoteDataCenterRequests.getRequest().addRequest(REPLACEMENTSLIB);
		SFRemoteDataCenterRequests.getRequest().addRequest(DEFAULTASSETSLIB);
		System.err.println("Time:" + System.currentTimeMillis() + " default libraries requested");
		
		while(!loadDefaultReps || !loadDefaultAssets){
			SFUpdater.refresh();
			try {
				Thread.sleep(100);
				System.err.println("Time:" + System.currentTimeMillis() + " waiting for default libraries ");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(defaultLibrariesError){
			throw new SFDataCenterCreationException("Client: can't download default assets libraries");
		}
	}
	
}
