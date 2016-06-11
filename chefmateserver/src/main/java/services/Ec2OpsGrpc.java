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
public class Ec2OpsGrpc {

  private Ec2OpsGrpc() {}

  public static final String SERVICE_NAME = "Ec2Ops";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Chefmate.CreateVmRequest,
      Chefmate.CreateVmResponse> METHOD_CREATE_VM =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "Ec2Ops", "createVm"),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.CreateVmRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.CreateVmResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Chefmate.DestroyVmRequest,
      Chefmate.DestroyVmResponse> METHOD_DESTROY_VM =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "Ec2Ops", "destroyVm"),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.DestroyVmRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.DestroyVmResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Chefmate.InitChefRepoRequest,
      Chefmate.InitChefRepoResponse> METHOD_INIT_CHEF_REPO =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "Ec2Ops", "initChefRepo"),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.InitChefRepoRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.InitChefRepoResponse.getDefaultInstance()));

  public static Ec2OpsStub newStub(io.grpc.Channel channel) {
    return new Ec2OpsStub(channel);
  }

  public static Ec2OpsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new Ec2OpsBlockingStub(channel);
  }

  public static Ec2OpsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new Ec2OpsFutureStub(channel);
  }

  public static interface Ec2Ops {

    public void createVm(Chefmate.CreateVmRequest request,
        io.grpc.stub.StreamObserver<Chefmate.CreateVmResponse> responseObserver);

    public void destroyVm(Chefmate.DestroyVmRequest request,
        io.grpc.stub.StreamObserver<Chefmate.DestroyVmResponse> responseObserver);

    public void initChefRepo(Chefmate.InitChefRepoRequest request,
        io.grpc.stub.StreamObserver<Chefmate.InitChefRepoResponse> responseObserver);
  }

  public static interface Ec2OpsBlockingClient {

    public Chefmate.CreateVmResponse createVm(Chefmate.CreateVmRequest request);

    public Chefmate.DestroyVmResponse destroyVm(Chefmate.DestroyVmRequest request);

    public Chefmate.InitChefRepoResponse initChefRepo(Chefmate.InitChefRepoRequest request);
  }

  public static interface Ec2OpsFutureClient {

    public com.google.common.util.concurrent.ListenableFuture<Chefmate.CreateVmResponse> createVm(
        Chefmate.CreateVmRequest request);

    public com.google.common.util.concurrent.ListenableFuture<Chefmate.DestroyVmResponse> destroyVm(
        Chefmate.DestroyVmRequest request);

    public com.google.common.util.concurrent.ListenableFuture<Chefmate.InitChefRepoResponse> initChefRepo(
        Chefmate.InitChefRepoRequest request);
  }

  public static class Ec2OpsStub extends io.grpc.stub.AbstractStub<Ec2OpsStub>
      implements Ec2Ops {
    private Ec2OpsStub(io.grpc.Channel channel) {
      super(channel);
    }

    private Ec2OpsStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Ec2OpsStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new Ec2OpsStub(channel, callOptions);
    }

    @java.lang.Override
    public void createVm(Chefmate.CreateVmRequest request,
        io.grpc.stub.StreamObserver<Chefmate.CreateVmResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CREATE_VM, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void destroyVm(Chefmate.DestroyVmRequest request,
        io.grpc.stub.StreamObserver<Chefmate.DestroyVmResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DESTROY_VM, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void initChefRepo(Chefmate.InitChefRepoRequest request,
        io.grpc.stub.StreamObserver<Chefmate.InitChefRepoResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_INIT_CHEF_REPO, getCallOptions()), request, responseObserver);
    }
  }

  public static class Ec2OpsBlockingStub extends io.grpc.stub.AbstractStub<Ec2OpsBlockingStub>
      implements Ec2OpsBlockingClient {
    private Ec2OpsBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private Ec2OpsBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Ec2OpsBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new Ec2OpsBlockingStub(channel, callOptions);
    }

    @java.lang.Override
    public Chefmate.CreateVmResponse createVm(Chefmate.CreateVmRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CREATE_VM, getCallOptions(), request);
    }

    @java.lang.Override
    public Chefmate.DestroyVmResponse destroyVm(Chefmate.DestroyVmRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DESTROY_VM, getCallOptions(), request);
    }

    @java.lang.Override
    public Chefmate.InitChefRepoResponse initChefRepo(Chefmate.InitChefRepoRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_INIT_CHEF_REPO, getCallOptions(), request);
    }
  }

  public static class Ec2OpsFutureStub extends io.grpc.stub.AbstractStub<Ec2OpsFutureStub>
      implements Ec2OpsFutureClient {
    private Ec2OpsFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private Ec2OpsFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Ec2OpsFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new Ec2OpsFutureStub(channel, callOptions);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Chefmate.CreateVmResponse> createVm(
        Chefmate.CreateVmRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CREATE_VM, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Chefmate.DestroyVmResponse> destroyVm(
        Chefmate.DestroyVmRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DESTROY_VM, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Chefmate.InitChefRepoResponse> initChefRepo(
        Chefmate.InitChefRepoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_INIT_CHEF_REPO, getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_VM = 0;
  private static final int METHODID_DESTROY_VM = 1;
  private static final int METHODID_INIT_CHEF_REPO = 2;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final Ec2Ops serviceImpl;
    private final int methodId;

    public MethodHandlers(Ec2Ops serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_VM:
          serviceImpl.createVm((Chefmate.CreateVmRequest) request,
              (io.grpc.stub.StreamObserver<Chefmate.CreateVmResponse>) responseObserver);
          break;
        case METHODID_DESTROY_VM:
          serviceImpl.destroyVm((Chefmate.DestroyVmRequest) request,
              (io.grpc.stub.StreamObserver<Chefmate.DestroyVmResponse>) responseObserver);
          break;
        case METHODID_INIT_CHEF_REPO:
          serviceImpl.initChefRepo((Chefmate.InitChefRepoRequest) request,
              (io.grpc.stub.StreamObserver<Chefmate.InitChefRepoResponse>) responseObserver);
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
      final Ec2Ops serviceImpl) {
    return io.grpc.ServerServiceDefinition.builder(SERVICE_NAME)
        .addMethod(
          METHOD_CREATE_VM,
          asyncUnaryCall(
            new MethodHandlers<
              Chefmate.CreateVmRequest,
              Chefmate.CreateVmResponse>(
                serviceImpl, METHODID_CREATE_VM)))
        .addMethod(
          METHOD_DESTROY_VM,
          asyncUnaryCall(
            new MethodHandlers<
              Chefmate.DestroyVmRequest,
              Chefmate.DestroyVmResponse>(
                serviceImpl, METHODID_DESTROY_VM)))
        .addMethod(
          METHOD_INIT_CHEF_REPO,
          asyncUnaryCall(
            new MethodHandlers<
              Chefmate.InitChefRepoRequest,
              Chefmate.InitChefRepoResponse>(
                serviceImpl, METHODID_INIT_CHEF_REPO)))
        .build();
  }
}
