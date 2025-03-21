import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private static final int PORT = 12345;
    private static Set<ClientHandler> clients = new HashSet<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Chat server started on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket.getInetAddress());

                ClientHandler client = new ClientHandler(socket, clients);
                clients.add(client);

                // Run each client handler in a separate thread
                new Thread(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
