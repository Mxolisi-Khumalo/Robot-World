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

    private int ammo = 5;
    private int shield = 50;

    public int getAmmo() { return ammo; }
    public void setAmmo(int ammo) { this.ammo = ammo; }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = Math.max(0, Math.min(100, health)); }

    public int getShield() { return shield; }
    public void setShield(int shield) { this.shield = Math.max(0, Math.min(50, shield)); }

    public boolean isAlive() { return health > 0; }
}