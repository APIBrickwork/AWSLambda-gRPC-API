package services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import io.grpc.stub.StreamObserver;
import services.Chefmate.AWSInstanceId;
import services.Chefmate.CreateVMRequest;
import services.Chefmate.CreateVMResponse;
import services.Chefmate.DestroyVMRequest;
import services.Chefmate.DestroyVMResponse;
import util.ChefAttributesWriter;
import util.Config;
import util.ShellExecuter;

public class EC2OpsImpl implements EC2OpsGrpc.EC2Ops
{
	private static final Logger logger = Logger.getLogger(EC2OpsImpl.class.getName());

	@Override
	public void createVM(CreateVMRequest request, StreamObserver<CreateVMResponse> responseObserver)
	{
		logger.info("### Received request for createVM with info:\n " + request.toString());

//		// Write the according attributes
		Config config = Config.getInstance(false, false);
		String filename = config.getChefAttributesDefaultFilename("cb-chefmate");
		logger.info("### Writing Attributes file to: " + filename);
		ChefAttributesWriter.writeAttributesFile(filename, request);

		// Call cookbook for provisioning
		String execDir = config.getChefRepoPath();
		List<String> provisionCommands = new ArrayList<>();
		provisionCommands.add("chef-client");
		provisionCommands.add("-z");
		provisionCommands.add("-o");
		provisionCommands.add("recipe[cb-chefmate::aws_fogProvision]");

		logger.info(
				"### Starting provisioning task from directory " + execDir + " using commands: " + provisionCommands);
		String outputLog = ShellExecuter.execute(config.getChefRepoPath(), provisionCommands);

		// Extract instance id from output
		String instanceId = "";
		String[] splitInstanceId = outputLog.split(config.getChefMateInfo_Keyword_InstanceID());
		if (splitInstanceId.length > 1)
		{

			String[] splitLine = splitInstanceId[1].split(System.lineSeparator());
			instanceId = splitLine[0].trim();
		}

		// Extract Public DNS
		String publicDns = "";
		String[] splitPublicDns = outputLog.split(config.getChefMateInfo_Keyword_PublicDNS());
		if (splitPublicDns.length > 1)
		{

			String[] splitL = splitPublicDns[1].split(System.lineSeparator());
			publicDns = splitL[0].trim();
		}
		
		logger.info("### Provisioning Output: \n" + outputLog);
		
		// Use knife to bootstrap the created VM
		List<String> bootstrapCommands = new ArrayList<>();
		bootstrapCommands.add("knife");
		bootstrapCommands.add("bootstrap");
		bootstrapCommands.add("-z");
		bootstrapCommands.add(publicDns);
		bootstrapCommands.add("-i");
		String keyFile = System.getProperty("user.home") + "/.ssh/" + config.getAwsSSHKeyName() + ".pem";
		bootstrapCommands.add(keyFile);
		bootstrapCommands.add("--sudo");
		bootstrapCommands.add("-x");
		bootstrapCommands.add(request.getUsername());
		
		logger.info("### Starting bootstrapping using " + bootstrapCommands);
		ShellExecuter.execute(config.getChefRepoPath(), bootstrapCommands);
			
		CreateVMResponse resp = CreateVMResponse.newBuilder()
				.setInstanceId(AWSInstanceId.newBuilder().setId(instanceId).build()).setPublicDNS(publicDns)
				.setOutputLog(outputLog).build();

		responseObserver.onNext(resp);
		responseObserver.onCompleted();
		logger.info("### Sent response.");
	}

	@Override
	public void destroyVM(DestroyVMRequest request, StreamObserver<DestroyVMResponse> responseObserver)
	{
		// TODO Auto-generated method stub
		logger.info("### Received request for destroyVM with info:\n " + request.toString());

		// Write the according attributes
		Config config = Config.getInstance(false, false);
		String filename = config.getChefAttributesDefaultFilename("cb-chefmate");
		logger.info("### Writing Attributes file to: " + filename);
		ChefAttributesWriter.writeAttributesFile(filename, request);

		// Call cookbook for destorying
		String execDir = config.getChefRepoPath();
		List<String> destoryCommands = new ArrayList<>();
		destoryCommands.add("chef-client");
		destoryCommands.add("-z");
		destoryCommands.add("-o");
		destoryCommands.add("recipe[cb-chefmate::aws_fogDestroy]");

		logger.info(
				"### Starting destorying task from directory " + execDir + " using commands: " + destoryCommands);
		String outputLog = ShellExecuter.execute(config.getChefRepoPath(), destoryCommands);
		
		DestroyVMResponse resp = DestroyVMResponse.newBuilder().setOutputLog(outputLog).build();

		logger.info("### Destroying Output: \n" + outputLog);

		responseObserver.onNext(resp);
		responseObserver.onCompleted();
		logger.info("### Sent response.");
	}

}
