package services;
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
public class GenericOpsGrpc {

  private GenericOpsGrpc() {}

  public static final String SERVICE_NAME = "GenericOps";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Chefmate.ExecuteCookbookRequest,
      Chefmate.ExecuteCookbookResponse> METHOD_EXECUTE_COOKBOOK =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "GenericOps", "executeCookbook"),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.ExecuteCookbookRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.ExecuteCookbookResponse.getDefaultInstance()));

  public static GenericOpsStub newStub(io.grpc.Channel channel) {
    return new GenericOpsStub(channel);
  }

  public static GenericOpsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new GenericOpsBlockingStub(channel);
  }

  public static GenericOpsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new GenericOpsFutureStub(channel);
  }

  public static interface GenericOps {

    public void executeCookbook(Chefmate.ExecuteCookbookRequest request,
        io.grpc.stub.StreamObserver<Chefmate.ExecuteCookbookResponse> responseObserver);
  }

  public static interface GenericOpsBlockingClient {

    public Chefmate.ExecuteCookbookResponse executeCookbook(Chefmate.ExecuteCookbookRequest request);
  }

  public static interface GenericOpsFutureClient {

    public com.google.common.util.concurrent.ListenableFuture<Chefmate.ExecuteCookbookResponse> executeCookbook(
        Chefmate.ExecuteCookbookRequest request);
  }

  public static class GenericOpsStub extends io.grpc.stub.AbstractStub<GenericOpsStub>
      implements GenericOps {
    private GenericOpsStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GenericOpsStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GenericOpsStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GenericOpsStub(channel, callOptions);
    }

    @java.lang.Override
    public void executeCookbook(Chefmate.ExecuteCookbookRequest request,
        io.grpc.stub.StreamObserver<Chefmate.ExecuteCookbookResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_EXECUTE_COOKBOOK, getCallOptions()), request, responseObserver);
    }
  }

  public static class GenericOpsBlockingStub extends io.grpc.stub.AbstractStub<GenericOpsBlockingStub>
      implements GenericOpsBlockingClient {
    private GenericOpsBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GenericOpsBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GenericOpsBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GenericOpsBlockingStub(channel, callOptions);
    }

    @java.lang.Override
    public Chefmate.ExecuteCookbookResponse executeCookbook(Chefmate.ExecuteCookbookRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_EXECUTE_COOKBOOK, getCallOptions(), request);
    }
  }

  public static class GenericOpsFutureStub extends io.grpc.stub.AbstractStub<GenericOpsFutureStub>
      implements GenericOpsFutureClient {
    private GenericOpsFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GenericOpsFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GenericOpsFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GenericOpsFutureStub(channel, callOptions);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Chefmate.ExecuteCookbookResponse> executeCookbook(
        Chefmate.ExecuteCookbookRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_EXECUTE_COOKBOOK, getCallOptions()), request);
    }
  }

  private static final int METHODID_EXECUTE_COOKBOOK = 0;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final GenericOps serviceImpl;
    private final int methodId;

    public MethodHandlers(GenericOps serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_EXECUTE_COOKBOOK:
          serviceImpl.executeCookbook((Chefmate.ExecuteCookbookRequest) request,
              (io.grpc.stub.StreamObserver<Chefmate.ExecuteCookbookResponse>) responseObserver);
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
      final GenericOps serviceImpl) {
    return io.grpc.ServerServiceDefinition.builder(SERVICE_NAME)
        .addMethod(
          METHOD_EXECUTE_COOKBOOK,
          asyncUnaryCall(
            new MethodHandlers<
              Chefmate.ExecuteCookbookRequest,
              Chefmate.ExecuteCookbookResponse>(
                serviceImpl, METHODID_EXECUTE_COOKBOOK)))
        .build();
  }
}
