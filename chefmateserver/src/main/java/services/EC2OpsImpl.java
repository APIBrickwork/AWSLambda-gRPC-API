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
import services.Chefmate.InitCHEFRepoRequest;
import services.Chefmate.InitCHEFRepoResponse;
import util.ChefAttributesWriter;
import util.Config;
import util.SSHExecuter;
import util.SSHExecuter.ChannelType;
import util.ShellExecuter;

public class EC2OpsImpl implements EC2OpsGrpc.EC2Ops
{
	private static final Logger logger = Logger.getLogger(EC2OpsImpl.class.getName());

	@Override
	public void createVM(CreateVMRequest request, StreamObserver<CreateVMResponse> responseObserver)
	{
		logger.info("### Received request for createVM with info:\n " + request.toString());

		// Write the according attributes
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
		String outputLog = ShellExecuter.execute(execDir, provisionCommands);

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

		logger.info(
				"### Starting bootstrapping using from directory " + execDir+ "/.chef" + " using commands: " + bootstrapCommands);
		String bootStrapOutput = ShellExecuter.execute(execDir, bootstrapCommands);
		logger.info("### Bootstrap Output: \n" + bootStrapOutput);

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

		logger.info("### Starting destorying task from directory " + execDir + " using commands: " + destoryCommands);
		String outputLog = ShellExecuter.execute(config.getChefRepoPath(), destoryCommands);

		DestroyVMResponse resp = DestroyVMResponse.newBuilder().setOutputLog(outputLog).build();

		logger.info("### Destroying Output: \n" + outputLog);

		responseObserver.onNext(resp);
		responseObserver.onCompleted();
		logger.info("### Sent response.");
	}

	@Override
	public void initChefRepo(InitCHEFRepoRequest request, StreamObserver<InitCHEFRepoResponse> responseObserver)
	{
		logger.info("### Received request for initChefRepo with info:\n " + request.toString());

		Config config = Config.getInstance(false, false);

		String outputLog = "";
		StringBuilder sb = new StringBuilder();

		String username = request.getCredentials().getUsername();
		String host = request.getCredentials().getHost();

		String homeDir = System.getProperty("user.home");
		String keyfile = homeDir + "/.ssh/" + request.getCredentials().getKeyfilename();
		int timeout = request.getCredentials().getTimeout();
		SSHExecuter ssh = new SSHExecuter();
		ssh.connectHost(username, host, 22, timeout, keyfile);

		String aptGetUpdateCommand = "sudo apt-get -y update";
		logger.info("### Starting apt-repository update using commands: " + aptGetUpdateCommand);
		String aptgetUpdateOutput = ssh.sendToChannel(ChannelType.EXEC, aptGetUpdateCommand, timeout);
		ssh.tearDown();

		logger.info("Apt-get Update Output: \n" + aptgetUpdateOutput);

		String gitInstallCommand = "sudo apt-get -y install git";
		logger.info("### Installing git using commands: " + gitInstallCommand);
		ssh = new SSHExecuter();
		ssh.connectHost(username, host, 22, timeout, keyfile);

		String gitInstallOutput = ssh.sendToChannel(ChannelType.EXEC, gitInstallCommand, timeout);
		logger.info("Install git Output: \n" + gitInstallOutput);
		ssh.tearDown();
		sb.append(gitInstallOutput);

		String pullChefRepoCommand = "mkdir git && cd git && git clone -b development " + config.getChefRepoURL();

		logger.info("### Pulling git repository using commands: " + pullChefRepoCommand);
		ssh = new SSHExecuter();
		ssh.connectHost(username, host, 22, timeout, keyfile);

		String pullChefRepoOutput = ssh.sendToChannel(ChannelType.EXEC, pullChefRepoCommand, timeout);
		logger.info("Cloning git repo Output: \n" + pullChefRepoOutput);
		ssh.tearDown();
		sb.append(pullChefRepoOutput);

		// TODO: Send response etc.
		outputLog = sb.toString();
		System.out.println("Output: \n" + outputLog);

		InitCHEFRepoResponse resp = InitCHEFRepoResponse.newBuilder().setOutputLog(outputLog).build();

		responseObserver.onNext(resp);
		responseObserver.onCompleted();
		logger.info("### Sent response.");
	}
}
