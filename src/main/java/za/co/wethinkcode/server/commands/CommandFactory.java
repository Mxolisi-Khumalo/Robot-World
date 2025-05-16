package za.co.wethinkcode.server.commands;

import com.google.gson.JsonObject;

public class CommandFactory {
    public static Command fromJson(JsonObject json) {
        String commandName = json.get("command").getAsString();
        JsonObject args = json.getAsJsonObject("arguments");

        switch (commandName.toLowerCase()) {
            case "launch":
                return new LaunchCommand(json.get("robot").getAsString());
            // Add future cases: move, look, turn, etc.
            default:
                return () -> {
                    JsonObject error = new JsonObject();
                    error.addProperty("result", "ERROR");
                    error.addProperty("message", "Unknown command: " + commandName);
                    return error;
                };
        }
    }
}

