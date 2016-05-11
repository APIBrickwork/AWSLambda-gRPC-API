package services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import io.grpc.stub.StreamObserver;
import services.Chefmate.CreateVMRequest;
import services.Chefmate.CreateVMResponse;
import services.Chefmate.DestroyVMRequest;
import services.Chefmate.DestroyVMResponse;
import services.Chefmate.EmptyRequest;
import services.Chefmate.VMInstanceId;
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
		String execDir = config.getChefRepoPath();
		List<String> commands = new ArrayList<>();
		commands.add("chef-client");
		commands.add("-z");
		commands.add("-o");
		commands.add("recipe[cb-chefmate::aws_provision]");
		
		logger.info("### Starting provisioning task from directory " + execDir + " using commands: " + commands);
		String outputLog = ShellExecuter.execute(config.getChefRepoPath(), commands);
		
		// Extract instance id from output
		String seekedString = "instance_ids";
		String[] splitInstanceIds = outputLog.split(seekedString);
		String[] splitLine = splitInstanceIds[1].split(System.lineSeparator());
		
		// Is only capable when a SINGLE instance was created (it may be possible create multiple instances with one script). THIS IS NOT INTENDED HERE!
		String instanceId = splitLine[0].trim().toLowerCase().replace(":", "").replace("[", "").replace("]", "").replace("(", "").replace(")", "").replace("\"", "");
		logger.info("### Created instance with id: " + instanceId);
		if(!instanceId.startsWith("i-")){
			logger.warning("### Mismatch with instance_id pattern. Should start with i-.... but was " + instanceId);
		}
		
		CreateVMResponse resp = CreateVMResponse.newBuilder().setInstanceId(VMInstanceId.newBuilder().setId(instanceId).build()).setOutputLog(outputLog).build();
		
		responseObserver.onNext(resp);
		responseObserver.onCompleted();
		logger.info("### Sent response.");
	}

	@Override
	public void destroyVM(DestroyVMRequest request, StreamObserver<DestroyVMResponse> responseObserver)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void destroyAllVMs(EmptyRequest request, StreamObserver<DestroyVMResponse> responseObserver)
	{
		// TODO Auto-generated method stub
		
	}

}
