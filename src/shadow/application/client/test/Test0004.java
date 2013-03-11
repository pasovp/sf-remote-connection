
package shadow.application.client.test;


/**
 * @author Luigi Pasotti
 *
 */
public class Test0004 extends AbstractClient{
	
	/**
	 * @param args
	 */
	public static void main(String[] args){
		execute(new Test0004());
	}

	@Override
	public void viewTestData() {
		viewNode("PerlinMushroom");
	}
}
