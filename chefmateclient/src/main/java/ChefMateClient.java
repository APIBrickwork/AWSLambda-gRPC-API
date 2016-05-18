import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import services.Chefmate;
import services.Chefmate.AWSInstanceId;
import services.Chefmate.BackupDBRequest;
import services.Chefmate.BackupDBResponse;
import services.Chefmate.CreateVMRequest;
import services.Chefmate.CreateVMResponse;
import services.Chefmate.DeployDBRequest;
import services.Chefmate.DeployDBResponse;
import services.Chefmate.DeployWPAppRequest;
import services.Chefmate.DeployWPAppResponse;
import services.Chefmate.DestroyVMRequest;
import services.Chefmate.DestroyVMResponse;
import services.Chefmate.ExecuteCookbookRequest;
import services.Chefmate.ExecuteCookbookResponse;
import services.Chefmate.InitCHEFRepoRequest;
import services.Chefmate.InitCHEFRepoResponse;
import services.Chefmate.RestoreDBRequest;
import services.Chefmate.RestoreDBResponse;
import services.Chefmate.SSHCredentials;
import services.EC2OpsGrpc;
import services.EC2OpsGrpc.EC2OpsBlockingStub;
import services.GenericOpsGrpc;
import services.GenericOpsGrpc.GenericOpsBlockingStub;
import services.WordPressOpsGrpc;
import services.WordPressOpsGrpc.WordPressOpsBlockingStub;

/**
 * The ChefMateClient offering connection to the ChefMateServer and its
 * services.
 * 
 * @author Tobias Freundorfer
 *
 */
public class ChefMateClient
{

	private static final Logger logger = Logger.getLogger(ChefMateClient.class.getName());

	/**
	 * Channel for the connection to the server.
	 */
	private final ManagedChannel channel;

	/**
	 * The blocking Stub (response is synchronous).
	 */
	private final EC2OpsBlockingStub blockingStub;
	private final WordPressOpsBlockingStub wpBlockingStub;
	private final GenericOpsBlockingStub genBlockingStub;

	/**
	 * Creates a new instance of the ChefMateClient connected to the
	 * ChefMateServer.
	 * 
	 * @param host
	 *            The hostname of the ChefMateServer.
	 * @param port
	 *            The port of the ChefMateServer.
	 */
	public ChefMateClient(String host, int port)
	{
		this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
		this.blockingStub = EC2OpsGrpc.newBlockingStub(this.channel);
		this.wpBlockingStub = WordPressOpsGrpc.newBlockingStub(this.channel);
		this.genBlockingStub = GenericOpsGrpc.newBlockingStub(this.channel);
	}

	/**
	 * Sends the createVM request to the ChefMateServer.
	 */

	public void sendCreateVMRequest(CreateVMRequest createVMRequest)
	{
		logger.info("### Sending request for Creating VM.");

		CreateVMResponse createVMResponse = null;
		try
		{
			createVMResponse = this.blockingStub.createVM(createVMRequest);
		} catch (StatusRuntimeException e)
		{
			logger.warning("### RPC failed: {0}" + e.getStatus());
			return;
		}
		logger.info("### Received response.");
		System.out.println("\n Created new VM with OutputLog = \n");
		for (int i = 0; i < createVMResponse.getOutputLogList().size(); i++)
		{
			System.out.println(createVMResponse.getOutputLog(i));
		}

		System.out.println("Created new VM with InstanceID = " + createVMResponse.getInstanceId());
		System.out.println("Created new VM with Public DNS = " + createVMResponse.getPublicDNS());
	}

	/**
	 * Sends the destroyVM request to the ChefMateServer.
	 */

	public void sendDestroyVMRequest(DestroyVMRequest destroyVMRequest)
	{
		logger.info("### Sending request for Destroying VM.");

		DestroyVMResponse destroyVMResponse = null;
		try
		{
			destroyVMResponse = this.blockingStub.destroyVM(destroyVMRequest);
		} catch (StatusRuntimeException e)
		{
			logger.warning("### RPC failed: {0}" + e.getStatus());
			return;
		}
		logger.info("### Received response.");
		System.out.println("\n Destroyed VM with OutputLog = \n");
		for (int i = 0; i < destroyVMResponse.getOutputLogList().size(); i++)
		{
			System.out.println(destroyVMResponse.getOutputLog(i));
		}

		System.out.println("Destoyed VM with InstanceId =" + destroyVMRequest.getInstanceId());
	}

	/**
	 * Sends the initChefRepo request to the ChefMateServer.
	 */

	public void sendInitChefRepoRequest(InitCHEFRepoRequest initCHEFRepoRequest)
	{
		logger.info("### Sending request for InitChefRepo.");

		InitCHEFRepoResponse initCHEFRepoResponse = null;
		try
		{
			initCHEFRepoResponse = this.blockingStub.initChefRepo(initCHEFRepoRequest);
		} catch (StatusRuntimeException e)
		{
			logger.warning("### RPC failed: {0}" + e.getStatus());
			return;
		}
		logger.info("### Received response.");
		System.out.println("Initialized Chef-Repo with OutputLog =\n");
		for (int i = 0; i < initCHEFRepoResponse.getOutputLogList().size(); i++)
		{
			System.out.println(initCHEFRepoResponse.getOutputLog(i));
		}
	}

	/**
	 * Sends the deployWPApp request to the ChefMateServer.
	 */

	public void sendDeployWPAppRequest(DeployWPAppRequest deployWPAppRequest)
	{
		logger.info("### Sending request for Deploying WordPress.");

		DeployWPAppResponse deployWPAppResponse = null;
		try
		{
			deployWPAppResponse = this.wpBlockingStub.deployWPApp(deployWPAppRequest);
		} catch (StatusRuntimeException e)
		{
			logger.warning("### RPC failed: {0}" + e.getStatus());
			return;
		}
		logger.info("### Received response.");
		System.out.println("\n Deployed Word Press with OutputLog =\n");
		for (int i = 0; i < deployWPAppResponse.getOutputLogList().size(); i++)
		{
			System.out.println(deployWPAppResponse.getOutputLog(i));
		}
	}

	/**
	 * Sends the deployDB request to the ChefMateServer.
	 */

	public void sendDeployDBRequest(DeployDBRequest deployDBRequest)
	{
		logger.info("### Sending request for Deploying Database.");

		DeployDBResponse deployDBResponse = null;
		try
		{
			deployDBResponse = this.wpBlockingStub.deployDB(deployDBRequest);
		} catch (StatusRuntimeException e)
		{
			logger.warning("### RPC failed: {0}" + e.getStatus());
			return;
		}
		logger.info("### Received response.");
		System.out.println("\n Deployed Word Press with OutputLog =\n");
		for (int i = 0; i < deployDBResponse.getOutputLogList().size(); i++)
		{
			System.out.println(deployDBResponse.getOutputLog(i));
		}
	}

	/**
	 * Sends the backupDB request to the ChefMateServer.
	 */

	public void sendBackupDBRequest(BackupDBRequest backupDBRequest)
	{
		logger.info("### Sending request for BackupDB.");

		BackupDBResponse backupDBResponse = null;
		try
		{
			backupDBResponse = this.wpBlockingStub.backupDB(backupDBRequest);
		} catch (StatusRuntimeException e)
		{
			logger.warning("### RPC failed: {0}" + e.getStatus());
			return;
		}
		logger.info("### Received response.");
		System.out.println("\n Database Backup with OutputLog =\n");
		for (int i = 0; i < backupDBResponse.getOutputLogList().size(); i++)
		{
			System.out.println(backupDBResponse.getOutputLog(i));
		}
	}

	/**
	 * Sends the restoreDB request to the ChefMateServer.
	 */

	public void sendRestoreDBRequest(RestoreDBRequest restoreDBRequest)
	{
		logger.info("### Sending request for RestoreDB.");

		RestoreDBResponse restoreDBResponse = null;
		try
		{
			restoreDBResponse = this.wpBlockingStub.restoreDB(restoreDBRequest);
		} catch (StatusRuntimeException e)
		{
			logger.warning("### RPC failed: {0}" + e.getStatus());
			return;
		}
		logger.info("### Received response.");
		System.out.println("\n Database Restore with OutputLog =\n");
		for (int i = 0; i < restoreDBResponse.getOutputLogList().size(); i++)
		{
			System.out.println(restoreDBResponse.getOutputLog(i));
		}
	}

	/**
	 * Sends the executeCookbook request to the ChefMateServer.
	 */

	public void sendExecuteCookbookRequest(ExecuteCookbookRequest execCookbookRequest)
	{
		logger.info("### Sending request for executeCookbook.");

		ExecuteCookbookResponse execCookbookResponse = null;
		try
		{
			execCookbookResponse = this.genBlockingStub.executeCookbook(execCookbookRequest);
		} catch (StatusRuntimeException e)
		{
			logger.warning("### RPC failed: {0}" + e.getStatus());
			return;
		}
		logger.info("### Received response.");
		System.out.println("\n Exectued Cookbook with OutputLog =\n");
		for (int i = 0; i < execCookbookResponse.getOutputLogList().size(); i++)
		{
			System.out.println(execCookbookResponse.getOutputLog(i));
		}
	}

	/**
	 * Initiates the shutdown sequence.
	 * 
	 * @throws InterruptedException
	 *             Thrown if shutdown was interrupted.
	 */
	public void shutdown() throws InterruptedException
	{
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	public static void main(String[] args)
	{
		// Read input args
		String hostname = "";
		int port = -1;
		for (int i = 0; i < args.length; i++)
		{
			if (args[i].equals("-h"))
			{
				// Check if there's a following command
				if ((i + 1) < args.length)
				{
					hostname = args[i + 1];
					i++;
				} else
				{
					ChefMateClient.showArgsPrompt();
					System.exit(0);
				}
			}
			if (args[i].equals("-p"))
			{
				if ((i + 1) < args.length)
				{
					try
					{
						port = Integer.parseInt(args[i + 1]);
						i++;
					} catch (NumberFormatException e)
					{
						ChefMateClient.showArgsPrompt();
						System.exit(0);
					}
				} else
				{
					ChefMateClient.showArgsPrompt();
					System.exit(0);
				}
			}
		}
		if (hostname.isEmpty() || port == -1)
		{
			ChefMateClient.showArgsPrompt();
			System.exit(0);
		}

		System.out.println("Connecting to " + hostname + ":" + port);
		ChefMateClient client = new ChefMateClient(hostname, port);

		// Loop for user input commands
		Scanner scanner = new Scanner(System.in);
		boolean receiveUserCommands = true;
		while (receiveUserCommands)
		{
			ChefMateClient.showUserCommandPrompt();
			System.out.println("\n Enter command: \n");
			String command = scanner.nextLine();

			if (command.equals("createVM"))
			{
				System.out.println("\n Enter Instance Name: ");
				String name = scanner.nextLine();
				System.out.println("\n Enter Instance Tag: ");
				String tag = scanner.nextLine();
				System.out.println("\n Enter Instance Region: ");
				String region = scanner.nextLine();
				System.out.println("\n Enter Instance ImageId: ");
				String imageId = scanner.nextLine();
				System.out.println("\n Enter User Name : ");
				String userName = scanner.nextLine();
				System.out.println("\n Enter Instance Type : ");
				String instanceType = scanner.nextLine();
				System.out.println("\n Enter Security GroupIds: ");
				String securityGroupIds = scanner.nextLine();

				Chefmate.CreateVMRequest createVMRequest = Chefmate.CreateVMRequest.newBuilder().setName(name)
						.setTag(tag).setRegion(region).setImageId(imageId).setUsername(userName)
						.setInstanceType(instanceType).addSecurityGroupIds(securityGroupIds).build();

				client.sendCreateVMRequest(createVMRequest);

			} else if (command.equals("shutdown"))
			{
				try
				{
					client.shutdown();
					receiveUserCommands = false;
				} catch (InterruptedException e)
				{
					logger.warning("Client was interrupted at shutdown.");
					receiveUserCommands = false;
				}
			} else if (command.startsWith("destroyVM"))
			{
				String[] arr = command.split(" ");
				if (arr.length != 2)
				{
					logger.warning("### Wrong syntax.");
				} else
				{

					DestroyVMRequest destroyVMRequest = DestroyVMRequest.newBuilder()
							.setInstanceId(AWSInstanceId.newBuilder().setId(arr[1])).build();
					client.sendDestroyVMRequest(destroyVMRequest);
				}
			} else if (command.startsWith("initChefRepo"))
			{

				System.out.println("\n Enter User Name : ");
				String username = scanner.nextLine();
				System.out.println("\n Enter host: ");
				String host = scanner.nextLine();
				System.out.println("\n Enter keyfile Name: ");
				String keyfilename = scanner.nextLine();
				System.out.println("\n Enter timeout: ");
				int timeout = Integer.parseInt(scanner.nextLine());

				SSHCredentials credentials = SSHCredentials.newBuilder().setUsername(username).setHost(host)
						.setKeyfilename(keyfilename).setTimeout(timeout).build();

				InitCHEFRepoRequest initCHEFRepoRequest = InitCHEFRepoRequest.newBuilder().setCredentials(credentials)
						.build();

				client.sendInitChefRepoRequest(initCHEFRepoRequest);

			} else if (command.startsWith("deployWPApp"))
			{

				System.out.println("\n Enter User Name : ");
				String username = scanner.nextLine();
				System.out.println("\n Enter host: ");
				String host = scanner.nextLine();
				System.out.println("\n Enter keyfile Name: ");
				String keyfilename = scanner.nextLine();
				System.out.println("\n Enter timeout: ");
				int timeout = Integer.parseInt(scanner.nextLine());

				SSHCredentials credentials = SSHCredentials.newBuilder().setUsername(username).setHost(host)
						.setKeyfilename(keyfilename).setTimeout(timeout).build();

				System.out.println("\n Enter Server Name : ");
				String serverName = scanner.nextLine();
				System.out.println("\n Enter Port: ");
				String serverPort = scanner.nextLine();
				System.out.println("\n Enter Database Name: ");
				String dbName = scanner.nextLine();
				System.out.println("\n Enter Database Host : ");
				String dbHost = scanner.nextLine();
				System.out.println("\n Enter Database Port: ");
				String dbPort = scanner.nextLine();
				System.out.println("\n Enter DB User Name: ");
				String dbUserName = scanner.nextLine();
				System.out.println("\n Enter DB User Password : ");
				String dbUserPassword = scanner.nextLine();
				System.out.println("\n Enter DB Root Password: ");
				String dbRootPassword = scanner.nextLine();
				System.out.println("\n Enter WordePress Config Options: ");
				String wpConfigOptions = scanner.nextLine();

				DeployWPAppRequest deployWPAppRequest = DeployWPAppRequest.newBuilder().setCredentials(credentials)
						.setServerName(serverName).setPort(serverPort).setDbName(dbName).setDbHost(dbHost)
						.setDbPort(dbPort).setDbUserName(dbUserName).setDbUserPassword(dbUserPassword)
						.setDbRootPassword(dbRootPassword).setWpConfigOptions(wpConfigOptions).build();

				client.sendDeployWPAppRequest(deployWPAppRequest);

			} else if (command.startsWith("deployDB"))
			{

				System.out.println("\n Enter User Name : ");
				String username = scanner.nextLine();
				System.out.println("\n Enter host: ");
				String host = scanner.nextLine();
				System.out.println("\n Enter keyfile Name: ");
				String keyfilename = scanner.nextLine();
				System.out.println("\n Enter timeout: ");
				int timeout = Integer.parseInt(scanner.nextLine());

				SSHCredentials credentials = SSHCredentials.newBuilder().setUsername(username).setHost(host)
						.setKeyfilename(keyfilename).setTimeout(timeout).build();

				System.out.println("\n Enter MySql Service Name : ");
				String serviceName = scanner.nextLine();
				System.out.println("\n Enter Port: ");
				int mysqlPort = Integer.parseInt(scanner.nextLine());
				System.out.println("\n Enter MySql User Name : ");
				String mysqlUsername = scanner.nextLine();
				System.out.println("\n Enter MySql User Password : ");
				String userPassword = scanner.nextLine();
				System.out.println("\n Enter Mysql Database Name : ");
				String dbName = scanner.nextLine();
				System.out.println("\n Enter Root Password: ");
				String rootPassword = scanner.nextLine();

				DeployDBRequest deployDBRequest = DeployDBRequest.newBuilder().setCredentials(credentials)
						.setServiceName(serviceName).setPort(mysqlPort).setUsername(mysqlUsername)
						.setUserPassword(userPassword).setDbName(dbName).setRootPassword(rootPassword).build();
				client.sendDeployDBRequest(deployDBRequest);

			} else if (command.startsWith("backupDB"))
			{

				System.out.println("\n Enter User Name : ");
				String username = scanner.nextLine();
				System.out.println("\n Enter host: ");
				String host = scanner.nextLine();
				System.out.println("\n Enter keyfile Name: ");
				String keyfilename = scanner.nextLine();
				System.out.println("\n Enter timeout: ");
				int timeout = Integer.parseInt(scanner.nextLine());

				SSHCredentials credentials = SSHCredentials.newBuilder().setUsername(username).setHost(host)
						.setKeyfilename(keyfilename).setTimeout(timeout).build();

				System.out.println("\n Enter MySql Service Name : ");
				String serviceName = scanner.nextLine();
				System.out.println("\n Enter Database User Name : ");
				String dbUsername = scanner.nextLine();
				System.out.println("\n Enter Database User Password : ");
				String dbUserPassword = scanner.nextLine();
				System.out.println("\n Enter Database Name : ");
				String dbName = scanner.nextLine();
				System.out.println("\n Enter Backup File Name: ");
				String backupFilename = scanner.nextLine();

				BackupDBRequest backupDBRequest = BackupDBRequest.newBuilder().setCredentials(credentials)
						.setServiceName(serviceName).setDbUsername(dbUsername).setDbUserPassword(dbUserPassword)
						.setDbName(dbName).setBackupFilename(backupFilename).build();
				client.sendBackupDBRequest(backupDBRequest);

			} else if (command.startsWith("restoreDB"))
			{

				System.out.println("\n Enter User Name : ");
				String username = scanner.nextLine();
				System.out.println("\n Enter host: ");
				String host = scanner.nextLine();
				System.out.println("\n Enter keyfile Name: ");
				String keyfilename = scanner.nextLine();
				System.out.println("\n Enter timeout: ");
				int timeout = Integer.parseInt(scanner.nextLine());

				SSHCredentials credentials = SSHCredentials.newBuilder().setUsername(username).setHost(host)
						.setKeyfilename(keyfilename).setTimeout(timeout).build();

				System.out.println("\n Enter MySql Service Name : ");
				String serviceName = scanner.nextLine();
				System.out.println("\n Enter Database User Name : ");
				String dbUsername = scanner.nextLine();
				System.out.println("\n Enter Database User Password : ");
				String dbUserPassword = scanner.nextLine();
				System.out.println("\n Enter Database Name : ");
				String dbName = scanner.nextLine();
				System.out.println("\n Enter Backup File Name: ");
				String backupFilename = scanner.nextLine();

				RestoreDBRequest restoreDBRequest = RestoreDBRequest.newBuilder().setCredentials(credentials)
						.setServiceName(serviceName).setDbUsername(dbUsername).setDbUserPassword(dbUserPassword)
						.setDbName(dbName).setBackupFilename(backupFilename).build();
				client.sendRestoreDBRequest(restoreDBRequest);

			} else if (command.startsWith("executeCookbook"))
			{
				System.out.println("\n Enter User Name : ");
				String username = scanner.nextLine();
				System.out.println("\n Enter host: ");
				String host = scanner.nextLine();
				System.out.println("\n Enter keyfile Name: ");
				String keyfilename = scanner.nextLine();
				System.out.println("\n Enter timeout: ");
				int timeout = Integer.parseInt(scanner.nextLine());

				SSHCredentials credentials = SSHCredentials.newBuilder().setUsername(username).setHost(host)
						.setKeyfilename(keyfilename).setTimeout(timeout).build();

				System.out.println("\n Enter Cookbook Name: ");
				String cookbookName = scanner.nextLine();
				System.out.println("\n Enter Recipe Name: (default.rb if empty)");
				String recipeName = scanner.nextLine();
				System.out.println("\n Enter the amount of attribute configurations\n you want to use:");

				int attributesQty = Integer.parseInt(scanner.nextLine());
				List<String> keys = new ArrayList<>();
				List<String> values = new ArrayList<>();

				for (int i = 0; i < attributesQty; i++)
				{
					System.out.println("\n Enter Attribute Key:");
					String key = scanner.nextLine();
					System.out.println("\n Enter Attribute Value:");
					String value = scanner.nextLine();
					keys.add(key);
					values.add(value);
				}

				ExecuteCookbookRequest execCookbookRequest = ExecuteCookbookRequest.newBuilder()
						.setCredentials(credentials).setCookbookName(cookbookName).setRecipeName(recipeName).build();
				client.sendExecuteCookbookRequest(execCookbookRequest);
			}
		}
		scanner.close();
	}

	/**
	 * Shows the command prompt for user commands.
	 */
	private static void showUserCommandPrompt()
	{
		System.out.println("Available Commands: \n");
		System.out.println("########################");
		System.out.println("##### VM Requests #####");
		System.out.println("########################");
		System.out.println("createVM \n\t Create VM Instance ");
		System.out.println("initChefRepo \n\t Initialize Chef Repo");
		System.out.println("destroyVM <Instance-ID>\n\t Destroy the VM Instance");
		System.out.println("########################");
		System.out.println("### WordPress/MySQL ###");
		System.out.println("########################");
		System.out.println("deployWPApp \n\t Deploy Word Press ");
		System.out.println("deployDB \n\t Deploy Database");
		System.out.println("backupDB \n\t Database Backup ");
		System.out.println("restoreDB \n\t Database Restore ");
		System.out.println("###############");
		System.out.println("### Generic ###");
		System.out.println("###############");
		System.out.println("executeCookbook \n\t Executes the given cookbook");
		System.out.println("########################");
		System.out.println("######## System ########");
		System.out.println("########################");
		System.out.println("shutdown \n\t Terminates this client and closes all connections.");
	}

	/**
	 * Shows the args prompt for startup arguments.
	 */
	private static void showArgsPrompt()
	{
		System.out.println("Usage: \n <appname> command argument");
		System.out.println("-h \t The host address to connect to. \n -p \t The port to connect to.");
	}

}
