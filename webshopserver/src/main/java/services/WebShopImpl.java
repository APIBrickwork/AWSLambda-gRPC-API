package services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import db.WebShopDB;
import io.grpc.stub.StreamObserver;
import services.WebShopGrpc.WebShop;
import services.Webshop.Availability;
import services.Webshop.Costs;
import services.Webshop.ListProductsParams;
import services.Webshop.Order;
import services.Webshop.Order.Status;
import services.Webshop.OrderId;
import services.Webshop.Payment;
import services.Webshop.Product;
import services.Webshop.ProductId;

/**
 * WebShop service implementation for gRPC.
 * 
 * @author Tobias Freundorfer
 *
 */
public class WebShopImpl implements WebShop
{
	/**
	 * The logging instance.
	 */
	private static final Logger logger = Logger.getLogger(WebShopImpl.class.getName());

	/**
	 * The demo database.
	 */
	private WebShopDB db = null;

	/**
	 * Creates a new instance of the WebShop Service Implementation.
	 */
	public WebShopImpl()
	{
		this.db = WebShopDB.getInstance();
	}

	/**
	 * Lists all products within the given limit (max replied products).
	 */
	public void listProducts(ListProductsParams request, StreamObserver<Product> responseObserver)
	{
		logger.info("### Received request for listProducts with limit " + request.getLimit() + ".");

		// The amount of products requested
		int limit = -1;
		if (request.getLimit() > this.db.getProducts().size())
		{
			limit = this.db.getProducts().size();
		} else
		{
			limit = request.getLimit();
		}

		for (int i = 0; i < limit; i++)
		{
			logger.info("### Responding with product id = " + this.db.getProducts().get(i).getId() + " | name = "
					+ this.db.getProducts().get(i).getName());
			responseObserver.onNext(this.db.getProducts().get(i));
		}
		responseObserver.onCompleted();

		logger.info("### Sent response.");
	}

	/**
	 * Checks if the given product (identified by its productId) is available.
	 */
	public void checkAvailability(ProductId request, StreamObserver<Availability> responseObserver)
	{
		logger.info("### Received request for availability for product = " + request.getId());

		boolean productAvailable = false;
		for (int i = 0; i < this.db.getProducts().size(); i++)
		{
			if (this.db.getProducts().get(i).getId().equals(request.getId()))
			{
				productAvailable = true;
				break;
			}
		}
		responseObserver.onNext(Availability.newBuilder().setAvailable(productAvailable).build());
		responseObserver.onCompleted();

		logger.info("### Sent response with availability " + productAvailable);
	}

	/**
	 * Stores the given order details.
	 */
	public void storeOrderDetails(Order request, StreamObserver<OrderId> responseObserver)
	{
		logger.info("### Received request for storeOrderDetails.");

		this.db.getOrders().add(request);

		OrderId oi = OrderId.newBuilder().setId(request.getId()).build();
		responseObserver.onNext(oi);
		responseObserver.onCompleted();

		logger.info("### Sent response for id = " + request.getId() + " | orders database size = "
				+ this.db.getOrders().size());
	}

	/**
	 * Returns order information for the requested orderId.
	 */
	public void getOrderDetails(OrderId request, StreamObserver<Order> responseObserver)
	{
		logger.info("### Received request for getOrderDetails with orderId = " + request.getId());
		Order requestedOrder = null;

		for (int i = 0; i < this.db.getOrders().size(); i++)
		{
			if (this.db.getOrders().get(i).getId().equals(request.getId()))
			{
				requestedOrder = this.db.getOrders().get(i);
				break;
			}
		}
		if (requestedOrder == null)
		{
			logger.warning("### Corresponding order for orderId = " + request.getId() + " could not be found.");
			responseObserver.onNext(null);
		} else
		{
			responseObserver.onNext(requestedOrder);
		}
		responseObserver.onCompleted();

		logger.info("### Sent response.");
	}

	/**
	 * Cancels an order.
	 */
	public void cancelOrder(OrderId request, StreamObserver<Order> responseObserver)
	{
		logger.info("### Received request for cancelOrder with orderId = " + request.getId());

		Order requestedOrder = null;
		for (int i = 0; i < this.db.getOrders().size(); i++)
		{
			if (this.db.getOrders().get(i).getId().equals(request.getId()))
			{
				requestedOrder = this.db.getOrders().get(i);
				String statusBefore = requestedOrder.getStatus().toString();
				Order newOrder = requestedOrder.toBuilder().setStatus(Status.CANCELED).build();
				logger.info("### Set status from " + statusBefore + " to "
						+ this.db.getOrders().get(i).getStatus().toString());
				this.db.getOrders().set(i, newOrder);

				responseObserver.onNext(newOrder);
				break;
			}
		}

		if (requestedOrder == null)
		{
			logger.warning("### Corresponding order for orderId = " + request.getId() + " could not be found.");
			responseObserver.onNext(null);
		}
		responseObserver.onCompleted();

		logger.info("### Sent response.");
	}

	/**
	 * Calcualtes the transaction costs.
	 */
	public void calcTransactionCosts(OrderId request, StreamObserver<Costs> responseObserver)
	{
		logger.info("### Received request for calcTransactionCosts with orderId = " + request.getId());

		float costs = 0.0F;
		List<Product> prod = this.db.getProductsOfOrder(request);
		for (Product p : prod)
		{
			logger.info("### Added productId = " + p.getId() + " | price = " + p.getPrice());
			costs += p.getPrice();

		}

		responseObserver.onNext(Costs.newBuilder().setCosts(costs).build());
		responseObserver.onCompleted();

		logger.info("### Sent response with cost = " + costs);
	}

	/**
	 * Conducts the payment for the given order.
	 */
	public void conductPayment(Payment request, StreamObserver<Order> responseObserver)
	{
		logger.info("### Received request for conductPayment with orderId = " + request.getId());

		List<ProductId> productIds = new ArrayList<ProductId>();
		Order requestedOrder = null;
		int requestedOrderIndex = -1;
		float costs = 0.0F;

		logger.info("### Searching in database containing " + this.db.getOrders().size() + " orders.");
		// Get products associated to the specific order
		for (int i = 0; i < this.db.getOrders().size(); i++)
		{
			if (this.db.getOrders().get(i).getId().equals(request.getId().getId()))
			{
				logger.info("### Found requested order.");
				requestedOrder = this.db.getOrders().get(i);
				requestedOrderIndex = i;
				productIds = requestedOrder.getProductsList();
				break;
			}
		}
		logger.info("### Order contains " + productIds.size() + " products.");

		for (int i = 0; i < this.db.getProducts().size(); i++)
		{
			for (int j = 0; j < productIds.size(); j++)
			{
				if (this.db.getProducts().get(i).getId().equals(productIds.get(j).getId()))
				{
					logger.info("### Added productId = " + this.db.getProducts().get(i).getId() + " | price = "
							+ this.db.getProducts().get(i).getPrice());
					costs += this.db.getProducts().get(i).getPrice();
				}
			}
		}

		if (costs == request.getAmount())
		{
			Order newOrder = Order.newBuilder(requestedOrder).setStatus(Status.PAYED).build();
			this.db.getOrders().remove(requestedOrderIndex);
			this.db.getOrders().add(newOrder);
			responseObserver.onNext(newOrder);
		} else
		{
			logger.warning("### Tried to pay costs = " + costs + " with payment = " + request.getAmount());
			responseObserver.onNext(null);
		}
		responseObserver.onCompleted();

		logger.info("### Sent response.");
	}

	/**
	 * Calculates the shipment costs, which is the sum of all product weights.
	 */
	public void calcShipmentCosts(OrderId request, StreamObserver<Costs> responseObserver)
	{
		logger.info("### Received request for calcShipmentCosts with orderId = " + request.getId());
		
		float weight = 0.0F;
		List<Product> prod = this.db.getProductsOfOrder(request);
		for (Product p : prod)
		{
			logger.info("### Added productId = " + p.getId() + " | weight = " + p.getWeight());
			weight += p.getWeight();

		}

		responseObserver.onNext(Costs.newBuilder().setCosts(weight).build());
		responseObserver.onCompleted();

		logger.info("### Sent response with weight = " + weight);
	}

	/**
	 * Ships the products and updates the orders status.
	 */
	public void shipProducts(OrderId request, StreamObserver<Order> responseObserver)
	{
		logger.info("### Received request for shipProducts with orderId = " + request.getId());

		Order requestedOrder = null;
		for (int i = 0; i < this.db.getOrders().size(); i++)
		{
			if (this.db.getOrders().get(i).getId().equals(request.getId()))
			{
				String statusBefore = this.db.getOrders().get(i).getStatus().toString();
				this.db.getOrders().get(i).toBuilder().setStatus(Status.SHIPPED);
				logger.info("### Set status from " + statusBefore + " to "
						+ this.db.getOrders().get(i).getStatus().toString());
				responseObserver.onNext(requestedOrder);
				break;
			}
		}

		if (requestedOrder == null)
		{
			logger.warning("### Corresponding order for orderId = " + request.getId() + " could not be found.");
			responseObserver.onNext(null);
		}
		responseObserver.onCompleted();

		logger.info("### Sent response.");
	}
}