package shadow.application.client.test;

import shadow.renderer.SFNode;
import shadow.renderer.data.SFDataAsset;
import shadow.renderer.viewer.SFFrameController;
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
		
		try {
			if(windowOpened == false) {
				viewer = SFViewer.generateFrame(dataset.getResource(),
					//CommonMaterial.generateColoursController(model),
					SFViewer.getLightStepController(),
					SFViewer.getRotationController(),
					SFViewer.getWireframeController(),
					SFViewer.getZoomController(),
					new SFDebugController(this)
					);
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
	
	private static class SFDebugController implements SFFrameController {
		
		private String name = "Debug";
		private String[] alternatives = new String[]{"break"};
		private ClientDataCenterListener listener; 
		
		public SFDebugController(ClientDataCenterListener listener) {
			this.listener = listener;
		}
		
		@Override
		public String getName() {
			return name;
		}

		@Override
		public String[] getAlternatives() {
			return alternatives;
		}

		@Override
		public void select(int index) {
			//DEBUG: set here a breakpoint to see the viewer state
		}
		
	}
}
