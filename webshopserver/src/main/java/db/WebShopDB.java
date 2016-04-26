package db;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import services.Webshop;
import services.Webshop.Customer;
import services.Webshop.Order;
import services.Webshop.Order.Status;
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
