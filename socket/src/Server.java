import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            // Création d'un ServerSocket écoutant sur le port 9999
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("Server listening on port 9999...");

            // Boucle d'attente des connexions entrantes des clients
            while (true) {
                // Accepte la connexion entrante du client
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                // Crée un nouveau thread pour gérer le client
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
