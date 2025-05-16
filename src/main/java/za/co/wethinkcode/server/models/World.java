package za.co.wethinkcode.server.models;

import java.awt.Point;
import java.util.*;

public class World {
    private final int width;
    private final int height;
    private final Set<Point> obstacles = new HashSet<>();
    private final Map<String, Robot> robots = new HashMap<>();

    public World(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean addRobot(Robot robot) {
        Point pos = new Point(0, 0);
        if (isBlocked(pos)) return false;
        robot.setPosition(pos);
        robots.put(robot.getName(), robot);
        return true;
    }

    public boolean moveRobot(String name, int steps) {
        Robot robot = robots.get(name);
        if (robot == null) return false;

        Point pos = new Point(robot.getPosition());
        for (int i = 0; i < steps; i++) {
            pos = nextPosition(pos, robot.getDirection());
            if (!inBounds(pos) || isBlocked(pos)) return false;
        }

        robot.setPosition(pos);
        return true;
    }

    public void addObstacle(int x, int y) {
        obstacles.add(new Point(x, y));
    }

    private boolean isBlocked(Point p) {
        return obstacles.contains(p) || robots.values().stream()
                .anyMatch(r -> r.getPosition().equals(p));
    }

    private boolean inBounds(Point p) {
        return p.x >= 0 && p.x < width && p.y >= 0 && p.y < height;
    }

    public Point nextPosition(Point pos, String dir) {
        return switch (dir.toUpperCase()) {
            case "NORTH" -> new Point(pos.x, pos.y + 1);
            case "SOUTH" -> new Point(pos.x, pos.y - 1);
            case "EAST" -> new Point(pos.x + 1, pos.y);
            case "WEST" -> new Point(pos.x - 1, pos.y);
            default -> pos;
        };
    }

    public Robot getRobot(String name) {
        return robots.get(name);
    }

    public Robot robotAt(Point position) {
        return robots.values().stream()
                .filter(r -> r.getPosition().equals(position))
                .findFirst()
                .orElse(null);
    }
}

