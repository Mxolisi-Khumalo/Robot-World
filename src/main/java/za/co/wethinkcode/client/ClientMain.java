package za.co.wethinkcode.client;

import com.google.gson.*;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClientMain {
    private static final Logger logger = LoggerUtil.logger;

    public static void main(String[] args) {
        String serverIp = "localhost";
        int serverPort = 6000;
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket(serverIp, serverPort);
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            System.out.println("Connected to Robot World. Type commands (e.g., 'launch AlphaBot', 'move 3', 'look'). Type 'quit' to exit.");

            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("quit")) break;

                JsonObject jsonCommand = parseCommand(input);
                if (jsonCommand == null) {
                    System.out.println("Invalid command format.");
                    continue;
                }

                logger.info("Sending: " + jsonCommand);
                out.write(jsonCommand.toString());
                out.newLine();
                out.flush();

                String response = in.readLine();
                logger.info("Received: " + response);

                JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
                prettyPrintResponse(jsonResponse);
            }

        } catch (IOException e) {
            logger.severe("Client error: " + e.getMessage());
        }
    }

    private static JsonObject parseCommand(String input) {
        String[] parts = input.split("\\s+");
        if (parts.length < 1) return null;

        JsonObject commandJson = new JsonObject();
        JsonObject args = new JsonObject();

        String command = parts[0].toLowerCase();
        commandJson.addProperty("command", command);

        switch (command) {
            case "launch":
                if (parts.length < 2) return null;
                commandJson.addProperty("robot", parts[1]);
                commandJson.add("arguments", new JsonObject());
                break;
            case "move":
                if (parts.length != 2) return null;
                JsonArray moveArgs = new JsonArray();
                moveArgs.add(Integer.parseInt(parts[1]));
                commandJson.addProperty("robot", "AlphaBot");
                commandJson.add("arguments", moveArgs);
                break;
            case "turn":
                if (parts.length != 2) return null;
                JsonArray turnArgs = new JsonArray();
                turnArgs.add(parts[1].toLowerCase());
                commandJson.addProperty("robot", "AlphaBot");
                commandJson.add("arguments", turnArgs);
                break;
            case "look":
            case "repair":
            case "reload":
            case "fire":
                commandJson.addProperty("robot", "AlphaBot");
                commandJson.add("arguments", new JsonObject());
                break;
            default:
                return null;
        }

        return commandJson;
    }

    private static void prettyPrintResponse(JsonObject json) {
        String result = json.get("result").getAsString();
        System.out.println("Server: " + result);
        if (json.has("message")) {
            System.out.println("Message: " + json.get("message").getAsString());
        }
        if (json.has("state")) {
            System.out.println("State: " + json.get("state").toString());
        }
    }
}
