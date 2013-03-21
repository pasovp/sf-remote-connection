package shadow.system.data.remote.wip;

public class SFRemoteDataCenterRequests extends SFRemoteRequests<DataCenterRequest>{

	private static SFRemoteDataCenterRequests request = new SFRemoteDataCenterRequests();
	
	private SFRemoteDataCenterRequests(){
		//singleton
	}

	public static SFRemoteDataCenterRequests getRequest() {
		return request;
	}
}
