package za.co.wethinkcode.server.models;

import java.awt.Point;

public class Robot {
    private final String name;
    private Point position = new Point(0, 0);
    private String direction = "NORTH";
    private int health = 100;

    public Robot(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public Point getPosition() { return position; }
    public void setPosition(Point p) { this.position = p; }
    public String getDirection() { return direction; }
    public void setDirection(String dir) { this.direction = dir; }
}