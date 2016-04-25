package webshopclient;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;

@javax.annotation.Generated("by gRPC proto compiler")
public class WebShopGrpc {

  private WebShopGrpc() {}

  public static final String SERVICE_NAME = "WebShop";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Webshop.ListProductsParams,
      Webshop.Product> METHOD_LIST_PRODUCTS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING,
          generateFullMethodName(
              "WebShop", "listProducts"),
          io.grpc.protobuf.ProtoUtils.marshaller(Webshop.ListProductsParams.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Webshop.Product.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Webshop.ProductId,
      Webshop.Availability> METHOD_CHECK_AVAILABILITY =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "WebShop", "checkAvailability"),
          io.grpc.protobuf.ProtoUtils.marshaller(Webshop.ProductId.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Webshop.Availability.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Webshop.Order,
      Webshop.OrderId> METHOD_STORE_ORDER_DETAILS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "WebShop", "storeOrderDetails"),
          io.grpc.protobuf.ProtoUtils.marshaller(Webshop.Order.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Webshop.OrderId.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Webshop.OrderId,
      Webshop.Order> METHOD_GET_ORDER_DETAILS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "WebShop", "getOrderDetails"),
          io.grpc.protobuf.ProtoUtils.marshaller(Webshop.OrderId.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Webshop.Order.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Webshop.OrderId,
      Webshop.Order> METHOD_CANCEL_ORDER =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "WebShop", "cancelOrder"),
          io.grpc.protobuf.ProtoUtils.marshaller(Webshop.OrderId.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Webshop.Order.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Webshop.OrderId,
      Webshop.Costs> METHOD_CALC_TRANSACTION_COSTS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "WebShop", "calcTransactionCosts"),
          io.grpc.protobuf.ProtoUtils.marshaller(Webshop.OrderId.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Webshop.Costs.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Webshop.Payment,
      Webshop.Order> METHOD_CONDUCT_PAYMENT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "WebShop", "conductPayment"),
          io.grpc.protobuf.ProtoUtils.marshaller(Webshop.Payment.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Webshop.Order.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Webshop.OrderId,
      Webshop.Costs> METHOD_CALC_SHIPMENT_COSTS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "WebShop", "calcShipmentCosts"),
          io.grpc.protobuf.ProtoUtils.marshaller(Webshop.OrderId.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Webshop.Costs.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Webshop.OrderId,
      Webshop.Order> METHOD_SHIP_PRODUCTS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "WebShop", "shipProducts"),
          io.grpc.protobuf.ProtoUtils.marshaller(Webshop.OrderId.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Webshop.Order.getDefaultInstance()));

  public static WebShopStub newStub(io.grpc.Channel channel) {
    return new WebShopStub(channel);
  }

  public static WebShopBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new WebShopBlockingStub(channel);
  }

  public static WebShopFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new WebShopFutureStub(channel);
  }

  public static interface WebShop {

    public void listProducts(Webshop.ListProductsParams request,
        io.grpc.stub.StreamObserver<Webshop.Product> responseObserver);

    public void checkAvailability(Webshop.ProductId request,
        io.grpc.stub.StreamObserver<Webshop.Availability> responseObserver);

    public void storeOrderDetails(Webshop.Order request,
        io.grpc.stub.StreamObserver<Webshop.OrderId> responseObserver);

    public void getOrderDetails(Webshop.OrderId request,
        io.grpc.stub.StreamObserver<Webshop.Order> responseObserver);

    public void cancelOrder(Webshop.OrderId request,
        io.grpc.stub.StreamObserver<Webshop.Order> responseObserver);

    public void calcTransactionCosts(Webshop.OrderId request,
        io.grpc.stub.StreamObserver<Webshop.Costs> responseObserver);

    public void conductPayment(Webshop.Payment request,
        io.grpc.stub.StreamObserver<Webshop.Order> responseObserver);

    public void calcShipmentCosts(Webshop.OrderId request,
        io.grpc.stub.StreamObserver<Webshop.Costs> responseObserver);

    public void shipProducts(Webshop.OrderId request,
        io.grpc.stub.StreamObserver<Webshop.Order> responseObserver);
  }

  public static interface WebShopBlockingClient {

    public java.util.Iterator<Webshop.Product> listProducts(
        Webshop.ListProductsParams request);

    public Webshop.Availability checkAvailability(Webshop.ProductId request);

    public Webshop.OrderId storeOrderDetails(Webshop.Order request);

    public Webshop.Order getOrderDetails(Webshop.OrderId request);

    public Webshop.Order cancelOrder(Webshop.OrderId request);

    public Webshop.Costs calcTransactionCosts(Webshop.OrderId request);

    public Webshop.Order conductPayment(Webshop.Payment request);

    public Webshop.Costs calcShipmentCosts(Webshop.OrderId request);

    public Webshop.Order shipProducts(Webshop.OrderId request);
  }

  public static interface WebShopFutureClient {

    public com.google.common.util.concurrent.ListenableFuture<Webshop.Availability> checkAvailability(
        Webshop.ProductId request);

    public com.google.common.util.concurrent.ListenableFuture<Webshop.OrderId> storeOrderDetails(
        Webshop.Order request);

    public com.google.common.util.concurrent.ListenableFuture<Webshop.Order> getOrderDetails(
        Webshop.OrderId request);

    public com.google.common.util.concurrent.ListenableFuture<Webshop.Order> cancelOrder(
        Webshop.OrderId request);

    public com.google.common.util.concurrent.ListenableFuture<Webshop.Costs> calcTransactionCosts(
        Webshop.OrderId request);

    public com.google.common.util.concurrent.ListenableFuture<Webshop.Order> conductPayment(
        Webshop.Payment request);

    public com.google.common.util.concurrent.ListenableFuture<Webshop.Costs> calcShipmentCosts(
        Webshop.OrderId request);

    public com.google.common.util.concurrent.ListenableFuture<Webshop.Order> shipProducts(
        Webshop.OrderId request);
  }

  public static class WebShopStub extends io.grpc.stub.AbstractStub<WebShopStub>
      implements WebShop {
    private WebShopStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WebShopStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WebShopStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WebShopStub(channel, callOptions);
    }

    @java.lang.Override
    public void listProducts(Webshop.ListProductsParams request,
        io.grpc.stub.StreamObserver<Webshop.Product> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_LIST_PRODUCTS, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void checkAvailability(Webshop.ProductId request,
        io.grpc.stub.StreamObserver<Webshop.Availability> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CHECK_AVAILABILITY, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void storeOrderDetails(Webshop.Order request,
        io.grpc.stub.StreamObserver<Webshop.OrderId> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_STORE_ORDER_DETAILS, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void getOrderDetails(Webshop.OrderId request,
        io.grpc.stub.StreamObserver<Webshop.Order> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_ORDER_DETAILS, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void cancelOrder(Webshop.OrderId request,
        io.grpc.stub.StreamObserver<Webshop.Order> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CANCEL_ORDER, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void calcTransactionCosts(Webshop.OrderId request,
        io.grpc.stub.StreamObserver<Webshop.Costs> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CALC_TRANSACTION_COSTS, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void conductPayment(Webshop.Payment request,
        io.grpc.stub.StreamObserver<Webshop.Order> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CONDUCT_PAYMENT, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void calcShipmentCosts(Webshop.OrderId request,
        io.grpc.stub.StreamObserver<Webshop.Costs> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CALC_SHIPMENT_COSTS, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void shipProducts(Webshop.OrderId request,
        io.grpc.stub.StreamObserver<Webshop.Order> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SHIP_PRODUCTS, getCallOptions()), request, responseObserver);
    }
  }

  public static class WebShopBlockingStub extends io.grpc.stub.AbstractStub<WebShopBlockingStub>
      implements WebShopBlockingClient {
    private WebShopBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WebShopBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WebShopBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WebShopBlockingStub(channel, callOptions);
    }

    @java.lang.Override
    public java.util.Iterator<Webshop.Product> listProducts(
        Webshop.ListProductsParams request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_LIST_PRODUCTS, getCallOptions(), request);
    }

    @java.lang.Override
    public Webshop.Availability checkAvailability(Webshop.ProductId request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CHECK_AVAILABILITY, getCallOptions(), request);
    }

    @java.lang.Override
    public Webshop.OrderId storeOrderDetails(Webshop.Order request) {
      return blockingUnaryCall(
          getChannel(), METHOD_STORE_ORDER_DETAILS, getCallOptions(), request);
    }

    @java.lang.Override
    public Webshop.Order getOrderDetails(Webshop.OrderId request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_ORDER_DETAILS, getCallOptions(), request);
    }

    @java.lang.Override
    public Webshop.Order cancelOrder(Webshop.OrderId request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CANCEL_ORDER, getCallOptions(), request);
    }

    @java.lang.Override
    public Webshop.Costs calcTransactionCosts(Webshop.OrderId request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CALC_TRANSACTION_COSTS, getCallOptions(), request);
    }

    @java.lang.Override
    public Webshop.Order conductPayment(Webshop.Payment request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CONDUCT_PAYMENT, getCallOptions(), request);
    }

    @java.lang.Override
    public Webshop.Costs calcShipmentCosts(Webshop.OrderId request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CALC_SHIPMENT_COSTS, getCallOptions(), request);
    }

    @java.lang.Override
    public Webshop.Order shipProducts(Webshop.OrderId request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SHIP_PRODUCTS, getCallOptions(), request);
    }
  }

  public static class WebShopFutureStub extends io.grpc.stub.AbstractStub<WebShopFutureStub>
      implements WebShopFutureClient {
    private WebShopFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WebShopFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WebShopFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WebShopFutureStub(channel, callOptions);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Webshop.Availability> checkAvailability(
        Webshop.ProductId request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CHECK_AVAILABILITY, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Webshop.OrderId> storeOrderDetails(
        Webshop.Order request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_STORE_ORDER_DETAILS, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Webshop.Order> getOrderDetails(
        Webshop.OrderId request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_ORDER_DETAILS, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Webshop.Order> cancelOrder(
        Webshop.OrderId request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CANCEL_ORDER, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Webshop.Costs> calcTransactionCosts(
        Webshop.OrderId request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CALC_TRANSACTION_COSTS, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Webshop.Order> conductPayment(
        Webshop.Payment request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CONDUCT_PAYMENT, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Webshop.Costs> calcShipmentCosts(
        Webshop.OrderId request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CALC_SHIPMENT_COSTS, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Webshop.Order> shipProducts(
        Webshop.OrderId request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SHIP_PRODUCTS, getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST_PRODUCTS = 0;
  private static final int METHODID_CHECK_AVAILABILITY = 1;
  private static final int METHODID_STORE_ORDER_DETAILS = 2;
  private static final int METHODID_GET_ORDER_DETAILS = 3;
  private static final int METHODID_CANCEL_ORDER = 4;
  private static final int METHODID_CALC_TRANSACTION_COSTS = 5;
  private static final int METHODID_CONDUCT_PAYMENT = 6;
  private static final int METHODID_CALC_SHIPMENT_COSTS = 7;
  private static final int METHODID_SHIP_PRODUCTS = 8;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WebShop serviceImpl;
    private final int methodId;

    public MethodHandlers(WebShop serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LIST_PRODUCTS:
          serviceImpl.listProducts((Webshop.ListProductsParams) request,
              (io.grpc.stub.StreamObserver<Webshop.Product>) responseObserver);
          break;
        case METHODID_CHECK_AVAILABILITY:
          serviceImpl.checkAvailability((Webshop.ProductId) request,
              (io.grpc.stub.StreamObserver<Webshop.Availability>) responseObserver);
          break;
        case METHODID_STORE_ORDER_DETAILS:
          serviceImpl.storeOrderDetails((Webshop.Order) request,
              (io.grpc.stub.StreamObserver<Webshop.OrderId>) responseObserver);
          break;
        case METHODID_GET_ORDER_DETAILS:
          serviceImpl.getOrderDetails((Webshop.OrderId) request,
              (io.grpc.stub.StreamObserver<Webshop.Order>) responseObserver);
          break;
        case METHODID_CANCEL_ORDER:
          serviceImpl.cancelOrder((Webshop.OrderId) request,
              (io.grpc.stub.StreamObserver<Webshop.Order>) responseObserver);
          break;
        case METHODID_CALC_TRANSACTION_COSTS:
          serviceImpl.calcTransactionCosts((Webshop.OrderId) request,
              (io.grpc.stub.StreamObserver<Webshop.Costs>) responseObserver);
          break;
        case METHODID_CONDUCT_PAYMENT:
          serviceImpl.conductPayment((Webshop.Payment) request,
              (io.grpc.stub.StreamObserver<Webshop.Order>) responseObserver);
          break;
        case METHODID_CALC_SHIPMENT_COSTS:
          serviceImpl.calcShipmentCosts((Webshop.OrderId) request,
              (io.grpc.stub.StreamObserver<Webshop.Costs>) responseObserver);
          break;
        case METHODID_SHIP_PRODUCTS:
          serviceImpl.shipProducts((Webshop.OrderId) request,
              (io.grpc.stub.StreamObserver<Webshop.Order>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServerServiceDefinition bindService(
      final WebShop serviceImpl) {
    return io.grpc.ServerServiceDefinition.builder(SERVICE_NAME)
        .addMethod(
          METHOD_LIST_PRODUCTS,
          asyncServerStreamingCall(
            new MethodHandlers<
              Webshop.ListProductsParams,
              Webshop.Product>(
                serviceImpl, METHODID_LIST_PRODUCTS)))
        .addMethod(
          METHOD_CHECK_AVAILABILITY,
          asyncUnaryCall(
            new MethodHandlers<
              Webshop.ProductId,
              Webshop.Availability>(
                serviceImpl, METHODID_CHECK_AVAILABILITY)))
        .addMethod(
          METHOD_STORE_ORDER_DETAILS,
          asyncUnaryCall(
            new MethodHandlers<
              Webshop.Order,
              Webshop.OrderId>(
                serviceImpl, METHODID_STORE_ORDER_DETAILS)))
        .addMethod(
          METHOD_GET_ORDER_DETAILS,
          asyncUnaryCall(
            new MethodHandlers<
              Webshop.OrderId,
              Webshop.Order>(
                serviceImpl, METHODID_GET_ORDER_DETAILS)))
        .addMethod(
          METHOD_CANCEL_ORDER,
          asyncUnaryCall(
            new MethodHandlers<
              Webshop.OrderId,
              Webshop.Order>(
                serviceImpl, METHODID_CANCEL_ORDER)))
        .addMethod(
          METHOD_CALC_TRANSACTION_COSTS,
          asyncUnaryCall(
            new MethodHandlers<
              Webshop.OrderId,
              Webshop.Costs>(
                serviceImpl, METHODID_CALC_TRANSACTION_COSTS)))
        .addMethod(
          METHOD_CONDUCT_PAYMENT,
          asyncUnaryCall(
            new MethodHandlers<
              Webshop.Payment,
              Webshop.Order>(
                serviceImpl, METHODID_CONDUCT_PAYMENT)))
        .addMethod(
          METHOD_CALC_SHIPMENT_COSTS,
          asyncUnaryCall(
            new MethodHandlers<
              Webshop.OrderId,
              Webshop.Costs>(
                serviceImpl, METHODID_CALC_SHIPMENT_COSTS)))
        .addMethod(
          METHOD_SHIP_PRODUCTS,
          asyncUnaryCall(
            new MethodHandlers<
              Webshop.OrderId,
              Webshop.Order>(
                serviceImpl, METHODID_SHIP_PRODUCTS)))
        .build();
  }
}
