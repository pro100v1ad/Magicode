package graphics;

import utils.ResourceLoader;

import java.awt.image.BufferedImage;



public class Texture {

    private BufferedImage texture = null;
    ResourceLoader rs = new ResourceLoader();
    public void loadTexture(String PATH) {
        texture = rs.loadImage(PATH);
    }
    public BufferedImage getTexture() {
        return texture;
    }


}
