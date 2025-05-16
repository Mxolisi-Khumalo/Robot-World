package za.co.wethinkcode.server.commands;

import com.google.gson.JsonObject;
import za.co.wethinkcode.server.ServerState;
import za.co.wethinkcode.server.models.*;

public class LaunchCommand implements Command {
    private final String name;
    private static final World world = ServerState.world;

    public LaunchCommand(String name) {
        this.name = name;
    }

    @Override
    public JsonObject execute() {
        Robot robot = new Robot(name);
        boolean placed = world.addRobot(robot);
        JsonObject response = new JsonObject();

        if (placed) {
            response.addProperty("result", "OK");
            response.addProperty("message", "Robot " + name + " launched.");
            JsonObject state = new JsonObject();
            state.addProperty("position", robot.getPosition().toString());
            state.addProperty("status", "ACTIVE");
            response.add("state", state);
        } else {
            response.addProperty("result", "ERROR");
            response.addProperty("message", "Launch position blocked.");
        }

        return response;
    }
}


