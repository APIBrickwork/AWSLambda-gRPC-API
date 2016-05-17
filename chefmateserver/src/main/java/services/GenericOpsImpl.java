package services;

import io.grpc.stub.StreamObserver;
import services.Chefmate.ExecuteCookbookRequest;
import services.Chefmate.ExecuteCookbookResponse;
import services.GenericOpsGrpc.GenericOps;

public class GenericOpsImpl implements GenericOps
{

	@Override
	public void executeCookbook(ExecuteCookbookRequest request,
			StreamObserver<ExecuteCookbookResponse> responseObserver)
	{
		// TODO Auto-generated method stub
		
	}

}
