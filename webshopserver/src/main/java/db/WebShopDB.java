package db;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import services.Webshop;
import services.Webshop.Customer;
import services.Webshop.Order;
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
	 * List containing all customers.
	 */
	private List<Customer> customers = new ArrayList<Customer>();

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

		// demo customers
		Customer c1 = Customer.newBuilder().setId(UUID.randomUUID().toString()).setFirstname("Carl")
				.setLastname("Customer").setPaymentDetails("VISA").setShippingAddress("Middle of nowhere 2").build();
		Customer c2 = Customer.newBuilder().setId(UUID.randomUUID().toString()).setFirstname("Cecile")
				.setLastname("Customer").setPaymentDetails("MasterCard").setShippingAddress("Somewhere else 3").build();

		this.customers.add(c1);
		this.customers.add(c2);

		// demo orders
		ProductId gumId = ProductId.newBuilder().setId(gum.getId()).build();
		ProductId tomatoId = ProductId.newBuilder().setId(tomato.getId()).build();
		
		Order o1 = Order.newBuilder().setId(UUID.randomUUID().toString()).setCustomer(c1).addProducts(gumId)
				.setStatusValue(0).build(); // new order
		Order o2 = Order.newBuilder().setId(UUID.randomUUID().toString()).setCustomer(c2).addProducts(gumId).addProducts(tomatoId)
				.setStatusValue(1).build(); // payed order
		
		this.orders.add(o1);
		this.orders.add(o2);
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}
