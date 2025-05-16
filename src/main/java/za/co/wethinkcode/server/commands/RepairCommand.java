package za.co.wethinkcode.server.commands;

import com.google.gson.JsonObject;
import za.co.wethinkcode.server.ServerState;
import za.co.wethinkcode.server.models.Robot;
import za.co.wethinkcode.server.models.World;

public class RepairCommand implements Command {
    private final String name;
    private static final World world = ServerState.world;

    public RepairCommand(String name) {
        this.name = name;
    }

    @Override
    public JsonObject execute() {
        Robot robot = world.getRobot(name);
        JsonObject response = new JsonObject();

        if (robot.getHealth() >= 100) {
            response.addProperty("result", "ERROR");
            response.addProperty("message", "Health already full.");
        } else {
            robot.setHealth(robot.getHealth() + 10);
            response.addProperty("result", "OK");
            response.addProperty("message", "Repaired 10 HP.");
        }

        return response;
    }
}

