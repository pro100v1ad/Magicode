package graphics;

import utils.ResourceLoader;

import java.awt.image.BufferedImage;
/*
Тоже хз пока зач этот класс
 */
public class TextureAtlas {

    BufferedImage image;

    public TextureAtlas(String imageName) {
        image = ResourceLoader.loadImage(imageName);
    }

    public BufferedImage cut(int x, int y, int w, int h) {

        return image.getSubimage(x, y, w, h); // Для того чтобы вырезать часть изображения

    }

}