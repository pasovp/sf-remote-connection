
package sfrc.application.client.test;

import java.io.IOException;
import java.net.UnknownHostException;

import junk.SFDefaultDatasetReference;

import shadow.image.SFRenderedTexturesSet;
import shadow.image.SFTexture;
import shadow.renderer.SFNode;
import shadow.renderer.contents.tests.common.CommonPipeline;
import shadow.renderer.contents.tests.common.CommonTextures;
import shadow.renderer.data.SFDataAsset;
import shadow.renderer.data.SFObjectModelData;
import shadow.renderer.data.utils.SFViewerDatasetFactory;
import shadow.renderer.viewer.SFTextureViewer;
import shadow.renderer.viewer.SFViewer;
import shadow.system.data.SFDataCenter;
import shadow.system.data.SFDataCenterListener;
import shadow.system.data.remote.wip.SFRemoteDataCenter;

/**
 * @author Luigi Pasotti
 *
 */
public class Test0007<T extends SFDataAsset<SFNode>> implements SFDataCenterListener<SFDataAsset<SFRenderedTexturesSet>> {
	
	private boolean windowOpened = false;
	private SFViewer viewer;
	private SFTextureViewer txviewer;
	
	public Test0007() {
		// TODO Auto-generated constructor stub
	}
	
	public void configureDataCenter(){
		SFViewerDatasetFactory factory = new SFViewerDatasetFactory();
		factory.addSFDataset(new SFDefaultDatasetReference());
		SFDataCenter.setDatasetFactory(factory);
		SFRemoteDataCenter implementation = new SFRemoteDataCenter();
		SFDataCenter.setDataCenterImplementation(implementation);
		implementation.loadDefaultData();
	}

	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		//SFViewer.prepare();
		CommonPipeline.prepare();
		Test0007 client = new Test0007();
		client.configureDataCenter();
		//ClientDataCenterListener<SFObjectModelData> listener = new ClientDataCenterListener<SFObjectModelData>();
		
		SFDataCenter.getDataCenter().makeDatasetAvailable("PebblesTextures", client);
	}

//	@Override
//	public synchronized void onDatasetAvailable(String name, SFDataAsset<SFNode> dataset) {
//		
//		try {
//			if(windowOpened == false) {
//				viewer = SFViewer.generateFrame(dataset.getResource(),
//					//CommonMaterial.generateColoursController(model),
//					SFViewer.getLightStepController(),
//					SFViewer.getRotationController(),
//					SFViewer.getWireframeController(),
//					SFViewer.getZoomController()
//					);
//				windowOpened = true;
//			} else {
//				viewer.setNode(dataset.getResource());
//			}
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
//	}

	@Override
	public void onDatasetAvailable(String name,SFDataAsset<SFRenderedTexturesSet> dataset) {
		try {
			SFRenderedTexturesSet set=dataset.getResource();
			SFTexture texture=new SFTexture(set, 1);
			if(windowOpened == false) {
//				viewer = SFViewer.generateFrame(dataset.getResource(),
//					//CommonMaterial.generateColoursController(model),
//					SFViewer.getLightStepController(),
//					SFViewer.getRotationController(),
//					SFViewer.getWireframeController(),
//					SFViewer.getZoomController()
//					);
				txviewer = SFTextureViewer.generateFrame(texture,CommonTextures.generateTextureSelectionController(texture, set.getTextureSize()));
				windowOpened = true;
			} else {
				txviewer.setTexture(texture);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
