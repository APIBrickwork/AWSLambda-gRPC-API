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
  public static final io.grpc.MethodDescriptor<Chefmate.DeployWPAppRequest,
      Chefmate.DeployWPAppResponse> METHOD_DEPLOY_WPAPP =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "WordPressOps", "deployWPApp"),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.DeployWPAppRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.DeployWPAppResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Chefmate.DeployDBRequest,
      Chefmate.DeployDBResponse> METHOD_DEPLOY_DB =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "WordPressOps", "deployDB"),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.DeployDBRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.DeployDBResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Chefmate.BackupDBRequest,
      Chefmate.BackupDBResponse> METHOD_BACKUP_DB =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "WordPressOps", "backupDB"),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.BackupDBRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.BackupDBResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi
  public static final io.grpc.MethodDescriptor<Chefmate.RestoreDBRequest,
      Chefmate.RestoreDBResponse> METHOD_RESTORE_DB =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "WordPressOps", "restoreDB"),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.RestoreDBRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(Chefmate.RestoreDBResponse.getDefaultInstance()));

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

    public void deployWPApp(Chefmate.DeployWPAppRequest request,
        io.grpc.stub.StreamObserver<Chefmate.DeployWPAppResponse> responseObserver);

    public void deployDB(Chefmate.DeployDBRequest request,
        io.grpc.stub.StreamObserver<Chefmate.DeployDBResponse> responseObserver);

    public void backupDB(Chefmate.BackupDBRequest request,
        io.grpc.stub.StreamObserver<Chefmate.BackupDBResponse> responseObserver);

    public void restoreDB(Chefmate.RestoreDBRequest request,
        io.grpc.stub.StreamObserver<Chefmate.RestoreDBResponse> responseObserver);
  }

  public static interface WordPressOpsBlockingClient {

    public Chefmate.DeployWPAppResponse deployWPApp(Chefmate.DeployWPAppRequest request);

    public Chefmate.DeployDBResponse deployDB(Chefmate.DeployDBRequest request);

    public Chefmate.BackupDBResponse backupDB(Chefmate.BackupDBRequest request);

    public Chefmate.RestoreDBResponse restoreDB(Chefmate.RestoreDBRequest request);
  }

  public static interface WordPressOpsFutureClient {

    public com.google.common.util.concurrent.ListenableFuture<Chefmate.DeployWPAppResponse> deployWPApp(
        Chefmate.DeployWPAppRequest request);

    public com.google.common.util.concurrent.ListenableFuture<Chefmate.DeployDBResponse> deployDB(
        Chefmate.DeployDBRequest request);

    public com.google.common.util.concurrent.ListenableFuture<Chefmate.BackupDBResponse> backupDB(
        Chefmate.BackupDBRequest request);

    public com.google.common.util.concurrent.ListenableFuture<Chefmate.RestoreDBResponse> restoreDB(
        Chefmate.RestoreDBRequest request);
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
    public void deployWPApp(Chefmate.DeployWPAppRequest request,
        io.grpc.stub.StreamObserver<Chefmate.DeployWPAppResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DEPLOY_WPAPP, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void deployDB(Chefmate.DeployDBRequest request,
        io.grpc.stub.StreamObserver<Chefmate.DeployDBResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DEPLOY_DB, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void backupDB(Chefmate.BackupDBRequest request,
        io.grpc.stub.StreamObserver<Chefmate.BackupDBResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_BACKUP_DB, getCallOptions()), request, responseObserver);
    }

    @java.lang.Override
    public void restoreDB(Chefmate.RestoreDBRequest request,
        io.grpc.stub.StreamObserver<Chefmate.RestoreDBResponse> responseObserver) {
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
    public Chefmate.DeployWPAppResponse deployWPApp(Chefmate.DeployWPAppRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DEPLOY_WPAPP, getCallOptions(), request);
    }

    @java.lang.Override
    public Chefmate.DeployDBResponse deployDB(Chefmate.DeployDBRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DEPLOY_DB, getCallOptions(), request);
    }

    @java.lang.Override
    public Chefmate.BackupDBResponse backupDB(Chefmate.BackupDBRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_BACKUP_DB, getCallOptions(), request);
    }

    @java.lang.Override
    public Chefmate.RestoreDBResponse restoreDB(Chefmate.RestoreDBRequest request) {
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
    public com.google.common.util.concurrent.ListenableFuture<Chefmate.DeployWPAppResponse> deployWPApp(
        Chefmate.DeployWPAppRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DEPLOY_WPAPP, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Chefmate.DeployDBResponse> deployDB(
        Chefmate.DeployDBRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DEPLOY_DB, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Chefmate.BackupDBResponse> backupDB(
        Chefmate.BackupDBRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_BACKUP_DB, getCallOptions()), request);
    }

    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<Chefmate.RestoreDBResponse> restoreDB(
        Chefmate.RestoreDBRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_RESTORE_DB, getCallOptions()), request);
    }
  }

  private static final int METHODID_DEPLOY_WPAPP = 0;
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
        case METHODID_DEPLOY_WPAPP:
          serviceImpl.deployWPApp((Chefmate.DeployWPAppRequest) request,
              (io.grpc.stub.StreamObserver<Chefmate.DeployWPAppResponse>) responseObserver);
          break;
        case METHODID_DEPLOY_DB:
          serviceImpl.deployDB((Chefmate.DeployDBRequest) request,
              (io.grpc.stub.StreamObserver<Chefmate.DeployDBResponse>) responseObserver);
          break;
        case METHODID_BACKUP_DB:
          serviceImpl.backupDB((Chefmate.BackupDBRequest) request,
              (io.grpc.stub.StreamObserver<Chefmate.BackupDBResponse>) responseObserver);
          break;
        case METHODID_RESTORE_DB:
          serviceImpl.restoreDB((Chefmate.RestoreDBRequest) request,
              (io.grpc.stub.StreamObserver<Chefmate.RestoreDBResponse>) responseObserver);
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
          METHOD_DEPLOY_WPAPP,
          asyncUnaryCall(
            new MethodHandlers<
              Chefmate.DeployWPAppRequest,
              Chefmate.DeployWPAppResponse>(
                serviceImpl, METHODID_DEPLOY_WPAPP)))
        .addMethod(
          METHOD_DEPLOY_DB,
          asyncUnaryCall(
            new MethodHandlers<
              Chefmate.DeployDBRequest,
              Chefmate.DeployDBResponse>(
                serviceImpl, METHODID_DEPLOY_DB)))
        .addMethod(
          METHOD_BACKUP_DB,
          asyncUnaryCall(
            new MethodHandlers<
              Chefmate.BackupDBRequest,
              Chefmate.BackupDBResponse>(
                serviceImpl, METHODID_BACKUP_DB)))
        .addMethod(
          METHOD_RESTORE_DB,
          asyncUnaryCall(
            new MethodHandlers<
              Chefmate.RestoreDBRequest,
              Chefmate.RestoreDBResponse>(
                serviceImpl, METHODID_RESTORE_DB)))
        .build();
  }
}
