package services;

import io.grpc.stub.StreamObserver;
import services.Chefmate.Request.CreateVMRequest;
import services.Chefmate.Request.DestroyVMRequest;
import services.Chefmate.Response.CreateVMResponse;
import services.Chefmate.Response.DestroyVMResponse;

public class EC2OpsImpl implements EC2OpsGrpc.EC2Ops
{

	@Override
	public void createVM(CreateVMRequest request, StreamObserver<CreateVMResponse> responseObserver)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroyVM(DestroyVMRequest request, StreamObserver<DestroyVMResponse> responseObserver)
	{
		// TODO Auto-generated method stub
		
	}

}
