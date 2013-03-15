
package sfrc.application.client.test;


/**
 * @author Luigi Pasotti
 *
 */
public class Test0006 extends AbstractClient {

	/**
	 * @param args
	 */
	public static void main(String[] args){
		execute(new Test0006());
	}

	@Override
	public void viewTestData() {
		viewNode("Scene01");
	}
}
