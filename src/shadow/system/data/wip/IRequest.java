package shadow.system.data.wip;

public interface IRequest {
	public void executeRequest(String name);
	public void failedRequest(String name);
}
