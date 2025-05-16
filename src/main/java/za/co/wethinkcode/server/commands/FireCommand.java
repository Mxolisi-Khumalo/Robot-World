package za.co.wethinkcode.server.commands;

import com.google.gson.JsonObject;
import za.co.wethinkcode.server.ServerState;
import za.co.wethinkcode.server.models.Robot;
import za.co.wethinkcode.server.models.World;

import java.awt.Point;

public class FireCommand implements Command {
    private final String name;
    private static final World world = ServerState.world;
+
    public FireCommand(String name) {
        this.name = name;
    }

    @Override
    public JsonObject execute() {
        Robot shooter = world.getRobot(name);
        JsonObject response = new JsonObject();

        if (shooter.getAmmo() <= 0) {
            response.addProperty("result", "ERROR");
            response.addProperty("message", "No ammo. Please reload.");
            return response;
        }

        Point scan = new Point(shooter.getPosition());
        for (int i = 0; i < 5; i++) {
            scan = world.nextPosition(scan, shooter.getDirection());
            Robot target = world.robotAt(scan);
            if (target != null && !target.getName().equals(name)) {
                int damage = 20;
                int shield = target.getShield();
                int leftover = Math.max(0, damage - shield);
                target.setShield(shield - damage);
                target.setHealth(target.getHealth() - leftover);

                shooter.setAmmo(shooter.getAmmo() - 1);

                response.addProperty("result", "OK");
                response.addProperty("message", "Hit " + target.getName() + " for " + damage + " damage.");
                return response;
            }
        }

        shooter.setAmmo(shooter.getAmmo() - 1);
        response.addProperty("result", "OK");
        response.addProperty("message", "Fired and missed.");
        return response;
    }
}

