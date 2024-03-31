import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            // Flux de lecture pour lire les messages envoyés par le client
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // Flux d'écriture pour envoyer des messages au client
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            // Boucle pour lire les messages du client
            String message;
            while ((message = input.readLine()) != null) {
                // Affiche le message reçu du client
                System.out.println("Received message from client: " + message);
                // Envoie une confirmation au client
                output.println("Message received by server: " + message);
            }

            // Ferme la connexion avec le client
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
