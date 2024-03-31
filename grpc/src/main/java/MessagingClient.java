import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.Messaging;
import org.example.MessagingServiceGrpc;

import java.util.concurrent.TimeUnit;
import java.util.Scanner;

public class MessagingClient {
    private final ManagedChannel channel;
    private final MessagingServiceGrpc.MessagingServiceBlockingStub blockingStub;

    public MessagingClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = MessagingServiceGrpc.newBlockingStub(channel);
    }

    public void sendMessage(String senderId, String recipientId, String message) {
        Messaging.SendMessageRequest request = Messaging.SendMessageRequest.newBuilder()
                .setSenderId(senderId)
                .setRecipientId(recipientId)
                .setMessage(message)
                .build();

        blockingStub.sendMessage(request);
        System.out.println("Messages sent successfully to " + recipientId + ": " + message);
    }

    public void getMessages(String userId) {
        Messaging.GetMessagesRequest request = Messaging.GetMessagesRequest.newBuilder()
                .setUserId(userId)
                .build();

        Messaging.GetMessagesResponse response = blockingStub.getMessages(request);
        System.out.println("Messages for user " + userId + ": " + response.getMessagesList());
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        MessagingClient client = new MessagingClient("localhost", 50051);
        Scanner scanner = new Scanner(System.in);

        // Demander à l'utilisateur de saisir le message à envoyer
        System.out.print("Enter the message to send: ");
        String message = scanner.nextLine();

        // Envoi du message
        client.sendMessage("user1", "user2", message);

        // Récupération des messages pour un utilisateur
        client.getMessages("user2");

        client.shutdown();
    }
}
