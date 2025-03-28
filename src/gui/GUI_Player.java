package gui;

import Tile.Tiles;
import display.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GUI_Player extends GUI{

    private BufferedImage[] images;
    private boolean clickOnMenu = false;
    private GamePanel gp;

    public GUI_Player(GamePanel gp) {
        this.gp = gp;
        images = new BufferedImage[10];
        images[0] = gp.textureAtlas.textures[5][0].getTexture();
        images[1] = gp.textureAtlas.textures[5][1].getTexture();
        images[2] = gp.textureAtlas.textures[5][2].getTexture();
        images[3] = gp.textureAtlas.textures[5][3].getTexture();
        images[4] = gp.textureAtlas.textures[5][4].getTexture();

        images[5] = gp.textureAtlas.textures[6][0].getTexture();
        images[6] = gp.textureAtlas.textures[6][1].getTexture();

    }

    public void draw(Graphics2D g) {
//        g.drawImage(images[0], 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        int health = (int)(gp.player.health/gp.player.maxHealth*100);
        drawGUIHealthBar(g, health);
        drawButtonMenu(g);
    }

    public void drawGUIHealthBar(Graphics2D g, int x) {
        if(x > 100) return;
        int startX = GamePanel.WIDTH/2 - images[1].getWidth()/2;
        int startY = (int)(GamePanel.HEIGHT/1.1);
        g.drawImage(images[1], startX, startY, GamePanel.tileSize*20, GamePanel.tileSize, null);


        double currentX = startX+3;
        double delta = (GamePanel.tileSize*0.2)-0.05;
        for(int i = 0; i < x; i++) {

            if(i < 98) g.drawImage(images[2], (int)currentX, startY+3, (int)delta, GamePanel.tileSize-6, null);
            else if(i == 98) g.drawImage(images[4], (int)currentX, startY+3, (int)delta, GamePanel.tileSize-6, null);
            else g.drawImage(images[3], (int)currentX, startY+3, (int)delta, GamePanel.tileSize-6, null);

            currentX += delta;
        }

    }

    public void drawButtonMenu(Graphics2D g) {

        g.drawImage(images[5], GamePanel.tileSize, GamePanel.tileSize, GamePanel.tileSize * 4, GamePanel.tileSize * 4, null);


        if(GamePanel.mouseX > GamePanel.tileSize &&
            GamePanel.mouseY > GamePanel.tileSize &&
            GamePanel.mouseX < GamePanel.tileSize*5 &&
            GamePanel.mouseY < GamePanel.tileSize*5) {
            g.drawImage(images[6], GamePanel.tileSize, GamePanel.tileSize, GamePanel.tileSize * 4, GamePanel.tileSize * 4, null);

            if (clickOnMenu) { // логика при нажатии туда
                clickOnMenu = false;
            }

        }
    }

    public void setClickOnMenu(boolean click) {
        this.clickOnMenu = click;
    }

}
