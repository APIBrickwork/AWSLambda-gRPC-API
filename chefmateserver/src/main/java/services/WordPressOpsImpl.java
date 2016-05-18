package services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import io.grpc.stub.StreamObserver;
import services.Chefmate.BackupDBRequest;
import services.Chefmate.BackupDBResponse;
import services.Chefmate.DeployDBRequest;
import services.Chefmate.DeployDBResponse;
import services.Chefmate.DeployWPAppRequest;
import services.Chefmate.DeployWPAppResponse;
import services.Chefmate.RestoreDBRequest;
import services.Chefmate.RestoreDBResponse;
import util.ChefAttributesWriter;
import util.Config;
import util.SSHExecuter;
import util.ShellExecuter;
import util.SSHExecuter.ChannelType;

/**
 * Service implementation for WordPressOps.
 * 
 * @author Tobias Freundorfer
 *
 */
public class WordPressOpsImpl implements WordPressOpsGrpc.WordPressOps
{
	private static final Logger logger = Logger.getLogger(WordPressOpsImpl.class.getName());

	/**
	 * Deploys WordPress on the VM.
	 * 
	 * @param request
	 *            The request containing necessary information.
	 * @responseObserver The observer for the response.
	 */
	@Override
	public void deployWPApp(DeployWPAppRequest request, StreamObserver<DeployWPAppResponse> responseObserver)
	{
		logger.info("### Received request for deployWPApp with info:\n " + request.toString());
		List<String> outputLog = new ArrayList<>();

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
		scpCommands.add("-o");
		scpCommands.add("StrictHostKeyChecking=no");
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
				+ " && sudo chef-solo -c config.rb -o 'recipe[" + cookbookName + "]'";

		logger.info("### Executing chef-solo remotely using commands: " + runChefSoloCommand);
		SSHExecuter ssh = new SSHExecuter();
		ssh.connectHost(username, host, 22, timeout, keyfile);

		outputLog.addAll(ssh.sendToChannel(ChannelType.EXEC, runChefSoloCommand, timeout));
		ssh.tearDown();

		DeployWPAppResponse resp = DeployWPAppResponse.newBuilder().addAllOutputLog(outputLog).build();

		responseObserver.onNext(resp);
		responseObserver.onCompleted();
		logger.info("### Sent Response.");
	}

	/**
	 * Deploys a MySQL database on the VM.
	 * 
	 * @param request
	 *            The request containing necessary information.
	 * @responseObserver The observer for the response.
	 */
	@Override
	public void deployDB(DeployDBRequest request, StreamObserver<DeployDBResponse> responseObserver)
	{
		logger.info("### Received request for deployDB with info:\n " + request.toString());
		List<String> outputLog = new ArrayList<>();

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
		scpCommands.add("-o");
		scpCommands.add("StrictHostKeyChecking=no");
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
				+ " && sudo chef-solo -c config.rb -o 'recipe[" + cookbookName + "]'";

		logger.info("### Executing chef-solo remotely using commands: " + runChefSoloCommand);
		SSHExecuter ssh = new SSHExecuter();
		ssh.connectHost(username, host, 22, timeout, keyfile);

		outputLog.addAll(ssh.sendToChannel(ChannelType.EXEC, runChefSoloCommand, timeout));
		ssh.tearDown();
		ssh = null;

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

		for (int i = 0; i < sqlCommands.size(); i++)
		{
			String command = "mysql -S /run/mysql-" + dbServiceName + "/mysqld.sock -u root -p" + dbRootPw + " -e "
					+ "\"" + sqlCommands.get(i) + "\"";
			String commandLog = "### Executing command:" + command;
			logger.info(commandLog);
			outputLog.add(commandLog);
			outputLog.addAll(sshDb.sendToChannel(ChannelType.EXEC, command, timeout));
		}
		sshDb.tearDown();

		DeployDBResponse resp = DeployDBResponse.newBuilder().addAllOutputLog(outputLog).build();

		responseObserver.onNext(resp);
		responseObserver.onCompleted();
		logger.info("### Sent Response.");

	}

	/**
	 * Creates a Backup of the specified MySQL database on the VM.
	 * 
	 * @param request
	 *            The request containing necessary information.
	 * @responseObserver The observer for the response.
	 */
	@Override
	public void backupDB(BackupDBRequest request, StreamObserver<BackupDBResponse> responseObserver)
	{
		logger.info("### Received request for backupDB with info:\n " + request.toString());
		List<String> outputLog = new ArrayList<>();

		String username = request.getCredentials().getUsername();
		String host = request.getCredentials().getHost();
		int timeout = request.getCredentials().getTimeout();
		String homeDir = System.getProperty("user.home");
		String keyfile = homeDir + "/.ssh/" + request.getCredentials().getKeyfilename();

		SSHExecuter ssh = new SSHExecuter();
		ssh.connectHost(username, host, 22, timeout, keyfile);

		String dbServiceName = request.getServiceName();
		String dbUsername = request.getDbUsername();
		String dbPassword = request.getDbUserPassword();
		String dbName = request.getDbName();
		String backupFilename = request.getBackupFilename();

		String command = "mysqldump --socket=/run/mysql-" + dbServiceName + "/mysqld.sock --opt -u " + dbUsername
				+ " -p" + dbPassword + " " + dbName + " > " + backupFilename + ".sql";
		String commandLog = "### Executing command: " + command;
		logger.info(commandLog);
		outputLog.add(commandLog);
		outputLog.addAll(ssh.sendToChannel(ChannelType.EXEC, command, timeout));

		BackupDBResponse resp = BackupDBResponse.newBuilder().addAllOutputLog(outputLog).build();
		responseObserver.onNext(resp);
		responseObserver.onCompleted();
		logger.info("### Sent response.");
	}

	/**
	 * Restores a previously created backup of a MySQL database on the VM.
	 * 
	 * @param request
	 *            The request containing necessary information.
	 * @responseObserver The observer for the response.
	 */
	@Override
	public void restoreDB(RestoreDBRequest request, StreamObserver<RestoreDBResponse> responseObserver)
	{
		logger.info("### Received request for restoreDB with info:\n " + request.toString());
		List<String> outputLog = new ArrayList<>();

		String username = request.getCredentials().getUsername();
		String host = request.getCredentials().getHost();
		int timeout = request.getCredentials().getTimeout();
		String homeDir = System.getProperty("user.home");
		String keyfile = homeDir + "/.ssh/" + request.getCredentials().getKeyfilename();

		SSHExecuter ssh = new SSHExecuter();
		ssh.connectHost(username, host, 22, timeout, keyfile);

		String dbServiceName = request.getServiceName();
		String dbUsername = request.getDbUsername();
		String dbPassword = request.getDbUserPassword();
		String dbName = request.getDbName();
		String backupFilename = request.getBackupFilename();

		String command = "mysql --socket=/run/mysql-" + dbServiceName + "/mysqld.sock -u " + dbUsername + " -p"
				+ dbPassword + " " + dbName + " < " + backupFilename + ".sql";
		String commandLog = "### Executing command: " + command;
		logger.info(commandLog);
		outputLog.add(commandLog);
		outputLog.addAll(ssh.sendToChannel(ChannelType.EXEC, command, timeout));

		RestoreDBResponse resp = RestoreDBResponse.newBuilder().addAllOutputLog(outputLog).build();
		responseObserver.onNext(resp);
		responseObserver.onCompleted();
		logger.info("### Sent response.");
	}

}
