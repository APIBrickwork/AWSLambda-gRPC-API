package db;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import services.Webshop;
import services.Webshop.Customer;
import services.Webshop.Order;
import services.Webshop.Order.Status;
import services.Webshop.OrderId;
import services.Webshop.Product;
import services.Webshop.ProductId;

/**
 * A demo 'database'.
 * 
 * @author Tobias Freundorfer
 *
 */
public class WebShopDB {

	/**
	 * The singleton instance.
	 */
	private static WebShopDB instance = null;

	/**
	 * List containing all products.
	 */
	private List<Product> products = new ArrayList<Product>();

	/**
	 * This containing all orders.
	 */
	private List<Order> orders = new ArrayList<Order>();
	
	
	private WebShopDB() {
		this.buildDemoData();
	}

	/**
	 * Returns the single instance of this WebShopDB.
	 * @return The single instance.
	 */
	public static synchronized WebShopDB getInstance() {
		if (instance == null) {
			instance = new WebShopDB();
		}
		return instance;
	}

	/**
	 * Creates demo data for this database.
	 */
	private void buildDemoData() {
		// demo products
		Product gum = Product.newBuilder().setId(UUID.randomUUID().toString()).setName("Chewing Gum")
				.setCategory("Food").setProducer("Wrigleys").setWeight(0.01F).setPrice(0.5F).build();
		Product tomato = Product.newBuilder().setId(UUID.randomUUID().toString()).setName("Tomato").setCategory("Food")
				.setProducer("Spain").setWeight(0.05F).setPrice(0.9F).build();

		this.products.add(gum);
		this.products.add(tomato);
	}
	
	/**
	 * Returns all products associated with the given orderId.
	 * @param id The orderId of the order.
	 * @return A list containing all products of the given order.
	 */
	public List<Product> getProductsOfOrder(OrderId id){
		// Get the ids of all products associated with the given orderId
		List<ProductId> productIds = new ArrayList<ProductId>();
		List<Product> result = new ArrayList<Product>();
		
		for (int i = 0; i < this.orders.size(); i++)
		{
			if (this.orders.get(i).getId().equals(id.getId()))
			{
				productIds = this.orders.get(i).getProductsList();
				break;
			}
		}
		// Get the products by id
		for (int i = 0; i < this.products.size(); i++)
		{
			for (int j = 0; j < productIds.size(); j++)
			{
				if (this.products.get(i).getId().equals(productIds.get(j).getId()))
				{
					result.add(this.products.get(i));
				}
			}
		}
		return result;
	}
		
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}
