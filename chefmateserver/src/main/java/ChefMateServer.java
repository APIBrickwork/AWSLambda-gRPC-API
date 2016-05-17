import java.io.IOException;
import java.util.logging.Logger;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import services.Chefmate.BackupDBRequest;
import services.Chefmate.CreateVMRequest;
import services.Chefmate.DeployDBRequest;
import services.Chefmate.DeployWPAppRequest;
import services.Chefmate.InitCHEFRepoRequest;
import services.Chefmate.RestoreDBRequest;
import services.Chefmate.SSHCredentials;
import services.EC2OpsGrpc;
import services.EC2OpsImpl;
import services.WordPressOpsImpl;
import util.Config;
import util.EnvironmentInitializer;
import util.SSHExecuter;
import util.SSHExecuter.ChannelType;

import com.jcraft.jsch.*;

public class ChefMateServer
{

	private static final Logger logger = Logger.getLogger(ChefMateServer.class.getName());

	/**
	 * The port on which the server should run.
	 */
	private int port = -1;

	/**
	 * The gRPC server.
	 */
	private Server server;

	/**
	 * Starts the server.
	 * 
	 * @param port
	 *            The port to listen to.
	 * @throws IOException
	 */
	private void start(int port) throws IOException
	{
		this.port = port;
		this.server = ServerBuilder.forPort(this.port).addService(EC2OpsGrpc.bindService(new EC2OpsImpl())).build()
				.start();

		logger.info("### Server started listening on port " + this.port);
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				// Use stderr here since the logger may has been reset by its
				// JVM shutdown hook.
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				ChefMateServer.this.stop();
				System.err.println("*** server shut down");
			}
		});
	}

	/**
	 * Stops the server.
	 */
	private void stop()
	{
		if (this.server != null)
		{
			this.server.shutdown();
		}
	}

	/**
	 * Await termination on the main thread since the grpc library uses daemon
	 * threads.
	 */
	private void blockUntilShutdown() throws InterruptedException
	{
		if (server != null)
		{
			server.awaitTermination();
		}
	}

	public static void main(String[] args)
	{
		// TODO: Maybe also necessary to add Chef Installation when
		// initializing!!

		/* Local Test Code */

		// TODO: Delete demo code after testing
//		Config.getInstance(false, true);

		/**
		 * SSH credentials
		 */
//		SSHCredentials credentials = SSHCredentials.newBuilder().setUsername("ubuntu")
//				.setHost("ec2-52-28-99-214.eu-central-1.compute.amazonaws.com").setKeyfilename("chefmateserver_key.pem")
//				.setTimeout(10000).build();

		/**
		 * VM Services Tests
		 */
		// CreateVMRequest req =
		// CreateVMRequest.newBuilder().setName("mysql").setTag("mytag").setRegion("eu-central-1")
		// .setImageId("ami-87564feb").setUsername("ubuntu").setInstanceType("t2.micro")
		// .addSecurityGroupIds("sg-79ae5d11").build();
		// new EC2OpsImpl().createVM(req, null);
		//
		// DestroyVMRequest req = DestroyVMRequest.newBuilder()
		// .setInstanceId(AWSInstanceId.newBuilder().setId("i-06d20782fa2113e32").build()).build();
		// new EC2OpsImpl().destroyVM(req, null);

		// InitCHEFRepoRequest req =
		// InitCHEFRepoRequest.newBuilder().setCredentials(credentials).build();
		// new EC2OpsImpl().initChefRepo(req, null);

		/**
		 * deployDB Tests
		 */
		// DeployDBRequest req =
		// DeployDBRequest.newBuilder().setCredentials(credentials).setServiceName("server")
		// .setPort(3306).setUsername("wordpress").setUserPassword("cloud16").setDbName("wordpressdb")
		// .setRootPassword("cloud16").build();
		//
		// new WordPressOpsImpl().deployDB(req, null);

		/**
		 * Backup DB Tests
		 */

//		BackupDBRequest req = services.Chefmate.BackupDBRequest.newBuilder().setCredentials(credentials)
//				.setServiceName("server").setDbUsername("wordpress").setDbUserPassword("cloud16")
//				.setDbName("wordpressdb").setBackupFilename("wordpressdbbackup").build();
//		new WordPressOpsImpl().backupDB(req, null);
		
		/**
		 * Restore DB Tests
		 */
//		RestoreDBRequest req = services.Chefmate.RestoreDBRequest.newBuilder().setCredentials(credentials)
//				.setServiceName("server").setDbUsername("wordpress").setDbUserPassword("cloud16")
//				.setDbName("wordpressdb").setBackupFilename("wordpressdbbackup").build();
//		new WordPressOpsImpl().restoreDB(req, null);

		/**
		 * deploy WP Tests
		 */
		// TODO: MySQL DB kann folgendes garnicht verarbeiten: db_username;
		// db_userpassword;
		// DeployWPAppRequest req =
		// DeployWPAppRequest.newBuilder().setCredentials(credentials).setDbName("wordpressdb")
		// .setDbHost("ec2-52-58-191-79.eu-central-1.compute.amazonaws.com").setDbPort("3306")
		// .setDbUserName("wordpress").setDbUserPassword("cloud16").setDbRootPassword("cloud16").build();
		// // DeployWPAppRequest req =
		// //
		// DeployWPAppRequest.newBuilder().setCredentials(credentials).build();
		// new WordPressOpsImpl().deployWPApp(req, null);

		int port = -1;

		for (int i = 0; i < args.length; i++)
		{
			if (args[i].equals("--help"))
			{
				showArgsPrompt();
			}
			if (args[i].equals("-dc"))
			{
				new EnvironmentInitializer(true);
				return;
			}
			if (args[i].equals("-i"))
			{
				EnvironmentInitializer env = new EnvironmentInitializer(false);
				env.init();
				return;
			}
			if (args[i].equals("-p"))
			{
				Config.getInstance(false, true);
				// Check if there's a following command
				if ((i + 1) < args.length)
				{
					try
					{
						port = Integer.parseInt(args[i + 1]);
						i++;
					} catch (NumberFormatException e)
					{
						ChefMateServer.showArgsPrompt();
						return;
					}
				}
			} else
			{
				ChefMateServer.showArgsPrompt();
				return;
			}
		}

		if (port == -1)
		{
			ChefMateServer.showArgsPrompt();
			return;
		}
		final ChefMateServer server = new ChefMateServer();
		try
		{
			Config.getInstance(false, true);
			server.start(port);
			server.blockUntilShutdown();
		} catch (IOException | InterruptedException ex)
		{
			logger.warning("### Error when starting server on port " + port + ".\n " + ex.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Shows the args prompt for startup arguments.
	 */
	private static void showArgsPrompt()
	{
		System.out.println("Usage: \n <appname> command argument");
		System.out.println("-p \t The port to listen to.");
		System.out.println(
				"-dc \t Writes the default config file and creates the server environment folder. CALL THIS BEFORE INIT.");
		System.out.println("-i \t Initializes the server environment.");
	}
}
