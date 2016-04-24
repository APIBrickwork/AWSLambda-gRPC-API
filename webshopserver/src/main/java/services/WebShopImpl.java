package services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.grpc.stub.StreamObserver;
import services.WebShopGrpc.WebShop;
import services.Webshop.Availability;
import services.Webshop.Costs;
import services.Webshop.ListProductsParams;
import services.Webshop.Order;
import services.Webshop.OrderId;
import services.Webshop.Payment;
import services.Webshop.Product;
import services.Webshop.ProductId;

public class WebShopImpl implements WebShop {

	private List<Product> products = new ArrayList<Product>();
	
	public WebShopImpl(){
		this.buildDemoDB();
	}
	
	public void listProducts(ListProductsParams request, StreamObserver<Product> responseObserver) {
		// Return the amount of products requested
		for(int i=0;i<request.getLimit();i++){
			responseObserver.onNext(this.products.get(i));
		}
		responseObserver.onCompleted();
	}

	public void checkAvailability(ProductId request, StreamObserver<Availability> responseObserver) {
		// TODO Auto-generated method stub

	}

	public void storeOrderDetails(Order request, StreamObserver<OrderId> responseObserver) {
		// TODO Auto-generated method stub

	}

	public void getOrderDetails(OrderId request, StreamObserver<Order> responseObserver) {
		// TODO Auto-generated method stub

	}

	public void cancelOrder(OrderId request, StreamObserver<Order> responseObserver) {
		// TODO Auto-generated method stub

	}

	public void calcTransactionCosts(OrderId request, StreamObserver<Costs> responseObserver) {
		// TODO Auto-generated method stub

	}

	public void conductPayment(Payment request, StreamObserver<Order> responseObserver) {
		// TODO Auto-generated method stub

	}

	public void calcShipmentCosts(OrderId request, StreamObserver<Costs> responseObserver) {
		// TODO Auto-generated method stub

	}

	public void shipProducts(OrderId request, StreamObserver<Order> responseObserver) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Creates an initally filled demo 'database'. This is for the proof of concept only.
	 */
	private void buildDemoDB()
	{
		Product gum = Product.newBuilder().setId(UUID.randomUUID().toString()).setName("Chewing Gum")
				.setCategory("Food").setProducer("Wrigleys").setWeight(0.01F).setPrice(0.5F).build();
		Product tomato = Product.newBuilder().setId(UUID.randomUUID().toString()).setName("Tomato")
				.setCategory("Food").setProducer("Spain").setWeight(0.05F).setPrice(0.9F).build();
		
		
		this.products.add(gum);
		this.products.add(tomato);
	}

}