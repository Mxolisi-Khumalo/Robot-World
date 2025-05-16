package za.co.wethinkcode.server.commands;

import com.google.gson.JsonObject;
import za.co.wethinkcode.server.ServerState;
import za.co.wethinkcode.server.models.Robot;
import za.co.wethinkcode.server.models.World;

public class ReloadCommand implements Command {
    private final String name;
    private static final World world = ServerState.world;

    public ReloadCommand(String name) {
        this.name = name;
    }

    @Override
    public JsonObject execute() {
        Robot robot = world.getRobot(name);
        JsonObject response = new JsonObject();

        if (robot.getAmmo() >= 5) {
            response.addProperty("result", "ERROR");
            response.addProperty("message", "Ammo already full.");
        } else {
            robot.setAmmo(5);
            response.addProperty("result", "OK");
            response.addProperty("message", "Ammo reloaded.");
        }

        return response;
    }
}

