package gui;

import display.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class GUI_StartMenu extends GUI{

    private BufferedImage[] images;
    private boolean clickOnMenu = false;
    private int isButton; // 1, 2, 3, 0
    private GamePanel gp;

    public GUI_StartMenu(GamePanel gp) {
        this.gp = gp;
        images = new BufferedImage[10];
        images[0] = gp.textureAtlas.textures[6][6].getTexture();


        isButton = 0;

        try {
            InputStream is = getClass().getResourceAsStream("/res/font/my_font.ttf");
            if (is != null) {
                my_font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(36f); // Регистрируем и задаём размер
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки шрифта: " + e.getMessage());
            my_font = new Font("Arial", Font.PLAIN, 24); // Шрифт по умолчанию
        }


    }

    public void update() {

        int x = GamePanel.mouseX;
        int y = GamePanel.mouseY;

        if(x > GamePanel.tileSize*26 && x < GamePanel.tileSize*45 &&
                y > GamePanel.tileSize*20 && y < GamePanel.tileSize*25) {

            isButton = 1;

            if (clickOnMenu) { // логика при нажатии туда
                gp.state = GamePanel.GameState.Game;
                gp.guiPlayer.setClickOnMenu(false);
            }

        } else if(x > GamePanel.tileSize*26 && x < GamePanel.tileSize*45 &&
                y > GamePanel.tileSize*26 && y < GamePanel.tileSize*32) {
            isButton = 2;
        } else if(x > GamePanel.tileSize*26 && x < GamePanel.tileSize*45 &&
                y > GamePanel.tileSize*33 && y < GamePanel.tileSize*38) {
            isButton = 3;

            if (clickOnMenu) { // логика при нажатии туда
                System.exit(0);
            }

        } else isButton = 0;


        clickOnMenu = false;
    }

    public void draw(Graphics2D g) {
        if(gp.state.equals(GamePanel.GameState.StartMenu)) {
            drawMenu(g);
        }
    }

    public void drawMenu(Graphics2D g) {

        g.setFont(my_font);

        g.drawImage(images[0], 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);

        if(isButton == 1) {
            g.setColor(Color.ORANGE);
            g.drawString("Начать", GamePanel.tileSize * 29, GamePanel.tileSize * 21 + 36);
        }

        g.drawImage(images[2], 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        if(isButton == 2) {
            g.setColor(Color.ORANGE);
            g.drawString("Настройки", GamePanel.tileSize * 28, GamePanel.tileSize * 27 + 36);
        }

        g.drawImage(images[3], 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
        if(isButton == 3) {
            g.setColor(Color.ORANGE);
            g.drawString("Выход", GamePanel.tileSize * 30, GamePanel.tileSize * 34 + 36);
        }


        if(isButton == 0 || isButton == 2 || isButton == 3) {
            g.setColor(Color.WHITE);
            g.drawString("Начать", GamePanel.tileSize * 29, GamePanel.tileSize * 21 + 36);
        }
        if(isButton == 0 || isButton == 1 || isButton == 3) {
            g.setColor(Color.WHITE);
            g.drawString("Настройки", GamePanel.tileSize * 28, GamePanel.tileSize * 27 + 36);
        }
        if(isButton == 0 || isButton == 1 || isButton == 2) {
            g.setColor(Color.WHITE);
            g.drawString("Выход", GamePanel.tileSize * 30, GamePanel.tileSize * 34 + 36);
        }
    }

    public void setClickOnMenu(boolean click) {
        this.clickOnMenu = click;
    }


}
