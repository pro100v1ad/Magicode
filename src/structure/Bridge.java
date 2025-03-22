package structure;

import Tile.Tiles;
import display.GamePanel;

import java.awt.*;
/*
Структура, что строит мост.
Поменять направления на UP DOWN RIGHT LEFT,
чтобы строило поломанные стороны исходя из этого
 */
public class Bridge extends Structure{

    private int len;
    private GamePanel gp;

    public Bridge(GamePanel gp, String name, int bridgeLength, String direction, boolean isBreak, int worldX, int worldY) {

        this.name = name;
        this.len = bridgeLength;
        this.isBreak = isBreak;
        this.direction = direction;
        this.worldX = worldX;
        this.worldY = worldY;
        this.gp = gp;

        loadTextures();

    }
    @Override
    protected void loadTextures() {
        tiles = new Tiles[4];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tiles(); // Initialize each element
        }

        if(direction.equals("right") || direction.equals("left")){
            tiles[0].image = gp.textureAtlas.textures[2][0].getTexture();
            tiles[1].image = gp.textureAtlas.textures[2][1].getTexture();
            tiles[2].image = gp.textureAtlas.textures[2][2].getTexture();
            tiles[3].image = gp.textureAtlas.textures[2][3].getTexture();

        } else {
            tiles[0].image = gp.textureAtlas.textures[3][0].getTexture();
            tiles[1].image = gp.textureAtlas.textures[3][1].getTexture();
            tiles[2].image = gp.textureAtlas.textures[3][2].getTexture();
            tiles[3].image = gp.textureAtlas.textures[3][3].getTexture();
        }


    }
    @Override
    public void draw(Graphics2D g) { // Дорисовать направления RIGHT и LEFT

        int screenX = (int) (worldX - gp.player.worldX + gp.player.screenX);
        int screenY = (int) (worldY - gp.player.worldY + gp.player.screenY);
        int sizeX;
        int sizeY;
        if(direction.equals("up") || direction.equals("down")) {
            sizeX = 10;
            sizeY = 20 + len*2;
        } else {
            sizeX = 20 + len*2;
            sizeY = 10;

        }

        if (worldX + GamePanel.tileSize*sizeX > gp.player.worldX - gp.player.screenX &&
                worldX - GamePanel.tileSize * 3 < gp.player.worldX + gp.player.screenX &&
                worldY + GamePanel.tileSize*sizeY > gp.player.worldY - gp.player.screenY &&
                worldY - GamePanel.tileSize * 4 < gp.player.worldY + gp.player.screenY) {


            if (direction.equals("right")) {
                g.drawImage(tiles[0].image, screenX, screenY, GamePanel.tileSize * 5, GamePanel.tileSize * 8, null);
                if (isBreak) {
                    g.drawImage(tiles[3].image, screenX + GamePanel.tileSize * 5, screenY, GamePanel.tileSize * 5, GamePanel.tileSize * 8, null);
                    for (int i = 0; i < len; i++) {
                        g.drawImage(tiles[2].image, screenX + GamePanel.tileSize * (10 + 2 * i), screenY, GamePanel.tileSize * 2, GamePanel.tileSize * 8, null);
                    }
                    g.drawImage(tiles[1].image, screenX + GamePanel.tileSize * (10 + 2 * (len)), screenY, GamePanel.tileSize * 4, GamePanel.tileSize * 8, null);
                } else {
                    g.drawImage(tiles[2].image, screenX + GamePanel.tileSize * 5, screenY, GamePanel.tileSize * 2, GamePanel.tileSize * 8, null);
                    g.drawImage(tiles[2].image, screenX + GamePanel.tileSize * 7, screenY, GamePanel.tileSize * 2, GamePanel.tileSize * 8, null);
                    for (int i = 0; i < len; i++) {
                        g.drawImage(tiles[2].image, screenX + GamePanel.tileSize * (9 + 2 * i), screenY, GamePanel.tileSize * 2, GamePanel.tileSize * 8, null);
                    }
                    g.drawImage(tiles[1].image, screenX + GamePanel.tileSize * (9 + 2 * (len)), screenY, GamePanel.tileSize * 4, GamePanel.tileSize * 8, null);
                }
            } else if (direction.equals("left")) {
                g.drawImage(tiles[0].image, screenX, screenY, GamePanel.tileSize * 5, GamePanel.tileSize * 8, null);
                if (isBreak) {
                    for (int i = 0; i < len; i++) {
                        g.drawImage(tiles[2].image, screenX + GamePanel.tileSize * (5 + 2 * i), screenY, GamePanel.tileSize * 2, GamePanel.tileSize * 8, null);
                    }
                    g.drawImage(tiles[3].image, screenX + GamePanel.tileSize * (5 + len * 2), screenY, GamePanel.tileSize * 5, GamePanel.tileSize * 8, null);
                    g.drawImage(tiles[1].image, screenX + GamePanel.tileSize * (10 + 2 * (len)), screenY, GamePanel.tileSize * 4, GamePanel.tileSize * 8, null);

                } else {
                    g.drawImage(tiles[2].image, screenX + GamePanel.tileSize * 5, screenY, GamePanel.tileSize * 2, GamePanel.tileSize * 8, null);
                    g.drawImage(tiles[2].image, screenX + GamePanel.tileSize * 7, screenY, GamePanel.tileSize * 2, GamePanel.tileSize * 8, null);
                    for (int i = 0; i < len; i++) {
                        g.drawImage(tiles[2].image, screenX + GamePanel.tileSize * (9 + 2 * i), screenY, GamePanel.tileSize * 2, GamePanel.tileSize * 8, null);
                    }
                    g.drawImage(tiles[1].image, screenX + GamePanel.tileSize * (9 + 2 * (len)), screenY, GamePanel.tileSize * 4, GamePanel.tileSize * 8, null);
                }

            } else if (direction.equals("down")) {
                g.drawImage(tiles[1].image, screenX, screenY, GamePanel.tileSize * 8, GamePanel.tileSize * 4, null);

                if (isBreak) {
                    g.drawImage(tiles[3].image, screenX, screenY + GamePanel.tileSize * 4, GamePanel.tileSize * 8, GamePanel.tileSize * 6, null);
                    for (int i = 0; i < len; i++) {
                        g.drawImage(tiles[2].image, screenX, screenY + GamePanel.tileSize * (9 + 2 * i), GamePanel.tileSize * 8, GamePanel.tileSize * 2, null);
                    }
                    g.drawImage(tiles[0].image, screenX, screenY + GamePanel.tileSize * (9 + 2 * (len)), GamePanel.tileSize * 8, GamePanel.tileSize * 4, null);
                } else {
                    g.drawImage(tiles[2].image, screenX, screenY + GamePanel.tileSize * 4, GamePanel.tileSize * 8, GamePanel.tileSize * 2, null);
                    g.drawImage(tiles[2].image, screenX, screenY + GamePanel.tileSize * 6, GamePanel.tileSize * 8, GamePanel.tileSize * 2, null);
                    for (int i = 0; i < len; i++) {
                        g.drawImage(tiles[2].image, screenX, screenY + GamePanel.tileSize * (8 + 2 * i), GamePanel.tileSize * 8, GamePanel.tileSize * 2, null);
                    }
                    g.drawImage(tiles[0].image, screenX, screenY + GamePanel.tileSize * (8 + 2 * (len)), GamePanel.tileSize * 8, GamePanel.tileSize * 4, null);

                }
            } else {
                g.drawImage(tiles[1].image, screenX, screenY, GamePanel.tileSize * 8, GamePanel.tileSize * 4, null);
                if (isBreak) {
                    for (int i = 0; i < len; i++) {
                        g.drawImage(tiles[2].image, screenX, screenY + GamePanel.tileSize * (4 + 2 * i), GamePanel.tileSize * 8, GamePanel.tileSize * 2, null);
                    }
                    g.drawImage(tiles[3].image, screenX, screenY + GamePanel.tileSize * (4 + 2 * len), GamePanel.tileSize * 8, GamePanel.tileSize * 5, null);
                    g.drawImage(tiles[0].image, screenX, screenY + GamePanel.tileSize * (9 + 2 * len), GamePanel.tileSize * 8, GamePanel.tileSize * 4, null);

                } else {
                    for (int i = 0; i < len + 2; i++) {
                        g.drawImage(tiles[2].image, screenX, screenY + GamePanel.tileSize * (4 + 2 * i), GamePanel.tileSize * 8, GamePanel.tileSize * 2, null);
                    }
                    g.drawImage(tiles[0].image, screenX, screenY + GamePanel.tileSize * (8 + 2 * len), GamePanel.tileSize * 8, GamePanel.tileSize * 4, null);

                }
            }


        } // end if

    } // end draw

}
