package Entity;

import java.awt.*;

public class Entity { // Родительский класс для всех сущностей

    public double worldX, worldY;
    public double speed;

    public Rectangle solidArea;
    public boolean collisionOn = false;
}
