
import java.io.IOException;
import java.util.logging.Logger;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import services.WebShopGrpc;
import services.WebShopImpl;

/**
 * The WebShopServer offering products and orders for customers.
 * 
 * @author Tobias Freundorfer
 *
 */
public class WebshopServer
{
	private static final Logger logger = Logger.getLogger(WebshopServer.class.getName());

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
		this.server = ServerBuilder.forPort(this.port).addService(WebShopGrpc.bindService(new WebShopImpl())).build()
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
				WebshopServer.this.stop();
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

	/**
	 * Main launches the server from the command line.
	 */
	public static void main(String[] args) throws IOException, InterruptedException
	{
		int port = -1;
		for (int i = 0; i < args.length; i++)
		{
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
						WebshopServer.showArgsPrompt();
						System.exit(0);
					}
				}
			} else
			{
				WebshopServer.showArgsPrompt();
				System.exit(0);
			}
		}
		if (port == -1)
		{
			WebshopServer.showArgsPrompt();
			System.exit(0);
		}
		final WebshopServer server = new WebshopServer();
		server.start(port);
		server.blockUntilShutdown();
	}

	/**
	 * Shows the args prompt for startup arguments.
	 */
	private static void showArgsPrompt()
	{
		System.out.println("Usage: \n <appname> command argument");
		System.out.println("-p \t The port to listen to.");
	}
}
