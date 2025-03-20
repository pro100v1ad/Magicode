package game;

import Tile.Tiles;
import display.GamePanel;
import graphics.Layer;
import graphics.SpriteSheet;
import graphics.Texture;
import utils.Time;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import static java.lang.Math.abs;
import static utils.ResourceLoader.loadImage;

public class BackGround {
    // Для работы с графикой
    private BufferedImage imageMap;
    public Tiles[] tiles;
    private int scaleX, scaleY;
    //Доступ к главной панели
    GamePanel gp;

    public BackGround(GamePanel gp) {
        // Ensure gp is not null
        if (gp == null) {
            throw new IllegalArgumentException("GamePanel (gp) cannot be null");
        }
        this.gp = gp;

        // Initialize the tiles array and each Tiles object
        tiles = new Tiles[221];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tiles(); // Initialize each element
        }
        createMap("/maps/map01.txt");
        loadMap("/maps/map01.txt");
        loadTiles();
    }
    //TEMP
    public void createMap(String mapName) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(mapName))) {

            for (int i = 0; i < GamePanel.maxWorldRow; i++) {
                for (int j = 0; j < GamePanel.maxWorldCol; j++) {
                    // Генерируем два случайных числа
                    int first = 1; // Число от 0 до 999
                    int second = 1; // Число от 0 до 999

                    // Формируем строку в формате "число_число"
                    String line = first + "_" + second;

                    // Записываем строку в файл
                    writer.write(line);

                    // Добавляем пробел между элементами в строке (кроме последнего)
                    if (j < GamePanel.maxWorldCol - 1) {
                        writer.write(" ");
                    }
                }
                // Переходим на новую строку после каждой строки массива
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
    //////
    public void loadTiles() {
        // Ensure textureAtlas is initialized
        if (gp.textureAtlas == null) {
            throw new IllegalStateException("TextureAtlas must be initialized before loading tiles");
        }

        // Assign textures to tiles
        tiles[1].image = gp.textureAtlas.textures[0][0].getTexture();
        tiles[0].image = gp.textureAtlas.textures[0][1].getTexture();
        tiles[2].image = gp.textureAtlas.textures[0][2].getTexture();
        tiles[3].image = gp.textureAtlas.textures[0][3].getTexture();
        tiles[4].image = gp.textureAtlas.textures[0][4].getTexture();
        tiles[5].image = gp.textureAtlas.textures[0][5].getTexture();
        tiles[6].image = gp.textureAtlas.textures[0][6].getTexture();
        tiles[7].image = gp.textureAtlas.textures[0][7].getTexture();
        tiles[8].image = gp.textureAtlas.textures[0][8].getTexture();

        tiles[9].image = gp.textureAtlas.textures[1][0].getTexture();
        tiles[10].image = gp.textureAtlas.textures[1][1].getTexture();
        tiles[11].image = gp.textureAtlas.textures[1][2].getTexture();
        tiles[12].image = gp.textureAtlas.textures[1][3].getTexture();
        tiles[13].image = gp.textureAtlas.textures[1][4].getTexture();
        tiles[14].image = gp.textureAtlas.textures[1][5].getTexture();
        tiles[15].image = gp.textureAtlas.textures[1][6].getTexture();
        tiles[16].image = gp.textureAtlas.textures[1][7].getTexture();
        tiles[17].image = gp.textureAtlas.textures[1][8].getTexture();
    }

    public void loadMap(String path) { // загружает мир из файла

        for (int i = 0; i < GamePanel.maxWorldRow; i++) {
            for (int j = 0; j < GamePanel.maxWorldCol; j++) {
                GamePanel.worldMap[i][j] = new Layer();
            }
        }

        try {
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < GamePanel.maxWorldCol && row < GamePanel.maxWorldRow) {
                String line = br.readLine();

                while (col < GamePanel.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    GamePanel.worldMap[row][col].setLayers(numbers[col]);
                    col++;
                }
                if (col == GamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
    }

    public void draw(Graphics2D g) { // Рисует мир
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < GamePanel.maxWorldCol && worldRow < GamePanel.maxWorldRow) {
            int tileNum1 = GamePanel.worldMap[worldRow][worldCol].getLayer(1);
            int tileNum2 = GamePanel.worldMap[worldRow][worldCol].getLayer(2);

            int worldX = worldCol * GamePanel.tileSize;
            int worldY = worldRow * GamePanel.tileSize;
            int screenX = (int) (worldX - gp.player.worldX + gp.player.screenX);
            int screenY = (int) (worldY - gp.player.worldY + gp.player.screenY);

            if (worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - GamePanel.tileSize * 3 < gp.player.worldX + gp.player.screenX &&
                    worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - GamePanel.tileSize * 4 < gp.player.worldY + gp.player.screenY) {
                try {
                    if(GamePanel.worldMap[worldRow][worldCol].getLayer(1) != 0) {
                        g.drawImage(tiles[tileNum1].image, screenX, screenY, GamePanel.tileSize, GamePanel.tileSize, null);
                    }
                    if(GamePanel.worldMap[worldRow][worldCol].getLayer(2) != 0) {
                        g.drawImage(tiles[tileNum2].image, screenX, screenY, GamePanel.tileSize, GamePanel.tileSize, null);
                    }

                } catch (Exception e) {
                    System.out.println("BackGround: Не удалось загрузить на позицию " + screenX + " и " + screenY + ": tiles[" + tileNum1 + "];");
                }
            }

            worldCol++;
            if (worldCol == GamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}