import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import services.Chefmate;
import services.EC2OpsGrpc;
import services.Chefmate.CreateVMRequest;
import services.Chefmate.CreateVMResponse;
import services.Chefmate.DestroyVMRequest;
import services.Chefmate.DestroyVMResponse;
import services.Chefmate.VMInstanceId;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;




/**
 * The ChefMateClient offering connection to the ChefMateServer and its services.
 * @author Tobias Freundorfer
 *
 */
public class ChefMateClient
{	
	// TODO: See WebShopClient on how to set up this class!!
	private static final Logger logger = Logger.getLogger(ChefMateClient.class.getName());
	
	/**
	 * Channel for the connection to the server.
	 */
	private final ManagedChannel channel;
	
	/**
	 * The blocking Stub (response is synchronous).
	 */
	private final EC2OpsGrpc.EC2OpsBlockingStub blockingStub;
	
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
		System.out.println("Created new VM with VmInfo = " + createVMResponse.getInfo());
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
		System.out.println("Destoyed VM with InstanceId" +destroyVMRequest.getId()+" = " + destroyVMResponse.getSuccess());
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
				String requestId = UUID.randomUUID().toString();
				System.out.println("\n Enter Instance Name: ");
				String name = scanner.nextLine();
				System.out.println("\n Enter Instance Tag: ");
				String tag = scanner.nextLine();
				System.out.println("\n Enter Instance Region: ");
				String region = scanner.nextLine();
				System.out.println("\n Enter Instance ImageId: ");
				String imageId = scanner.nextLine();
				System.out.println("\n Enter Instance Type : ");
				String instanceType = scanner.nextLine();
				System.out.println("\n Enter Security GroupIds: ");
				String securityGroupIds = scanner.nextLine();

				Chefmate.CreateVMRequest createVMRequest = Chefmate.CreateVMRequest.newBuilder().setName(name)
						.setTag(tag).setRegion(region).setImageId(imageId).setInstanceType(instanceType).addSecurityGroupIds(securityGroupIds).build();

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
					
					DestroyVMRequest destroyVMRequest = DestroyVMRequest.newBuilder().setId(VMInstanceId.newBuilder().setId(arr[1])).build();
					client.sendDestroyVMRequest(destroyVMRequest);
				}
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
		System.out.println("createVM \n\t Create VM Instance ");
		System.out.println("destroyVM \n\t Destroy the VM Instance");
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
