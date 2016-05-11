import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import services.EC2OpsGrpc;
import services.EC2OpsImpl;
import services.Chefmate.Request.CreateVMRequest;
import util.Config;
import util.EnvironmentInitializer;

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

		// Ensure that config is read initially
		Config.getInstance(false, true);
		int port = -1;
		// TODO: Delete demo code after testing
		CreateVMRequest req = CreateVMRequest.newBuilder().setName("vm1").setTag("chefmate").build();
		new EC2OpsImpl().createVM(req, null);

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
