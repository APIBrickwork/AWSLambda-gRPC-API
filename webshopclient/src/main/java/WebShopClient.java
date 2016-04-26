

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import services.WebShopGrpc;
import services.Webshop.Availability;
import services.Webshop.Costs;
import services.Webshop.Customer;
import services.Webshop.ListProductsParams;
import services.Webshop.Order;
import services.Webshop.OrderId;
import services.Webshop.Payment;
import services.Webshop.Product;
import services.Webshop.ProductId;
import services.Webshop.Order.Status;

/**
 * The WebShopClient that is able to request information from the WebShopServer.
 * 
 * @author Tobias Freundorfer
 *
 */
public class WebShopClient
{
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
	public WebShopClient(String host, int port)
	{
		this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
		this.blockingStub = WebShopGrpc.newBlockingStub(this.channel);
	}

	/**
	 * Sends the listProduct request to the WebShopServer.
	 */
	public void sendListProductsRequest()
	{
		logger.info("### Sending request for listProducts.");
		ListProductsParams req = ListProductsParams.newBuilder().setLimit(10).build();
		Iterator<Product> it;

		try
		{
			it = this.blockingStub.listProducts(req);

		} catch (StatusRuntimeException e)
		{
			logger.warning("### RPC failed: {0}" + e.getStatus());
			return;
		}
		// TODO: Catch ClosedChannelException (when server is not
		// running/responding)
		logger.info("### Received response");
		System.out.println("### Products ###");
		while (it.hasNext())
		{
			Product p = it.next();
			System.out.println("Product: " + p.toString());
		}
	}

	/**
	 * Sends the checkAvailability request to the WebShopServer.
	 * 
	 * @param id
	 *            The ProductId for which the availability should be requested.
	 */
	public void sendCheckAvailabilityRequest(ProductId id)
	{
		logger.info("### Sending request for checkAvailability with productId = " + id.getId());
		Availability av;
		try
		{
			av = this.blockingStub.checkAvailability(id);

		} catch (StatusRuntimeException e)
		{
			logger.warning("### RPC failed: {0}" + e.getStatus());
			return;
		}
		logger.info("### Received response.");
		System.out.println("Availability for productId " + id.getId() + " = " + av.getAvailable());
	}

	/**
	 * Sends the storeOrder request for the given Order.
	 * 
	 * @param order
	 *            The order that should be stored.
	 */
	public void sendStoreOrderRequest(Order order)
	{
		logger.info("### Sending request storeOrder.");
		OrderId orderId = null;
		try
		{
			orderId = this.blockingStub.storeOrderDetails(order);
		} catch (StatusRuntimeException e)
		{
			logger.warning("### RPC failed: {0}" + e.getStatus());
			return;
		}
		logger.info("### Received response.");
		System.out.println("Created new order with orderId = " + orderId.getId());
	}

	/**
	 * Sends the getOrderDetails request for the given orderId.
	 * 
	 * @param id
	 *            The OrderId for which the order details should be requested.
	 */
	public void sendGetOrderRequest(OrderId id)
	{
		logger.info("### Sending request getOrderDetails for orderId = " + id.getId() + ".");
		Order order = null;
		try
		{
			order = this.blockingStub.getOrderDetails(id);
		} catch (StatusRuntimeException e)
		{
			logger.warning("### RPC failed: {0}" + e.getStatus());
			return;
		}
		logger.info("### Received response.");
		if (!order.getId().isEmpty())
		{
			System.out.println("Order:");
			System.out.println(order.toString() + "\n Status: " + order.getStatus().toString());
		} else
		{
			logger.warning("### Server returned empty Order.");
			System.out.println("Order could not be found.");
		}
	}

	/**
	 * Sends the cancelOrder request for the given orderId.
	 * 
	 * @param id
	 *            The id of the order that should be canceled.
	 */
	public void sendCancelOrderRequest(OrderId id)
	{
		logger.info("### Sending request cancelOrder with orderId = " + id.getId());
		Order order = null;
		try
		{
			order = this.blockingStub.cancelOrder(id);
		} catch (StatusRuntimeException e)
		{
			logger.warning("### RPC failed: {0}" + e.getStatus());
			return;
		}
		logger.info("### Received response.");
		if (!order.getId().isEmpty())
		{
			System.out.println("Order:");
			System.out.println(order.toString());
		} else
		{
			logger.warning("### Server returned empty Order.");
			System.out.println("Order could not be found.");
		}
	}

	/**
	 * Sends the calcTransactionCosts request for the given orderId.
	 * 
	 * @param id
	 *            The orderId for which the transaction costs should be
	 *            calculated.
	 */
	public void sendCalcTransactionCostsRequest(OrderId id)
	{
		logger.info("### Sending request calcTransactionCosts with orderId = " + id.getId());
		Costs costs = null;
		try
		{
			costs = this.blockingStub.calcTransactionCosts(id);
		} catch (StatusRuntimeException e)
		{
			logger.warning("### RPC failed: {0}" + e.getStatus());
			return;
		}

		if (costs.getCosts() == 0.0)
		{
			logger.warning("### Returned costs were 0. Is the orderId valid?");
		} else
		{
			System.out.println("Costs: \n " + costs.getCosts() + " EUR");
		}
	}

	/**
	 * Sends the conductPayment request for the given payment.
	 * 
	 * @param payment
	 *            The payment that should be conducted.
	 */
	public void sendConductPaymentRequest(Payment payment)
	{
		logger.info("### Sending request for conductPayment.");
		Order order = null;
		try
		{
			order = this.blockingStub.conductPayment(payment);
		} catch (StatusRuntimeException e)
		{
			logger.warning("### RPC failed: {0}" + e.getStatus());
			return;
		}

		if (!order.getId().isEmpty())
		{
			System.out.println("Order:\n " + order.toString());
		} else
		{
			logger.warning("### Server returned empty Order.");
			System.out.println("Order could not be found.");
		}
	}

	/**
	 * Sends the calcShipmentCosts request for the given orderId. Shipment costs
	 * are the total weight of all products within an order.
	 * 
	 * @param id
	 *            The orderId of the order to calculate.
	 */
	public void sendCalcShipmentCostsRequest(OrderId id)
	{
		logger.info("### Sending request for calcShipmentCosts with orderId =" + id.getId() + ".");
		Costs weight = null;
		try
		{
			weight = this.blockingStub.calcShipmentCosts(id);
		} catch (StatusRuntimeException e)
		{
			logger.warning("### RPC failed: {0}" + e.getStatus());
			return;
		}
		logger.info("### Received response.");
		if (weight.getCosts() == 0.0)
		{
			logger.warning("### Returned costs were 0. Is the orderId valid?");
		} else
		{
			System.out.println("Weight: \n " + weight.getCosts() + " KG");
		}
	}

	/**
	 * Sends the shipProducts request for the given orderId.
	 * 
	 * @param id
	 *            The orderId of the order that should be shipped.
	 */
	public void sendShipProductsRequest(OrderId id)
	{
		logger.info("### Sending request for sendShipProducts with orderId =" + id.getId() + ".");
		Order order = null;
		try
		{
			order = this.blockingStub.shipProducts(id);
		} catch (StatusRuntimeException e)
		{
			logger.warning("### RPC failed: {0}" + e.getStatus());
			return;
		}
		logger.info("### Received response.");
		if (!order.getId().isEmpty())
		{
			System.out.println("Order:\n " + order.toString());
		} else
		{
			logger.warning("### Server returned empty Order.");
			System.out.println("Order could not be found.");
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
					WebShopClient.showArgsPrompt();
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
						WebShopClient.showArgsPrompt();
						System.exit(0);
					}
				} else
				{
					WebShopClient.showArgsPrompt();
					System.exit(0);
				}
			}
		}
		if (hostname.isEmpty() || port == -1)
		{
			WebShopClient.showArgsPrompt();
			System.exit(0);
		}

		System.out.println("Connecting to " + hostname + ":" + port);
		WebShopClient client = new WebShopClient(hostname, port);

		// Loop for user input commands
		Scanner scanner = new Scanner(System.in);
		boolean receiveUserCommands = true;
		while (receiveUserCommands)
		{
			WebShopClient.showUserCommandPrompt();
			System.out.println("\n Enter command: \n");
			String command = scanner.nextLine();

			if (command.equals("listProducts"))
			{
				client.sendListProductsRequest();
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
			} else if (command.startsWith("checkAvailability"))
			{
				String[] arr = command.split(" ");
				if (arr.length != 2)
				{
					logger.warning("### Wrong syntax.");
				} else
				{
					client.sendCheckAvailabilityRequest(ProductId.newBuilder().setId(arr[1]).build());
				}
			} else if (command.equals("storeOrder"))
			{
				ArrayList<ProductId> productIds = new ArrayList<>();
				int amountOfProducts = 0;

				System.out.println("\n Enter the amount of products: ");
				try
				{
					amountOfProducts = Integer.parseInt(scanner.nextLine());

				} catch (NumberFormatException e)
				{
					logger.warning("### Parsing was not possible. Please enter a number.");
					break;
				}
				for (int i = 0; i < amountOfProducts; i++)
				{
					System.out.println("\n Enter productId: ");
					productIds.add(ProductId.newBuilder().setId(scanner.nextLine()).build());
				}

				String customerId = UUID.randomUUID().toString();
				System.out.println("\n Enter customer firstname: ");
				String firstname = scanner.nextLine();
				System.out.println("\n Enter customer lastname: ");
				String lastname = scanner.nextLine();
				System.out.println("\n Enter customer shipping address: ");
				String shipadd = scanner.nextLine();
				System.out.println("\n Enter customer payment details: ");
				String paymentDetails = scanner.nextLine();

				Customer customer = Customer.newBuilder().setId(customerId).setFirstname(firstname)
						.setLastname(lastname).setShippingAddress(shipadd).setPaymentDetails(paymentDetails).build();

				// Initially always status NEW
				String orderId = UUID.randomUUID().toString();
				Order order = Order.newBuilder().setId(orderId).setStatus(Status.NEW).setCustomer(customer).build();
				for (int i = 0; i < productIds.size(); i++)
				{
					order = order.toBuilder().addProducts(productIds.get(i)).build();
				}

				client.sendStoreOrderRequest(order);
			} else if (command.startsWith("getOrder"))
			{
				String[] arr = command.split(" ");
				if (arr.length != 2)
				{
					logger.warning("### Wrong syntax.");
				} else
				{
					client.sendGetOrderRequest(OrderId.newBuilder().setId(arr[1]).build());
				}
			} else if (command.startsWith("cancelOrder"))
			{
				String[] arr = command.split(" ");
				if (arr.length != 2)
				{
					logger.warning("### Wrong syntax.");
				} else
				{
					client.sendCancelOrderRequest(OrderId.newBuilder().setId(arr[1]).build());
				}
			} else if (command.startsWith("calcTransactionCosts"))
			{
				String[] arr = command.split(" ");
				if (arr.length != 2)
				{
					logger.warning("### Wrong syntax.");
				} else
				{
					client.sendCalcTransactionCostsRequest(OrderId.newBuilder().setId(arr[1]).build());
				}
			} else if (command.startsWith("conductPayment"))
			{
				String[] arr = command.split(" ");
				if (arr.length != 3)
				{
					logger.warning("### Wrong syntax.");
				} else
				{
					float amount = 0.0F;
					try
					{
						amount = Float.parseFloat(arr[2]);
					} catch (NumberFormatException ex)
					{
						logger.warning("### Parsing was not possible. Please enter a floating point number.");
					}
					Payment payment = Payment.newBuilder().setId(OrderId.newBuilder().setId(arr[1])).setAmount(amount)
							.build();
					client.sendConductPaymentRequest(payment);
				}
			} else if (command.startsWith("calcShipmentCosts"))
			{
				String[] arr = command.split(" ");
				if (arr.length != 2)
				{
					logger.warning("### Wrong syntax.");
				} else
				{
					client.sendCalcShipmentCostsRequest(OrderId.newBuilder().setId(arr[1]).build());
				}
			}
			else if(command.startsWith("shipProducts")){
				String[] arr = command.split(" ");
				if (arr.length != 2)
				{
					logger.warning("### Wrong syntax.");
				} else
				{
					client.sendShipProductsRequest(OrderId.newBuilder().setId(arr[1]).build());
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
		System.out.println("listProducts \n\t Lists all the products registered in the servers database.");
		System.out.println("checkAvailability <ProductId> \n\t Checks the availability for the given productId");
		System.out.println("storeOrder \n\t Interactively creates a new Order and stores it.");
		System.out.println("getOrder <OrderId> \n\t Returns an order for the given orderId.");
		System.out.println("cancelOrder <OrderId> \n\t Cancels an Order for the given orderId.");
		System.out
				.println("calcTransactionCosts <OrderId> \n\t Calculates the transaction costs for the given orderId.");
		System.out.println(
				"conductPayment <OrderId> <Amount> \n\t Conducts the paymont for the given orderId and amount.");
		System.out.println("calcShipmentCosts <OrderId> \n\t Calculates the shipment costs for the given orderId.");
		System.out.println("shipProducts <OrderId> \n\t Ships all products for the given orderId.");
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
