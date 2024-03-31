import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            // Connexion au serveur sur le port 9999
            Socket socket = new Socket("localhost", 9999);

            // Flux de lecture pour lire les entrées utilisateur depuis la console
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            // Flux de lecture pour lire les messages du serveur
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Flux d'écriture pour envoyer les messages au serveur
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Connected to server. Start typing messages:");

            // Boucle pour envoyer les messages au serveur
            String message;
            while ((message = input.readLine()) != null) {
                // Envoie le message au serveur
                output.println(message);
                // Attend une réponse du serveur et l'affiche
                String serverMessage = serverInput.readLine();
                System.out.println("Server: " + serverMessage);
            }

            // Ferme la connexion
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
