package shadow.application.client;

import shadow.renderer.SFObjectModel;
import shadow.renderer.contents.tests.common.CommonMaterial;
import shadow.renderer.data.SFObjectModelData;
import shadow.renderer.viewer.SFViewer;
import shadow.system.data.wip.SFUpdatableDatasetListener;

public class ClientDataCenterListener<T extends SFObjectModelData> implements SFUpdatableDatasetListener<SFObjectModelData> {
	
	private SFObjectModel mainNode = new SFObjectModel();
	private boolean windowOpened = false;
	private SFViewer viewer;
	
	public ClientDataCenterListener() {
		//viewer = new SFViewer();
	}
	
	@Override
	public void onDatasetAvailable(String name, SFObjectModelData dataset) {
//		SFObjectModel model=(SFObjectModel)dataset.getResource();
//		mainNode.setModel(model.getModel());
//		
//		if(windowOpened == false) {
//			SFViewer.generateFrame(mainNode,
//				CommonMaterial.generateColoursController(mainNode),
//				SFViewer.getLightStepController(),
//				SFViewer.getRotationController(),
//				SFViewer.getWireframeController(),
//				SFViewer.getZoomController());
//			windowOpened = true;
//		}
		
		SFObjectModel model=(SFObjectModel)dataset.getResource();
		//mainNode.setModel(model.getModel());
		
		if(windowOpened == false) {
			viewer = SFViewer.generateFrame(model,
				CommonMaterial.generateColoursController(model),
				SFViewer.getLightStepController(),
				SFViewer.getRotationController(),
				SFViewer.getWireframeController(),
				SFViewer.getZoomController());
			windowOpened = true;
		} else {
			viewer.setNode(model);
		}
	}
	
	@Override
	public void onDatasetUpdate(String name, SFObjectModelData dataset) {
		
//		SFObjectModel model=(SFObjectModel)dataset.getResource();
//		mainNode.setModel(model.getModel());
//		
//		SFViewer.generateFrame(mainNode,
//				CommonMaterial.generateColoursController(mainNode),
//				SFViewer.getLightStepController(),
//				SFViewer.getRotationController(),
//				SFViewer.getWireframeController(),
//				SFViewer.getZoomController());
		
	}
}
