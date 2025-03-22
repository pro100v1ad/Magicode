package utils;

import display.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/*
Класс нужный, без него работать не будет игра, в подробности не вдавайся
 */
public class Utils {

    public static BufferedImage resize(BufferedImage image, int width, int height) {

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        newImage.getGraphics().drawImage(image, 0, 0, width, height, null);

        return newImage;
    }

    public static Integer[][] levelParser(String filePath) {

        Integer[][] result = null;

        try(BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {

            String line = null;
            List<Integer[]> lvlLines = new ArrayList<Integer[]>();
            while ((line = reader.readLine()) != null) {
                lvlLines.add(str2int_arrays(line.split(" ")));
            }
            result = new Integer[lvlLines.size()][lvlLines.get(0).length];

            for(int i = 0; i < lvlLines.size(); i++) {
                result[i] = lvlLines.get(i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static final Integer[] str2int_arrays(String[] sArr) {
        Integer[] result = new Integer[sArr.length];
        for(int i = 0; i < sArr.length; i++) {
            result[i] = Integer.parseInt(sArr[i]);
        }
        return result;
    }

    public static BufferedImage rotate(BufferedImage bimg, double angle) { // Функция из инета, чтобы поворачивать изображения, странно работает
        int w = bimg.getWidth();
        int h = bimg.getHeight();
        BufferedImage rotated = new BufferedImage(w, h, bimg.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.rotate(Math.toRadians(angle), w/2, h/2);
        graphic.drawImage(bimg, null, 0, 0);
        graphic.dispose();
        return rotated;
    }

//    public static void scaleUpdate(GamePanel gp) { // обновление масштаба карты
//        GamePanel.tileSize = (int)(GamePanel.originalTileSize*GamePanel.scale);
//    }

}
