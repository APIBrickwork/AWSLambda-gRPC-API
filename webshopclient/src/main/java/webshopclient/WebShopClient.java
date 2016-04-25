package webshopclient;

import java.io.Console;
import java.nio.channels.ClosedChannelException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.internal.Stream;
import webshopclient.Webshop.ListProductsParams;
import webshopclient.Webshop.Product;
import webshopclient.Webshop.ProductId;

/**
 * The WebShopClient that is able to request information from the WebShopServer.
 * @author Tobias Freundorfer
 *
 */
public class WebShopClient {
	private static final Logger logger = Logger.getLogger(WebShopClient.class.getName());

	/**
	 * Channel for the connection to the server.
	 */
	private final ManagedChannel channel;

	/**
	 * The blocking Stub (response is synchronous).
	 */
	private final WebShopGrpc.WebShopBlockingStub blockingStub;

	/**
	 * Creates a new instance of the WebShopClient connected to the
	 * WebShopServer.
	 * 
	 * @param host
	 *            The hostname of the WebShopServer.
	 * @param port
	 *            The port of the WebShopServer.
	 */
	public WebShopClient(String host, int port) {
		this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
		this.blockingStub = WebShopGrpc.newBlockingStub(this.channel);
	}

	public void sendListProductsRequest() {
		logger.info("Sending synchronous demo request");
		ListProductsParams req = ListProductsParams.newBuilder().setLimit(10).build();
		Iterator<Product> it;

		try {
			it = this.blockingStub.listProducts(req);

		}
		catch (StatusRuntimeException e) {
			logger.warning("RPC failed: {0}" + e.getStatus());
			return;
		}
		// TODO: Catch ClosedChannelException (when server is not running/responding)
		logger.info("Received response");
		
		while(it.hasNext()) {
			Product p = it.next();
			logger.info("Product: " + p.getId() + " | " + p.getName());
		}
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	public static void main(String[] args) {
		
		// Read input args
		String hostname = "";
		int port = -1;
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-h"))
			{
				// Check if there's a following command
				if((i+1) < args.length){
					hostname = args[i+1];
					i++;
				}
				else{
					WebShopClient.showArgsPrompt();
					System.exit(0);
				}
			}
			if(args[i].equals("-p"))
			{
				if((i+1)<args.length){
					try{
						port = Integer.parseInt(args[i+1]);						
						i++;
					}catch(NumberFormatException e){
						WebShopClient.showArgsPrompt();
						System.exit(0);
					}
				}
				else{
					WebShopClient.showArgsPrompt();
					System.exit(0);
				}
			}
		}
		if(hostname.isEmpty() || port == -1){
			WebShopClient.showArgsPrompt();
			System.exit(0);
		}
		
		System.out.println("Connecting to " + hostname + ":" + port);
		WebShopClient client = new WebShopClient(hostname, port);
		
		// Loop for user input commands
		Scanner scanner = new Scanner(System.in);
		boolean receiveUserCommands = true;
		while(receiveUserCommands)
		{
			WebShopClient.showUserCommandPrompt();
			System.out.println("\n Enter command: \n");
			String command = scanner.nextLine();
			
			if(command.equals("listProducts"))
			{
				client.sendListProductsRequest();
			}
			else if(command.equals("shutdown")){
				try
				{
					client.shutdown();
					receiveUserCommands = false;
				} catch (InterruptedException e)
				{
					logger.warning("Client was interrupted at shutdown.");
					receiveUserCommands = false;
				}
			}
		}
		scanner.close();
	}
	
	/**
	 * Shows the command prompt for user commands.
	 */
	private static void showUserCommandPrompt(){
		System.out.println("Available Commands: \n");
		System.out.println("listProducts \t Lists all the products registered in the servers database.");
		System.out.println("shutdown \t Terminates this client and closes all connections.");

	}
	
	/**
	 * Shows the args prompt for startup arguments.
	 */
	private static void showArgsPrompt(){
		System.out.println("Usage: \n <appname> command argument");
		System.out.println("-h \t The host address to connect to. \n -p \t The port to connect to.");
	
	}
}
