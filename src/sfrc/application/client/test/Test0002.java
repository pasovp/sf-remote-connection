
package sfrc.application.client.test;

import sfrc.application.client.tasks.AnalizeReplyClientCommunicationTask;
import sfrc.application.client.tasks.ClosingClientCommunicationTask;
import sfrc.application.client.tasks.FailClientCommunicationTask;
import sfrc.application.client.tasks.ReplyClientCommunicationTask;
import sfrc.application.client.tasks.ReplyEndClientCommunicationTask;
import sfrc.application.client.tasks.RequestClientCommunicationTask;
import shadow.renderer.viewer.SFViewer;

/**
 * @author Luigi Pasotti
 *
 */
public class Test0002 extends AbstractClient{
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args){
		execute(new Test0002());
	}

	@Override
	public void viewTestData() {
		viewNode("RedMushroom",true,false,SFViewer.getLightStepController());
	}

	@Override
	public void setupProtocol() {
		getProtocol().getProtocolMap().put(RequestClientCommunicationTask.getTaskName(), new RequestClientCommunicationTask());
		getProtocol().getProtocolMap().put(AnalizeReplyClientCommunicationTask.getTaskName(), new AnalizeReplyClientCommunicationTask());
		getProtocol().getProtocolMap().put(ReplyClientCommunicationTask.getTaskName(), new ReplyClientCommunicationTask());
		getProtocol().getProtocolMap().put(FailClientCommunicationTask.getTaskName(), new FailClientCommunicationTask());
		getProtocol().getProtocolMap().put(ClosingClientCommunicationTask.getTaskName(), new ClosingClientCommunicationTask());
		getProtocol().getProtocolMap().put(ReplyEndClientCommunicationTask.getTaskName(), new ReplyEndClientCommunicationTask());
	}
}
