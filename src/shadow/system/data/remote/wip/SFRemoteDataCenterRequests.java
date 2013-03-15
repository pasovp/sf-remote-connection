package shadow.system.data.remote.wip;

public class SFRemoteDataCenterRequests extends SFRemoteRequests<DataCenterRequest>{

	private static SFRemoteDataCenterRequests request=new SFRemoteDataCenterRequests();
	
	private SFRemoteDataCenterRequests(){
		
	}

	public static SFRemoteDataCenterRequests getRequest() {
		return request;
	}
}
