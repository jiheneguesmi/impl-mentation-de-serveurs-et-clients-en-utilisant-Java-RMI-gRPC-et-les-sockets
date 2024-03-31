import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.example.MessagingServiceGrpc;
import org.example.Messaging;



import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MessagingServer {
    private final int port;
    private final Server server;
    private final Map<String, StringBuilder> userMessages;

    public MessagingServer(int port) {
        this.port = port;
        this.server = ServerBuilder.forPort(port)
                .addService((BindableService) new MessagingServiceImpl())
                .build();
        this.userMessages = new HashMap<>();
    }

    static class MessagingServiceImpl extends MessagingServiceGrpc.MessagingServiceImplBase {
        private final Map<String, StringBuilder> userMessages;

        public MessagingServiceImpl() {
            this.userMessages = new HashMap<>();
        }

        @Override
        public void sendMessage(Messaging.SendMessageRequest request, StreamObserver<Messaging.SendMessageResponse> responseObserver) {
            String recipientId = request.getRecipientId();
            String message = request.getMessage();

            userMessages.computeIfAbsent(recipientId, k -> new StringBuilder()).append(message).append("\n");

            Messaging.SendMessageResponse response = Messaging.SendMessageResponse.newBuilder()
                    .setStatus("Message sent successfully.")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void getMessages(Messaging.GetMessagesRequest request, StreamObserver<Messaging.GetMessagesResponse> responseObserver) {
            String userId = request.getUserId();
            StringBuilder messages = userMessages.getOrDefault(userId, new StringBuilder());

            Messaging.GetMessagesResponse response = Messaging.GetMessagesResponse.newBuilder()
                    .addAllMessages(Arrays.asList(messages.toString().split("\n")))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    public void start() throws IOException {
        server.start();
        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server since JVM is shutting down");
            MessagingServer.this.stop();
            System.err.println("Server shut down");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        MessagingServer server = new MessagingServer(50051);
        server.start();
        server.blockUntilShutdown();
    }
}
