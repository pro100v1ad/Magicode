package graphics;

import java.awt.image.BufferedImage;

import static utils.ResourceLoader.loadImage;

public class Texture {

    private BufferedImage texture = null;
    private boolean collision = false;

    public void loadTexture(String PATH) {
        texture = loadImage(PATH);
    }
    public BufferedImage getTexture() {
        return texture;
    }
    public void setCollision(boolean collision) {
        this.collision = collision;
    }
    public boolean getCollision() {
        return collision;
    }

}
