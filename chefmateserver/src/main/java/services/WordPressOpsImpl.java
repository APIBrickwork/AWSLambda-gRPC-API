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
		logger.info("### Received request for deployWPApp with info:\n " + request.toString());

		// TODO: All the outputs maybe should be delivered to the client using a
		// stream of strings!!

		String outputLog = "";
		StringBuilder sb = new StringBuilder();

		String cookbookName = "cb-wordpress";
		String username = request.getCredentials().getUsername();
		String host = request.getCredentials().getHost();
		int timeout = request.getCredentials().getTimeout();
		String homeDir = System.getProperty("user.home");
		String keyfile = homeDir + "/.ssh/" + request.getCredentials().getKeyfilename();

		// Write the according attributes
		Config config = Config.getInstance(false, false);
		String filePath = config.getChefAttributesDefaultFilename(cookbookName);
		logger.info("### Writing Attributes file to: " + filePath);
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

		String scpOutput = ShellExecuter.execute(execDir, scpCommands);
		sb.append(scpOutput);

		// run chef-solo on node
		String runChefSoloCommand = "cd ~/git/" + config.getChefRepoName()
				+ " && sudo chef-solo -c config.rb -o 'recipe[cb-wordpress]'";

		logger.info("### Executing chef-solo remotely using commands: " + runChefSoloCommand);
		SSHExecuter ssh = new SSHExecuter();
		ssh.connectHost(username, host, 22, timeout, keyfile);

		String chefSoloOutput = ssh.sendToChannel(ChannelType.EXEC, runChefSoloCommand, timeout);
		logger.info("### Chef-Solo Output: \n" + chefSoloOutput);
		ssh.tearDown();
		sb.append(chefSoloOutput);

		outputLog = sb.toString();
		DeployWPAppResponse resp = DeployWPAppResponse.newBuilder().setOutputLog(outputLog).build();

		responseObserver.onNext(resp);
		responseObserver.onCompleted();
		logger.info("### Sent Response.");
	}

	@Override
	public void deployDB(DeployDBRequest request, StreamObserver<DeployDBResponse> responseObserver)
	{
		logger.info("### Received request for deployDB with info:\n " + request.toString());
		String outputLog = "";
		StringBuilder sb = new StringBuilder();

		String cookbookName = "cb-mysqlServer";

		String username = request.getCredentials().getUsername();
		String host = request.getCredentials().getHost();
		int timeout = request.getCredentials().getTimeout();
		String homeDir = System.getProperty("user.home");
		String keyfile = homeDir + "/.ssh/" + request.getCredentials().getKeyfilename();

		// Write the according attributes
		Config config = Config.getInstance(false, false);
		String filePath = config.getChefAttributesDefaultFilename(cookbookName);
		logger.info("### Writing Attributes file to: " + filePath);
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

		String scpOutput = ShellExecuter.execute(execDir, scpCommands);
		sb.append(scpOutput);

		// run chef-solo on node
		String runChefSoloCommand = "cd ~/git/" + config.getChefRepoName()
				+ " && sudo chef-solo -c config.rb -o 'recipe[cb-mysqlServer]'";

		logger.info("### Executing chef-solo remotely using commands: " + runChefSoloCommand);
		SSHExecuter ssh = new SSHExecuter();
		ssh.connectHost(username, host, 22, timeout, keyfile);

		String chefSoloOutput = ssh.sendToChannel(ChannelType.EXEC, runChefSoloCommand, timeout);
		logger.info("### Chef-Solo Output: \n" + chefSoloOutput);
		ssh.tearDown();
		ssh = null;
		sb.append(chefSoloOutput);

		/**
		 * Create DB user and setup database and privileges
		 */
		

		String dbServiceName = request.getServiceName(); 
		String dbRootPw = request.getRootPassword();
		String dbUsername = request.getUsername();
		String dbUserpw = request.getUserPassword();
		String dbName = request.getDbName();
		

		List<String> sqlCommands = new ArrayList<>();
		String sqlCreateUserCmd = "CREATE USER '" + dbUsername + "'@'%' IDENTIFIED BY '" + dbUserpw + "';";
		sqlCommands.add(sqlCreateUserCmd);
		String sqlCreateDbCmd = "CREATE DATABASE " + dbName + ";";
		sqlCommands.add(sqlCreateDbCmd);
		String sqlGrantPrivileges = "GRANT ALL ON *.* TO " + dbUsername + "@'%' IDENTIFIED BY '" + dbUserpw + "';";
		sqlCommands.add(sqlGrantPrivileges);
		String sqlFlushPrivileges = "FLUSH PRIVILEGES;";
		sqlCommands.add(sqlFlushPrivileges);
		
		
		SSHExecuter sshDb = new SSHExecuter();
		sshDb.connectHost(username, host, 22, timeout, keyfile);
//		ssh = new SSHExecuter();
//		ssh.connectHost(username, host, 22, timeout, keyfile);
		
		for(int i=0;i<sqlCommands.size();i++){
			String command = "mysql -S /run/mysql-" + dbServiceName + "/mysqld.sock -u root -p" + dbRootPw + " -e "
					+ "\"" + sqlCommands.get(i) + "\"";
			logger.info("### Executing command:'" + command);
			sshDb.sendToChannel(ChannelType.EXEC, command, timeout);			
		}
		sshDb.tearDown();
		
		outputLog = sb.toString();
		DeployDBResponse resp = DeployDBResponse.newBuilder().setOutputLog(outputLog).build();

		responseObserver.onNext(resp);
		responseObserver.onCompleted();
		logger.info("### Sent Response.");

	}

}
