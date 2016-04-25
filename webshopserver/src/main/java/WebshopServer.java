
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
	private int port = 50505;

	/**
	 * The gRPC server.
	 */
	private Server server;

	/**
	 * Starts the server.
	 * 
	 * @throws IOException
	 */
	private void start() throws IOException
	{
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
		final WebshopServer server = new WebshopServer();
		server.start();
		server.blockUntilShutdown();
	}
}
