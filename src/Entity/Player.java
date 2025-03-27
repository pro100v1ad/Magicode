package Entity;



import display.GamePanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import static java.lang.Math.sqrt;

/*
Класс для создания игрока
 */
public class Player extends Entity{ // Класс игрока
    // Для доступа к главной панели
    GamePanel gp;

    // Для работы с изображением
    private BufferedImage image;
    private BufferedImage[] playerImage = new BufferedImage[16];
    private int spriteNum;
    private int spriteCount;

    // Положение игрока в мире и направление взгляда
    public final int screenX;
    public final int screenY;

    public boolean checkCollisionUp = false;
    public boolean checkCollisionDown = false;
    public boolean checkCollisionRight = false;
    public boolean checkCollisionLeft = false;

    public Player(GamePanel gp){

        // Для доступа к главной панели
        this.gp = gp;
        setDefaultValues();
        loadPlayerImages();
        image = playerImage[0];
        // Устанавливает позицию игрока на центр экрана
        screenX = GamePanel.WIDTH/2 - GamePanel.tileSize;
        screenY = GamePanel.HEIGHT/2 - GamePanel.tileSize*2;
        // Для анимации
        spriteNum = 1;
        spriteCount = 0;

        // Для коллизии
        collisionWidth = GamePanel.tileSize*2;
        collisionHeight = GamePanel.tileSize*3;
        collisionCode = 2;
    }

    public void loadPlayerImages() {
            playerImage[0] = gp.textureAtlas.textures[4][0].getTexture();
            playerImage[1] = gp.textureAtlas.textures[4][1].getTexture();
            playerImage[2] = gp.textureAtlas.textures[4][2].getTexture();
            playerImage[3] = gp.textureAtlas.textures[4][3].getTexture();

            playerImage[4] = gp.textureAtlas.textures[4][4].getTexture();
            playerImage[5] = gp.textureAtlas.textures[4][5].getTexture();
            playerImage[6] = gp.textureAtlas.textures[4][6].getTexture();
            playerImage[7] = gp.textureAtlas.textures[4][7].getTexture();

            playerImage[8] = gp.textureAtlas.textures[4][8].getTexture();
            playerImage[9] = gp.textureAtlas.textures[4][9].getTexture();
            playerImage[10] = gp.textureAtlas.textures[4][10].getTexture();
            playerImage[11] = gp.textureAtlas.textures[4][11].getTexture();

            playerImage[12] = gp.textureAtlas.textures[4][12].getTexture();
            playerImage[13] = gp.textureAtlas.textures[4][13].getTexture();
            playerImage[14] = gp.textureAtlas.textures[4][14].getTexture();
            playerImage[15] = gp.textureAtlas.textures[4][15].getTexture();
    }

    public void setDefaultValues() {
//        worldX = 10;
//        worldY = 10;

        worldX = GamePanel.tileSize*120;
        worldY = GamePanel.tileSize*120;

        speed = (4*GamePanel.scale)/(GamePanel.UPDATE_RATE_Speed); // scale минимум 1/4 и максимум 2.
    }

    public void update() {
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
                if(gp.collision.checkCollisionUp(this)) {
                    if(worldY > - 0) worldY -= 1;
                }
                if(gp.collision.checkCollisionRight(this)) {
                    if (worldX < GamePanel.maxWorldCol*GamePanel.tileSize-GamePanel.tileSize*2-1) worldX += 1;
                }
            }
        } else if(direction.equals("up_left")) {
            for(int i = 0; i < speed/sqrt(2); i++) {// Обработка движения вверх
                if(gp.collision.checkCollisionUp(this)) {
                    if(worldY > - 0) worldY -= 1;
                }
                if(gp.collision.checkCollisionLeft(this)) {
                    if(worldX > 1) worldX -= 1;
                }
            }
        } else if(direction.equals("down_left")) {
            for(int i = 0; i < speed/sqrt(2); i++) {// Обработка движения вверх
                if(gp.collision.checkCollisionDown(this)) {
                    if(worldY < GamePanel.maxWorldRow*GamePanel.tileSize-GamePanel.tileSize*4 - 1) worldY += 1;
                }
                if(gp.collision.checkCollisionLeft(this)) {
                    if(worldX > 1) worldX -= 1;
                }
            }
        } else if(direction.equals("down_right")) {
            for(int i = 0; i < speed/sqrt(2); i++) {// Обработка движения вверх
                if(gp.collision.checkCollisionDown(this)) {
                    if(worldY < GamePanel.maxWorldRow*GamePanel.tileSize-GamePanel.tileSize*4 - 1) worldY += 1;
                }
                if(gp.collision.checkCollisionRight(this)) {
                    if(worldX < GamePanel.maxWorldCol*GamePanel.tileSize-GamePanel.tileSize*2-1) worldX += 1;
                }
            }
        } else if(direction.equals("up")) {
            for(int i = 0; i < speed/sqrt(2); i++) {
                if(worldY > - 0 && gp.collision.checkCollisionUp(this)) worldY -= 1;
            }
        } else if(direction.equals("down")) {
            for(int i = 0; i < speed/sqrt(2); i++) if(gp.collision.checkCollisionDown(this) && worldY < GamePanel.maxWorldRow*GamePanel.tileSize-GamePanel.tileSize*4 - 1) worldY += 1;
        } else if(direction.equals("left")) {
            for(int i = 0; i < speed/sqrt(2); i++) if(worldX > 1 && gp.collision.checkCollisionLeft(this)) worldX -= 1;
        } else if(direction.equals("right")) {
            for(int i = 0; i < speed/sqrt(2); i++) if(gp.collision.checkCollisionRight(this) && worldX < GamePanel.maxWorldCol*GamePanel.tileSize-GamePanel.tileSize*2-1) worldX += 1;
        }

        // Обновляем направление для анимации
        spriteCount++;
        if(spriteCount > 20*(GamePanel.UPDATE_RATE_Speed)) {
            spriteNum = (spriteNum + 1) % 4;
            spriteCount = 0;
        }
    }


    public void draw(Graphics2D g) { // Рисует игрока


        switch (direction) { // Анимирует движение по направлениям
            case "up": image = playerImage[spriteNum]; break;
            case "down": image = playerImage[4 + spriteNum]; break;
            case "left", "up_left", "down_left": image = playerImage[8 + spriteNum]; break;
            case "right", "up_right", "down_right": image = playerImage[12 + spriteNum]; break;
            case "null": image = playerImage[4]; break;
        }

        g.drawImage(image, screenX, screenY, GamePanel.tileSize*2, GamePanel.tileSize*4, null);

    }
}


