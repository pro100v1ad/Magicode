package object;

import display.GamePanel;
import utils.ResourceLoader;

import java.awt.*;


public class OBJ_Book extends SuperObject {

    ResourceLoader rs = new ResourceLoader();
    GamePanel gp;

    public OBJ_Book(GamePanel gp, int i, int x, int y, int w, int h) {
        super();
        this.gp = gp;
        name = "Book";
        this.worldX = x;
        this.worldY = y;
        this.collisionWidth = w;
        this.collisionHeight = h;

        this.interactionCenterX = this.worldX + this.collisionWidth/2;
        this.interactionCenterY = this.worldY + this.collisionHeight/2;
        this.interactionRadius = GamePanel.tileSize*5;

        image = rs.loadImage("/res/objects/book" + i + ".png");

    }

    @Override
    public void draw(Graphics2D g) {
        int screenX = (int) (worldX - gp.player.worldX + gp.player.screenX);
        int screenY = (int) (worldY - gp.player.worldY + gp.player.screenY);

        if (worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - GamePanel.tileSize * 3 < gp.player.worldX + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - GamePanel.tileSize * 4 < gp.player.worldY + gp.player.screenY) {
            try {
                g.drawImage(image, screenX, screenY, GamePanel.tileSize * 2, GamePanel.tileSize * 2, null);
            } catch (Exception e) {
                System.out.println("SuperObject: Не удалось загрузить  на позицию " + screenX + " и " + screenY);
            }
        }

    }
}