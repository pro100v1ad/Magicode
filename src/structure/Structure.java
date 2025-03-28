package structure;

import Tile.Tiles;

import java.awt.*;

public abstract class Structure {

    protected Tiles[] tiles;
    protected int worldX, worldY;
    protected String direction;
    protected boolean isBreak;
    protected String name;

    protected void loadTextures() {

    }

    public void draw(Graphics2D g) {

    }

    public String getName() {
        return name;
    }
    public int getWorldX() {
        return worldX;
    }
    public int getWorldY() {
        return worldY;
    }
    public boolean getBreak() {
        return isBreak;
    }
    public String getDirection() {
        return direction;
    }
}
