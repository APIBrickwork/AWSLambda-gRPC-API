package services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import io.grpc.stub.StreamObserver;
import services.Chefmate.Request.CreateVMRequest;
import services.Chefmate.Request.DestroyVMRequest;
import services.Chefmate.Response.CreateVMResponse;
import services.Chefmate.Response.DestroyVMResponse;
import util.ChefAttributesWriter;
import util.Config;
import util.ShellExecuter;

public class EC2OpsImpl implements EC2OpsGrpc.EC2Ops
{
	private static final Logger logger = Logger.getLogger(EC2OpsImpl.class.getName());

	@Override
	public void createVM(CreateVMRequest request, StreamObserver<CreateVMResponse> responseObserver)
	{
		// TODO: Implement
		logger.info("### Received request for createVM with info:\n " + request.toString());
		
		// Write the according attributes
		Config config = Config.getInstance(false, false);
		String filename = config.getChefAttributesDefaultFilename("cb-chefmate");
		logger.info("### Writing Attributes file to: " + filename);
		ChefAttributesWriter.writeAttributesFile(filename, request);

		// Call cookbook for provisioning
		// chef-client -z -o 'recipe[cb-chefmate::aws_provision]'
		String execDir = config.getChefRepoPath();
		List<String> commands = new ArrayList<>();
		commands.add("chef-client");
		commands.add("-z");
		commands.add("-o");
		commands.add("recipe[cb-chefmate::aws_provision]");
		
		logger.info("### Starting provisioning task from directory " + execDir + " using commands: " + commands);
		ShellExecuter.execute(config.getChefRepoPath(), commands);
		
		// TODO: How do I get the ID??? Maybe via machine-batch or so??
		// TODO: Form response!!
		
		
	}

	@Override
	public void destroyVM(DestroyVMRequest request, StreamObserver<DestroyVMResponse> responseObserver)
	{
		// TODO Auto-generated method stub
	}

}
