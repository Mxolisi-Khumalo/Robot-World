package za.co.wethinkcode.client;

import com.google.gson.JsonObject;

import java.io.*;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) {
        String serverIp = "localhost";
        int serverPort = 6000;

        try (Socket socket = new Socket(serverIp, serverPort);
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            JsonObject command = new JsonObject();
            command.addProperty("command", "test");
            command.addProperty("robot", "AlphaBot");

            out.write(command.toString());
            out.newLine();
            out.flush();

            String response = in.readLine();
            System.out.println("Server response: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
