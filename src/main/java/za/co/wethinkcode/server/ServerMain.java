package za.co.wethinkcode.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import za.co.wethinkcode.server.commands.Command;
import za.co.wethinkcode.server.commands.CommandFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerMain {
    private static final Logger logger = LoggerUtil.logger;

    public static void main(String[] args) {
        int port = 6000;
        logger.info("Server starting on port " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("New client connected: " + clientSocket.getInetAddress());
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            logger.severe("Server error: " + e.getMessage());
        }
    }


    private static void handleClient(Socket socket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            String received = in.readLine();
            logger.info("From client [" + socket.getInetAddress() + "]: " + received);

            JsonObject json = JsonParser.parseString(received).getAsJsonObject();
            Command command = CommandFactory.fromJson(json);
            JsonObject response = command.execute();

            logger.info("To client [" + socket.getInetAddress() + "]: " + response);
            out.write(response.toString());
            out.newLine();
            out.flush();

        } catch (IOException e) {
            logger.severe("Client handling error: " + e.getMessage());
        }
    }
}