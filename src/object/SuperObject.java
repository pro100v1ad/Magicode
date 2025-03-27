package object;

import display.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    private static int ID_COUNTER = 1;

    protected BufferedImage image;
    protected String name;
    public int worldX, worldY;
    public int collisionWidth;
    public int collisionHeight;

    protected int interactionCenterX;
    protected int interactionCenterY;
    protected int interactionRadius;
    protected int interactionCode;

    public SuperObject() {
        this.interactionCode = ID_COUNTER++;
    }

    public String getName() {
        return name;

    }

    public int getInteractionCenterX() {
        return interactionCenterX;
    }

    public int getInteractionCenterY() {
        return interactionCenterY;
    }


    public int getInteractionRadius() {
        return interactionRadius;
    }

    public int getInteractionCode() {
        return interactionCode;
    }

    public void draw(Graphics2D g) {}

}
