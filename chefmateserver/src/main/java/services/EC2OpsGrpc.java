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
public class EC2OpsGrpc {

  private EC2OpsGrpc() {}

  public static final String SERVICE_NAME = "EC2Ops";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Chefmate.CreateVMRequest,
      Chefmate.CreateVMResponse> METHOD_CREATE_VM =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "EC2Ops", "createVM"),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.CreateVMRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.CreateVMResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Chefmate.DestroyVMRequest,
      Chefmate.DestroyVMResponse> METHOD_DESTROY_VM =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "EC2Ops", "destroyVM"),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.DestroyVMRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.DestroyVMResponse.getDefaultInstance()));

  public static EC2OpsStub newStub(io.grpc.Channel channel) {
    return new EC2OpsStub(channel);
  }

  public static EC2OpsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new EC2OpsBlockingStub(channel);
  }

  public static EC2OpsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new EC2OpsFutureStub(channel);
  }

  public static interface EC2Ops {

    public void createVM(Chefmate.CreateVMRequest request,
        io.grpc.stub.StreamObserver<Chefmate.CreateVMResponse> responseObserver);

    public void destroyVM(Chefmate.DestroyVMRequest request,
        io.grpc.stub.StreamObserver<Chefmate.DestroyVMResponse> responseObserver);
  }

  public static interface EC2OpsBlockingClient {

    public Chefmate.CreateVMResponse createVM(Chefmate.CreateVMRequest request);

    public Chefmate.DestroyVMResponse destroyVM(Chefmate.DestroyVMRequest request);
  }

  public static interface EC2OpsFutureClient {

    public com.google.common.util.concurrent.ListenableFuture<Chefmate.CreateVMResponse> createVM(
        Chefmate.CreateVMRequest request);

    public com.google.common.util.concurrent.ListenableFuture<Chefmate.DestroyVMResponse> destroyVM(
        Chefmate.DestroyVMRequest request);
  }

  public static class EC2OpsStub extends io.grpc.stub.AbstractStub<EC2OpsStub>
      implements EC2Ops {
    private EC2OpsStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EC2OpsStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EC2OpsStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EC2OpsStub(channel, callOptions);
    }

    @java.lang.Override
    public void createVM(Chefmate.CreateVMRequest request,
        io.grpc.stub.StreamObserver<Chefmate.CreateVMResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CREATE_VM, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void destroyVM(Chefmate.DestroyVMRequest request,
        io.grpc.stub.StreamObserver<Chefmate.DestroyVMResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DESTROY_VM, getCallOptions()), request, responseObserver);
    }
  }

  public static class EC2OpsBlockingStub extends io.grpc.stub.AbstractStub<EC2OpsBlockingStub>
      implements EC2OpsBlockingClient {
    private EC2OpsBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EC2OpsBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EC2OpsBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EC2OpsBlockingStub(channel, callOptions);
    }

    @java.lang.Override
    public Chefmate.CreateVMResponse createVM(Chefmate.CreateVMRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CREATE_VM, getCallOptions(), request);
    }

    @java.lang.Override
    public Chefmate.DestroyVMResponse destroyVM(Chefmate.DestroyVMRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DESTROY_VM, getCallOptions(), request);
    }
  }

  public static class EC2OpsFutureStub extends io.grpc.stub.AbstractStub<EC2OpsFutureStub>
      implements EC2OpsFutureClient {
    private EC2OpsFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EC2OpsFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EC2OpsFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EC2OpsFutureStub(channel, callOptions);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Chefmate.CreateVMResponse> createVM(
        Chefmate.CreateVMRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CREATE_VM, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Chefmate.DestroyVMResponse> destroyVM(
        Chefmate.DestroyVMRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DESTROY_VM, getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_VM = 0;
  private static final int METHODID_DESTROY_VM = 1;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final EC2Ops serviceImpl;
    private final int methodId;

    public MethodHandlers(EC2Ops serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_VM:
          serviceImpl.createVM((Chefmate.CreateVMRequest) request,
              (io.grpc.stub.StreamObserver<Chefmate.CreateVMResponse>) responseObserver);
          break;
        case METHODID_DESTROY_VM:
          serviceImpl.destroyVM((Chefmate.DestroyVMRequest) request,
              (io.grpc.stub.StreamObserver<Chefmate.DestroyVMResponse>) responseObserver);
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
      final EC2Ops serviceImpl) {
    return io.grpc.ServerServiceDefinition.builder(SERVICE_NAME)
        .addMethod(
          METHOD_CREATE_VM,
          asyncUnaryCall(
            new MethodHandlers<
              Chefmate.CreateVMRequest,
              Chefmate.CreateVMResponse>(
                serviceImpl, METHODID_CREATE_VM)))
        .addMethod(
          METHOD_DESTROY_VM,
          asyncUnaryCall(
            new MethodHandlers<
              Chefmate.DestroyVMRequest,
              Chefmate.DestroyVMResponse>(
                serviceImpl, METHODID_DESTROY_VM)))
        .build();
  }
}
