import java.io.*;
import java.net.*;
import java.util.Set;

class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String userName;
    private static Set<ClientHandler> clients;

    public ClientHandler(Socket socket, Set<ClientHandler> clients) {
        this.socket = socket;
        synchronized (clients) {  // Ensure thread safety
            ClientHandler.clients = clients;
        }
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Enter your username: ");
            userName = in.readLine();

            if (userName == null) {
                closeConnection();
                return;
            }

            synchronized (clients) {
                broadcast(userName + " joined the chat!");
            }

            String message;
            while ((message = in.readLine()) != null) {
                broadcast(userName + ": " + message);
            }
        } catch (IOException e) {
            System.out.println(userName + " disconnected.");
        } finally {
            closeConnection();
        }
    }

    private void broadcast(String message) {
        synchronized (clients) {  // Ensure safe iteration over the client set
            for (ClientHandler client : clients) {
                if (client != this) {
                    client.out.println(message);
                }
            }
        }
    }

    private void closeConnection() {
        try {
            synchronized (clients) {
                clients.remove(this);
            }
            if (socket != null) {
                socket.close();
            }
            broadcast(userName + " left the chat.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
