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
public class WordPressOpsGrpc {

  private WordPressOpsGrpc() {}

  public static final String SERVICE_NAME = "WordPressOps";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Chefmate.DeployWpAppRequest,
      Chefmate.DeployWpAppResponse> METHOD_DEPLOY_WP_APP =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "WordPressOps", "deployWpApp"),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.DeployWpAppRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.DeployWpAppResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Chefmate.DeployDbRequest,
      Chefmate.DeployDbResponse> METHOD_DEPLOY_DB =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "WordPressOps", "deployDb"),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.DeployDbRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.DeployDbResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Chefmate.BackupDbRequest,
      Chefmate.BackupDbResponse> METHOD_BACKUP_DB =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "WordPressOps", "backupDb"),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.BackupDbRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.BackupDbResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Chefmate.RestoreDbRequest,
      Chefmate.RestoreDbResponse> METHOD_RESTORE_DB =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "WordPressOps", "restoreDb"),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.RestoreDbRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.RestoreDbResponse.getDefaultInstance()));

  public static WordPressOpsStub newStub(io.grpc.Channel channel) {
    return new WordPressOpsStub(channel);
  }

  public static WordPressOpsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new WordPressOpsBlockingStub(channel);
  }

  public static WordPressOpsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new WordPressOpsFutureStub(channel);
  }

  public static interface WordPressOps {

    public void deployWpApp(Chefmate.DeployWpAppRequest request,
        io.grpc.stub.StreamObserver<Chefmate.DeployWpAppResponse> responseObserver);

    public void deployDb(Chefmate.DeployDbRequest request,
        io.grpc.stub.StreamObserver<Chefmate.DeployDbResponse> responseObserver);

    public void backupDb(Chefmate.BackupDbRequest request,
        io.grpc.stub.StreamObserver<Chefmate.BackupDbResponse> responseObserver);

    public void restoreDb(Chefmate.RestoreDbRequest request,
        io.grpc.stub.StreamObserver<Chefmate.RestoreDbResponse> responseObserver);
  }

  public static interface WordPressOpsBlockingClient {

    public Chefmate.DeployWpAppResponse deployWpApp(Chefmate.DeployWpAppRequest request);

    public Chefmate.DeployDbResponse deployDb(Chefmate.DeployDbRequest request);

    public Chefmate.BackupDbResponse backupDb(Chefmate.BackupDbRequest request);

    public Chefmate.RestoreDbResponse restoreDb(Chefmate.RestoreDbRequest request);
  }

  public static interface WordPressOpsFutureClient {

    public com.google.common.util.concurrent.ListenableFuture<Chefmate.DeployWpAppResponse> deployWpApp(
        Chefmate.DeployWpAppRequest request);

    public com.google.common.util.concurrent.ListenableFuture<Chefmate.DeployDbResponse> deployDb(
        Chefmate.DeployDbRequest request);

    public com.google.common.util.concurrent.ListenableFuture<Chefmate.BackupDbResponse> backupDb(
        Chefmate.BackupDbRequest request);

    public com.google.common.util.concurrent.ListenableFuture<Chefmate.RestoreDbResponse> restoreDb(
        Chefmate.RestoreDbRequest request);
  }

  public static class WordPressOpsStub extends io.grpc.stub.AbstractStub<WordPressOpsStub>
      implements WordPressOps {
    private WordPressOpsStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WordPressOpsStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WordPressOpsStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WordPressOpsStub(channel, callOptions);
    }

    @java.lang.Override
    public void deployWpApp(Chefmate.DeployWpAppRequest request,
        io.grpc.stub.StreamObserver<Chefmate.DeployWpAppResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DEPLOY_WP_APP, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void deployDb(Chefmate.DeployDbRequest request,
        io.grpc.stub.StreamObserver<Chefmate.DeployDbResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DEPLOY_DB, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void backupDb(Chefmate.BackupDbRequest request,
        io.grpc.stub.StreamObserver<Chefmate.BackupDbResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_BACKUP_DB, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void restoreDb(Chefmate.RestoreDbRequest request,
        io.grpc.stub.StreamObserver<Chefmate.RestoreDbResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_RESTORE_DB, getCallOptions()), request, responseObserver);
    }
  }

  public static class WordPressOpsBlockingStub extends io.grpc.stub.AbstractStub<WordPressOpsBlockingStub>
      implements WordPressOpsBlockingClient {
    private WordPressOpsBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WordPressOpsBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WordPressOpsBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WordPressOpsBlockingStub(channel, callOptions);
    }

    @java.lang.Override
    public Chefmate.DeployWpAppResponse deployWpApp(Chefmate.DeployWpAppRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DEPLOY_WP_APP, getCallOptions(), request);
    }

    @java.lang.Override
    public Chefmate.DeployDbResponse deployDb(Chefmate.DeployDbRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DEPLOY_DB, getCallOptions(), request);
    }

    @java.lang.Override
    public Chefmate.BackupDbResponse backupDb(Chefmate.BackupDbRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_BACKUP_DB, getCallOptions(), request);
    }

    @java.lang.Override
    public Chefmate.RestoreDbResponse restoreDb(Chefmate.RestoreDbRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_RESTORE_DB, getCallOptions(), request);
    }
  }

  public static class WordPressOpsFutureStub extends io.grpc.stub.AbstractStub<WordPressOpsFutureStub>
      implements WordPressOpsFutureClient {
    private WordPressOpsFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private WordPressOpsFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WordPressOpsFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new WordPressOpsFutureStub(channel, callOptions);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Chefmate.DeployWpAppResponse> deployWpApp(
        Chefmate.DeployWpAppRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DEPLOY_WP_APP, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Chefmate.DeployDbResponse> deployDb(
        Chefmate.DeployDbRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DEPLOY_DB, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Chefmate.BackupDbResponse> backupDb(
        Chefmate.BackupDbRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_BACKUP_DB, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Chefmate.RestoreDbResponse> restoreDb(
        Chefmate.RestoreDbRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_RESTORE_DB, getCallOptions()), request);
    }
  }

  private static final int METHODID_DEPLOY_WP_APP = 0;
  private static final int METHODID_DEPLOY_DB = 1;
  private static final int METHODID_BACKUP_DB = 2;
  private static final int METHODID_RESTORE_DB = 3;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WordPressOps serviceImpl;
    private final int methodId;

    public MethodHandlers(WordPressOps serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_DEPLOY_WP_APP:
          serviceImpl.deployWpApp((Chefmate.DeployWpAppRequest) request,
              (io.grpc.stub.StreamObserver<Chefmate.DeployWpAppResponse>) responseObserver);
          break;
        case METHODID_DEPLOY_DB:
          serviceImpl.deployDb((Chefmate.DeployDbRequest) request,
              (io.grpc.stub.StreamObserver<Chefmate.DeployDbResponse>) responseObserver);
          break;
        case METHODID_BACKUP_DB:
          serviceImpl.backupDb((Chefmate.BackupDbRequest) request,
              (io.grpc.stub.StreamObserver<Chefmate.BackupDbResponse>) responseObserver);
          break;
        case METHODID_RESTORE_DB:
          serviceImpl.restoreDb((Chefmate.RestoreDbRequest) request,
              (io.grpc.stub.StreamObserver<Chefmate.RestoreDbResponse>) responseObserver);
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
      final WordPressOps serviceImpl) {
    return io.grpc.ServerServiceDefinition.builder(SERVICE_NAME)
        .addMethod(
          METHOD_DEPLOY_WP_APP,
          asyncUnaryCall(
            new MethodHandlers<
              Chefmate.DeployWpAppRequest,
              Chefmate.DeployWpAppResponse>(
                serviceImpl, METHODID_DEPLOY_WP_APP)))
        .addMethod(
          METHOD_DEPLOY_DB,
          asyncUnaryCall(
            new MethodHandlers<
              Chefmate.DeployDbRequest,
              Chefmate.DeployDbResponse>(
                serviceImpl, METHODID_DEPLOY_DB)))
        .addMethod(
          METHOD_BACKUP_DB,
          asyncUnaryCall(
            new MethodHandlers<
              Chefmate.BackupDbRequest,
              Chefmate.BackupDbResponse>(
                serviceImpl, METHODID_BACKUP_DB)))
        .addMethod(
          METHOD_RESTORE_DB,
          asyncUnaryCall(
            new MethodHandlers<
              Chefmate.RestoreDbRequest,
              Chefmate.RestoreDbResponse>(
                serviceImpl, METHODID_RESTORE_DB)))
        .build();
  }
}
