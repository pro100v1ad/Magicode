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
}
