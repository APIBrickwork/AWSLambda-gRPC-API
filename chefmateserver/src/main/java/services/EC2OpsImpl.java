package services;

import java.util.Properties;

import io.grpc.stub.StreamObserver;
import services.Chefmate.Request.CreateVMRequest;
import services.Chefmate.Request.DestroyVMRequest;
import services.Chefmate.Response.CreateVMResponse;
import services.Chefmate.Response.DestroyVMResponse;
import util.ChefAttributesWriter;

public class EC2OpsImpl implements EC2OpsGrpc.EC2Ops
{

	@Override
	public void createVM(CreateVMRequest request, StreamObserver<CreateVMResponse> responseObserver)
	{
		// TODO: Implement
	}

	@Override
	public void destroyVM(DestroyVMRequest request, StreamObserver<DestroyVMResponse> responseObserver)
	{
		// TODO Auto-generated method stub
		
	}

}
