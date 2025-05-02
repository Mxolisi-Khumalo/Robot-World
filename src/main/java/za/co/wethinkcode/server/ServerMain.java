package za.co.wethinkcode.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) {
        int port = 6000;
        System.out.println("Server started on port " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            String received = in.readLine();
            JsonObject json = JsonParser.parseString(received).getAsJsonObject();
            System.out.println("Received: " + json);

            JsonObject response = new JsonObject();
            response.addProperty("result", "OK");
            response.addProperty("message", "Hello from server!");

            out.write(response.toString());
            out.newLine();
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
