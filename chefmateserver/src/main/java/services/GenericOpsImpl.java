package services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import io.grpc.stub.StreamObserver;
import services.Chefmate.ExecuteCookbookRequest;
import services.Chefmate.ExecuteCookbookResponse;
import services.GenericOpsGrpc.GenericOps;
import util.ChefAttributesWriter;
import util.Config;
import util.SSHExecuter;
import util.ShellExecuter;
import util.SSHExecuter.ChannelType;

/**
 * Service implementation for GenericOps.
 * 
 * @author Tobias Freundorfer
 *
 */
public class GenericOpsImpl implements GenericOps
{
	private static final Logger logger = Logger.getLogger(WordPressOpsImpl.class.getName());

	/**
	 * Executes the given cookbook on the VM.
	 * 
	 * @param request
	 *            The request containing necessary information.
	 * @responseObserver The observer for the response.
	 */
	@Override
	public void executeCookbook(ExecuteCookbookRequest request,
			StreamObserver<ExecuteCookbookResponse> responseObserver)
	{
		logger.info("### Received request for executeCookbook with info:\n " + request.toString());
		List<String> outputLog = new ArrayList<>();

		String cookbookName = request.getCookbookName();
		String recipeName = "default.rb";
		if (!request.getRecipeName().isEmpty())
		{
			recipeName = request.getRecipeName();
		}

		String username = request.getCredentials().getUsername();
		String host = request.getCredentials().getHost();
		int timeout = request.getCredentials().getTimeout();
		String homeDir = System.getProperty("user.home");
		String keyfile = homeDir + "/.ssh/" + request.getCredentials().getKeyfilename();

		// Write the according attributes

		Config config = Config.getInstance(false, false);
		String filePath = config.getChefAttributesDefaultFilename(cookbookName);
		logger.info("### Writing Attributes file to: " + filePath);

		if (request.getAttributesKeysCount() > 0 && request.getAttributesValuesCount() > 0
				&& (request.getAttributesKeysCount() == request.getAttributesValuesCount()))
		{
			ChefAttributesWriter.writeAttributesFile(filePath, request);

			String execDir = config.getChefAttributesPath(cookbookName);
			List<String> scpCommands = new ArrayList<>();
			scpCommands.add("scp");
			scpCommands.add("-i");
			scpCommands.add(keyfile);
			scpCommands.add(config.getChefAttributesPath(cookbookName) + "/default.rb");
			String remoteDestinationPath = execDir.replace(config.getServerEnvDir(), "/home/" + username + "/git/");
			String remoteCall = username + "@" + host + ":" + remoteDestinationPath;
			scpCommands.add(remoteCall);

			logger.info("### Sending default.rb from " + execDir + " using commands: " + scpCommands);

			outputLog.addAll(ShellExecuter.execute(execDir, scpCommands));

			// run chef-solo on node
			String runChefSoloCommand = "cd ~/git/" + config.getChefRepoName()
					+ " && sudo chef-solo -c config.rb -o 'recipe[" + cookbookName + ":" + recipeName + "]'";

			logger.info("### Executing chef-solo remotely using commands: " + runChefSoloCommand);
			SSHExecuter ssh = new SSHExecuter();
			ssh.connectHost(username, host, 22, timeout, keyfile);

			outputLog.addAll(ssh.sendToChannel(ChannelType.EXEC, runChefSoloCommand, timeout));
			ssh.tearDown();
		} else
		{
			outputLog.add("### Error with keys and values for attributes. Keys-Size = "
					+ request.getAttributesKeysCount() + " | Values Size = " + request.getAttributesValuesCount());
		}
		ExecuteCookbookResponse resp = ExecuteCookbookResponse.newBuilder().addAllOutputLog(outputLog).build();

		responseObserver.onNext(resp);
		responseObserver.onCompleted();
		logger.info("### Sent response.");
	}

}
