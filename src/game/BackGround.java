package game;

import Tile.Tiles;
import display.GamePanel;
import graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static utils.ResourceLoader.loadImage;
/*
Класс для создания заднего фона игры
 */
public class BackGround {
    // Для работы с графикой
    private BufferedImage imageMap;
    public Tiles[] tiles;
    private int scaleX, scaleY;
    //Доступ к главной панели
    GamePanel gp;
    public BackGround(GamePanel gp) {
        // Загрузка изображений
        imageMap = loadImage("tileset_texture.png");
        SpriteSheet spriteSheet = new SpriteSheet(imageMap, 17, 16);
        tiles = new Tiles[221];
        scaleX = 1;
        scaleY = 1;
        for(int i = 0; i < 32; i++) {
            tiles[i] = new Tiles();
            tiles[i].image = spriteSheet.getSprite(i, scaleX, scaleY);
            if(i == 5) tiles[i].collision = true;
        }
        loadMap("/maps/map01.txt");
        //Доступ к главной панели
        this.gp = gp;
    }

    public void loadMap(String path) { // загружает мир из файла
        try {
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < GamePanel.maxWorldCol && row < GamePanel.maxWorldRow){

                String line = br.readLine();

                while(col < GamePanel.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    GamePanel.worldMap[row][col] = num;
                    col++;
                }
                if(col == GamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {

    }

    public void draw(Graphics2D g) { // Рисует мир

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < GamePanel.maxWorldCol && worldRow < GamePanel.maxWorldRow) {

            int tileNum = GamePanel.worldMap[worldRow][worldCol];

            int worldX = worldCol * GamePanel.tileSize;
            int worldY = worldRow * GamePanel.tileSize;
            int screenX = (int) (worldX - gp.player.worldX + gp.player.screenX);
            int screenY = (int) (worldY - gp.player.worldY + gp.player.screenY);

            if(worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - GamePanel.tileSize*3 < gp.player.worldX + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - GamePanel.tileSize*4 < gp.player.worldY + gp.player.screenY)
            {
                try {
                    g.drawImage(tiles[tileNum].image, screenX, screenY, GamePanel.tileSize, GamePanel.tileSize, null);

                } catch (Exception e) {
                    System.out.println("Не удалось загрузить  на позицию " + screenX + " и " + screenY + ": tiles[" + tileNum + "];");
                }
            }


            worldCol++;
            if(worldCol == GamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
