package graphics;

import java.awt.image.BufferedImage;
/*
Полезный класс, помогает вырезать спрайты из одного изображения
Но нужно настроить, сейчас работает криво
 */
public class SpriteSheet {

    private BufferedImage sheet;
    private int spriteCount; // Количество танков
    private int scale; // их размер
    private int spriteInWidth; // Ширина вырез изобр

    public  SpriteSheet(BufferedImage sheet, int spriteCount, int scale) {
        this.sheet = sheet;
        this.spriteCount = spriteCount;
        this.scale = scale;

        this.spriteInWidth = sheet.getWidth() / scale;

    }

    public BufferedImage getSprite(int index, double scaleX, double scaleY) {

//        index = index % spriteCount;

        int x = (int)(index % spriteInWidth * scale*scaleX);
        int y = (int)(index / spriteInWidth * scale*scaleY);

        return sheet.getSubimage(x, y, scale, scale*2);
    }


}

