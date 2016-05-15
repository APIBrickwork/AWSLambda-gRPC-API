package services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import io.grpc.stub.StreamObserver;
import services.Chefmate.DeployDBRequest;
import services.Chefmate.DeployDBResponse;
import services.Chefmate.DeployWPAppRequest;
import services.Chefmate.DeployWPAppResponse;
import util.ChefAttributesWriter;
import util.Config;
import util.SSHExecuter;
import util.ShellExecuter;
import util.SSHExecuter.ChannelType;

public class WordPressOpsImpl implements WordPressOpsGrpc.WordPressOps
{
	private static final Logger logger = Logger.getLogger(WordPressOpsImpl.class.getName());

	@Override
	public void deployWPApp(DeployWPAppRequest request, StreamObserver<DeployWPAppResponse> responseObserver)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void deployDB(DeployDBRequest request, StreamObserver<DeployDBResponse> responseObserver)
	{
		// TODO Auto-generated method stub
		logger.info("### Received request for deployDB with info:\n " + request.toString());
		String outputLog = "";
		StringBuilder sb = new StringBuilder();
		
		String cookbookName = "cb-mysqlServer";
		
		String username = request.getCredentials().getUsername();
		String host = request.getCredentials().getHost();
		int timeout = request.getCredentials().getTimeout();
		
		// Write the according attributes
		Config config = Config.getInstance(false, false);
		String filePath = config.getChefAttributesDefaultFilename(cookbookName);
		logger.info("### Writing Attributes file to: " + filePath);
		ChefAttributesWriter.writeAttributesFile(filePath, request);
		
		// TODO: Send atrributes file via scp (Doesnt work yet)
		
		String execDir = config.getChefAttributesPath(cookbookName);
		List<String> scpCommands = new ArrayList<>();
		scpCommands.add("scp");
		scpCommands.add("default.rb");
		String remoteDestinationPath = execDir.replace(config.getServerEnvDir(), "/home/" + username + "/");
		String remoteCall = username + "@" + host + ":" + remoteDestinationPath;
		scpCommands.add( remoteCall);


		logger.info("### Sending default.rb from " + execDir + " using commands: " + scpCommands);

		String scpOutput = ShellExecuter.execute(execDir, scpCommands);
		sb.append(scpOutput);
		logger.info("SCP Output:\n" + scpOutput);		
		
		// run chef-solo on node
		String runChefSoloCommand = "cd ~/git/" + config.getChefRepoName() + " && sudo chef-solo -c config.rb -o 'recipe[cb-mysqlServer]'";

		String homeDir = System.getProperty("user.home");
		String keyfile = homeDir + "/.ssh/" + request.getCredentials().getKeyfilename();
		logger.info("### Executing chef-solo remotely using commands: " + runChefSoloCommand);
		SSHExecuter ssh = new SSHExecuter();
		ssh.connectHost(username, host, 22, timeout, keyfile);

		String chefSoloOutput = ssh.sendToChannel(ChannelType.EXEC, runChefSoloCommand, timeout);
		logger.info("Chef-Solo Output: \n" + chefSoloOutput);
		ssh.tearDown();
		sb.append(chefSoloOutput);
		
		// TODO: Return response
	}

}
