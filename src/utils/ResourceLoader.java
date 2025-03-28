package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/*
Для загрузки изображений
 */
public class ResourceLoader {

    public static final String PATH = "src/res/";

    public BufferedImage loadImage(String path) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(path);
            if (inputStream == null) {
                System.out.println("Ошибка: изображение не найдено! " + path);
                return null;
            }
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}