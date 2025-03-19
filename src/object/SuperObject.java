package object;

import display.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;

    public void draw(Graphics2D g, GamePanel gp) {

        int screenX = (int) (worldX - gp.player.worldX + gp.player.screenX);
        int screenY = (int) (worldY - gp.player.worldY + gp.player.screenY);

        if(worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - GamePanel.tileSize*3 < gp.player.worldX + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - GamePanel.tileSize*4 < gp.player.worldY + gp.player.screenY)
        {
            try {
                g.drawImage(image, screenX, screenY, GamePanel.tileSize*2, GamePanel.tileSize*2, null);

            } catch (Exception e) {
                System.out.println("SuperObject: Не удалось загрузить  на позицию " + screenX + " и " + screenY);
            }
        }

    }

}
