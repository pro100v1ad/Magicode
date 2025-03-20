package Entity;



import display.GamePanel;
import game.BackGround;
import graphics.SpriteSheet;
import utils.Collision;
import java.awt.*;
import java.awt.image.BufferedImage;
import static java.lang.Math.sqrt;
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

    public boolean checkCollisionUp = false;
    public boolean checkCollisionDown = false;
    public boolean checkCollisionRight = false;
    public boolean checkCollisionLeft = false;

    private Collision collision;
    private BackGround bg;

    public Player(GamePanel gp){
        // Загрузка спрайтов
        imageMap = loadImage("playerSkin.png");
        scaleX = 1;
        scaleY = 2;
        setDefaultValues();
        SpriteSheet spriteSheet = new SpriteSheet(imageMap, 4, 16); // Я пробовал загрузить анимированную ходьбу, надо доделать!!!
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

        collision = new Collision();
    }

    public void setDefaultValues() {
        worldX = 100;
        worldY = 100;

        speed = (4*GamePanel.scale)/(GamePanel.UPDATE_RATE_Speed); // scale минимум 1/4 и максимум 2.
    }

    public void update() { // Обрабатывает логику игрока.
        // Определяет направление движения все 8
        if (GamePanel.keys[0] && GamePanel.keys[3]) {
            direction = "up_right";
        } else if (GamePanel.keys[0] && GamePanel.keys[1]) {
            direction = "up_left";
        } else if (GamePanel.keys[2] && GamePanel.keys[1]) {
            direction = "down_left";
        } else if (GamePanel.keys[2] && GamePanel.keys[3]) {
            direction = "down_right";
        } else if(GamePanel.keys[0]) {
            direction = "up";
        } else if(GamePanel.keys[1]) {
            direction = "left";
        } else if(GamePanel.keys[2]) {
            direction = "down";
        } else if(GamePanel.keys[3]) {
            direction = "right";
        } else direction = "null";

        //Проверка коллизии
        if(direction.equals("up_right")) {
            for(int i = 0; i < speed/sqrt(2); i++) {// Обработка движения вверх
                if(!checkCollisionUp) {
                    if(worldY > - 0) worldY -= 1;
                }
                if(!checkCollisionRight) {
                    if (worldX < GamePanel.maxWorldCol*GamePanel.tileSize-GamePanel.tileSize*2-1) worldX += 1;
                }
            }
        } else if(direction.equals("up_left")) {
            for(int i = 0; i < speed/sqrt(2); i++) {// Обработка движения вверх
                if(!checkCollisionUp) {
                    if(worldY > - 0) worldY -= 1;
                }
                if(!checkCollisionLeft) {
                    if(worldX > 1) worldX -= 1;
                }
            }
        } else if(direction.equals("down_left")) {
            for(int i = 0; i < speed/sqrt(2); i++) {// Обработка движения вверх
                if(!checkCollisionDown) {
                    if(worldY < GamePanel.maxWorldRow*GamePanel.tileSize-GamePanel.tileSize*4 - 1) worldY += 1;
                }
                if(!checkCollisionLeft) {
                    if(worldX > 1) worldX -= 1;
                }
            }
        } else if(direction.equals("down_right")) {
            for(int i = 0; i < speed/sqrt(2); i++) {// Обработка движения вверх
                if(!checkCollisionDown) {
                    if(worldY < GamePanel.maxWorldRow*GamePanel.tileSize-GamePanel.tileSize*4 - 1) worldY += 1;
                }
                if(!checkCollisionRight) {
                    if(worldX < GamePanel.maxWorldCol*GamePanel.tileSize-GamePanel.tileSize*2-1) worldX += 1;
                }
            }
        } else if(direction.equals("up") && !checkCollisionUp) {
            for(int i = 0; i < speed/sqrt(2); i++) if(worldY > - 0) worldY -= 1;
        } else if(direction.equals("down") && !checkCollisionDown) {
            for(int i = 0; i < speed/sqrt(2); i++) if(worldY < GamePanel.maxWorldRow*GamePanel.tileSize-GamePanel.tileSize*4 - 1) worldY += 1;
        } else if(direction.equals("left") && !checkCollisionLeft) {
            for(int i = 0; i < speed/sqrt(2); i++) if(worldX > 1) worldX -= 1;
        } else if(direction.equals("right") && !checkCollisionRight) {
            for(int i = 0; i < speed/sqrt(2); i++) if(worldX < GamePanel.maxWorldCol*GamePanel.tileSize-GamePanel.tileSize*2-1) worldX += 1;
        }

        // Логика анимации
        spriteCount++;
        if(spriteCount > 20*(GamePanel.UPDATE_RATE_Speed)) {
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


        switch (direction) { // Анимирует движение по направлениям
            case "up": image = playerImage[8 + spriteNum]; break;
            case "down": image = playerImage[spriteNum]; break;
            case "left": image = playerImage[12 + spriteNum]; break;
            case "right": image = playerImage[4 + spriteNum]; break;
            case "up_right": image = playerImage[4 + spriteNum]; break;
            case "up_left": image = playerImage[12 + spriteNum]; break;
            case "down_left": image = playerImage[12 + spriteNum]; break;
            case "down_right": image = playerImage[4 + spriteNum]; break;
            case "null": image = playerImage[0]; break;
        }

        g.drawImage(image, screenX, screenY, GamePanel.tileSize*2, GamePanel.tileSize*4, null);

    }
}


