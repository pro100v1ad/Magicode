package Entity;



import display.GamePanel;
import graphics.Sprite;
import graphics.SpriteSheet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.SocketTimeoutException;

import static utils.ResourceLoader.loadImage;
/*
Класс для создания игрока
 */
public class Player extends Entity{ // Класс игрока
    // Для доступа к главной панели
    GamePanel gp;

    // Для работы с изображением
    private BufferedImage imageMap;
    private BufferedImage image;
    private BufferedImage[] playerImage = new BufferedImage[16];
    private int spriteNum;
    private int spriteCount;
    private int scaleX, scaleY;

    // Положение игрока в мире и направление взгляда
    public final int screenX;
    public final int screenY;
    public String direction;

    public Player(GamePanel gp){
        // Загрузка спрайтов
        imageMap = loadImage("playerSkin.png");
        scaleX = 1;
        scaleY = 2;
        setDefaultValues();
        SpriteSheet spriteSheet = new SpriteSheet(imageMap, 4, 16); // Я пробовал загрузить анимированную хотьбу, надо доделать!!!
        for (int i = 0; i < 4; i++) {
            playerImage[i] = spriteSheet.getSprite(i, scaleX, scaleY);
            playerImage[i+4] = spriteSheet.getSprite(i+36, scaleX, scaleY);
            playerImage[i+8] = spriteSheet.getSprite(i+72, scaleX, scaleY);
            playerImage[i+12] = spriteSheet.getSprite(i+108, scaleX, scaleY);
        }
        image = playerImage[0];
        // Устанавливает позицию игрока на центр экрана
        screenX = GamePanel.WIDTH/2 - GamePanel.tileSize;
        screenY = GamePanel.HEIGHT/2 - GamePanel.tileSize*2;
        direction = "null";
        // Для анимации
        spriteNum = 1;
        spriteCount = 0;
        // Для коллизии
        solidArea = new Rectangle();
        solidArea.x = 4;
        solidArea.y = 8;
        solidArea.width = 16;
        solidArea.height = 32;
        // Для доступа к главной панели
        this.gp = gp;
    }

    public void setDefaultValues() {
        worldX = GamePanel.tileSize*23;
        worldY = GamePanel.tileSize*22;
        speed = 4;
    }

    public void update() { // Обрабатывает логику игрока.
        // Определяет направление движения
        if(GamePanel.keys[0]) {
            direction = "up";
        }
        else if(GamePanel.keys[1]) {
            direction = "left";
        }
        else if(GamePanel.keys[2]) {
            direction = "down";
        }
        else if(GamePanel.keys[3]) {
            direction = "right";
        }

        //Проверка коллизии
        collisionOn = false;
        gp.cChecker.checkTile(this);
        if(!collisionOn) { // Меняет положение игрока

            if(GamePanel.keys[0]) {
                worldY -= speed;
            }
            else if(GamePanel.keys[1]) {
                worldX -= speed;
            }
            else if(GamePanel.keys[2]) {
                worldY += speed;
            }
            else if(GamePanel.keys[3]) {
                worldX += speed;
            }
        }

        // Логика анимации
        spriteCount++;
        if(spriteCount > 20) {
            if(spriteNum == 0) {
                spriteNum = 1;
            }
            else if(spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 3;
            }
            else if(spriteNum == 3) {
                spriteNum = 0;
            }
            spriteCount = 0;
        }
    }

    public void draw(Graphics2D g) { // Рисует игрока

        switch (direction) { // Анимирует движение
            case "up": image = playerImage[8+spriteNum]; break;
            case "down": image = playerImage[spriteNum]; break;
            case "left": image = playerImage[12+ spriteNum]; break;
            case "right": image = playerImage[4+ spriteNum]; break;
            case "null": image = playerImage[0]; break;
        }

        g.drawImage(image, screenX, screenY, GamePanel.tileSize*2, GamePanel.tileSize*4, null);
    }
}


