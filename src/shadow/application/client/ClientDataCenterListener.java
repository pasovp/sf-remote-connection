package shadow.application.client;

import shadow.renderer.SFNode;
import shadow.renderer.SFObjectModel;
import shadow.renderer.contents.tests.common.CommonMaterial;
import shadow.renderer.data.SFDataAsset;
import shadow.renderer.viewer.SFViewer;
import shadow.system.data.SFDataCenterListener;

public class ClientDataCenterListener<T extends SFDataAsset<SFNode>> implements SFDataCenterListener<SFDataAsset<SFNode>> {
	//private SFObjectModel mainNode = new SFObjectModel();
	private boolean windowOpened = false;
	private SFViewer viewer;
	//private static int counter;
	
	public ClientDataCenterListener() {
	}
	
	@Override
	public synchronized void onDatasetAvailable(String name, SFDataAsset<SFNode> dataset) {
		//SFObjectModel model=(SFObjectModel)dataset.getResource();
		
		try {
			if(windowOpened == false) {
				viewer = SFViewer.generateFrame(dataset.getResource(),
					//CommonMaterial.generateColoursController(model),
					SFViewer.getLightStepController(),
					SFViewer.getRotationController(),
					SFViewer.getWireframeController(),
					SFViewer.getZoomController());
//				counter++;
//				if(counter>=2){
//					throw new RuntimeException("2 finestre");
//				}
				windowOpened = true;
			} else {
				viewer.setNode(dataset.getResource());
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
