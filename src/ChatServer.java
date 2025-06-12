
import java.io.*;
        import java.net.*;

public class ChatServer {
    public static void main(String[] args) {
        final int PORT = 12345;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for client...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in));

            Thread receiveThread = new Thread(() -> {
                String msgFromClient;
                try {
                    while ((msgFromClient = in.readLine()) != null) {
                        System.out.println("Client: " + msgFromClient);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            });

            receiveThread.start();

            String msgToClient;
            while ((msgToClient = serverInput.readLine()) != null) {
                out.println(msgToClient);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
