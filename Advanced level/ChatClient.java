import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println(in.readLine());  // "Enter your username:"
            out.println(userInput.readLine()); // Send username

            // Thread to read messages from the server
            new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from chat.");
                }
            }).start();

            // Sending messages to the server
            String input;
            while ((input = userInput.readLine()) != null) {
                out.println(input);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
