package graphics;

import java.awt.image.BufferedImage;

import static utils.ResourceLoader.loadImage;

public class Texture {

    private BufferedImage texture = null;

    public void loadTexture(String PATH) {
        texture = loadImage(PATH);
    }
    public BufferedImage getTexture() {
        return texture;
    }


}
