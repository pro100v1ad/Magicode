package gui;

import display.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class GUI_Menu extends GUI{

    private BufferedImage[] images;
    private int isButton; // 1, 2, 3, 0
    private int transparency;
    private GamePanel gp;



    public GUI_Menu(GamePanel gp) {
        this.gp = gp;
        images = new BufferedImage[10];
        images[0] = gp.textureAtlas.textures[6][2].getTexture();
        images[1] = gp.textureAtlas.textures[6][3].getTexture();
        images[2] = gp.textureAtlas.textures[6][4].getTexture();
        images[3] = gp.textureAtlas.textures[6][5].getTexture();

        isButton = 0;
        transparency = 0;


    }

    public void update() {
        if(isButton != 0) transparency = 100;
        int x = GamePanel.mouseX;
        int y = GamePanel.mouseY;

        if(x > GamePanel.tileSize*26 && x < GamePanel.tileSize*45 &&
            y > GamePanel.tileSize*10 && y < GamePanel.tileSize*18) {

            isButton = 1;

            if (clickOnMenu) { // логика при нажатии туда
                gp.state = GamePanel.GameState.Game;
                gp.guiPlayer.setClickOnMenu(false);
            }

        } else if(x > GamePanel.tileSize*26 && x < GamePanel.tileSize*45 &&
                y > GamePanel.tileSize*18 && y < GamePanel.tileSize*26) {
            isButton = 2;
        } else if(x > GamePanel.tileSize*26 && x < GamePanel.tileSize*45 &&
                y > GamePanel.tileSize*26 && y < GamePanel.tileSize*34) {
            isButton = 3;

            if (clickOnMenu) { // логика при нажатии туда
                gp.state = GamePanel.GameState.StartMenu;
                gp.guiStartMenu.setClickOnMenu(false);
            }

        } else isButton = 0;


        clickOnMenu = false;
    }

    public void draw(Graphics2D g) {
        if(gp.state.equals(GamePanel.GameState.GameMenu)) {
            drawMenu(g);
        }
    }

    public void drawMenu(Graphics2D g) {

        g.setFont(my_font);

        g.setColor(new Color(0,0,0, 100));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);


        g.drawImage(images[1], 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        if(isButton == 1) {
//            g.fillRect(GamePanel.tileSize * 26, GamePanel.tileSize * 10, GamePanel.tileSize * 19, GamePanel.tileSize * 8);
            g.setColor(Color.ORANGE);
            g.drawString("Продолжить", GamePanel.tileSize * 29, GamePanel.tileSize * 12 + 36);
        }

        g.drawImage(images[2], 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        if(isButton == 2) {
//            g.fillRect(GamePanel.tileSize * 26, GamePanel.tileSize * 18, GamePanel.tileSize * 19, GamePanel.tileSize * 8);
            g.setColor(Color.ORANGE);
            g.drawString("Настройки", GamePanel.tileSize * 30, GamePanel.tileSize * 20 + 36);
        }

        g.drawImage(images[3], 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        if(isButton == 3) {
//            g.fillRect(GamePanel.tileSize * 26, GamePanel.tileSize * 26, GamePanel.tileSize * 19, GamePanel.tileSize * 8);
            g.setColor(Color.ORANGE);
            g.drawString("Выход", GamePanel.tileSize * 32, GamePanel.tileSize * 28 + 36);
        }

        g.drawImage(images[0], 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);

        if(isButton == 0 || isButton == 2 || isButton == 3) {
            g.setColor(Color.WHITE);
            g.drawString("Продолжить", GamePanel.tileSize * 29, GamePanel.tileSize * 12 + 36);
        }
        if(isButton == 0 || isButton == 1 || isButton == 3) {
            g.setColor(Color.WHITE);
            g.drawString("Настройки", GamePanel.tileSize * 30, GamePanel.tileSize * 20 + 36);
        }
        if(isButton == 0 || isButton == 1 || isButton == 2) {
            g.setColor(Color.WHITE);
            g.drawString("Выход", GamePanel.tileSize * 32, GamePanel.tileSize * 28 + 36);
        }
    }



}
