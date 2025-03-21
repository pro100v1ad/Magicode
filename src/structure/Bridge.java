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
    private boolean isBreak;
    private String direction;

    private GamePanel gp;

    public Bridge(GamePanel gp, int bridgeLength, String direction, boolean isBreak, int posX, int posY) {

        this.len = bridgeLength;
        this.isBreak = isBreak;
        this.direction = direction;
        this.posX = posX;
        this.posY = posY;
        this.gp = gp;

        loadTextures();

    }

    private void loadTextures() {
        tiles = new Tiles[4];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tiles(); // Initialize each element
        }

        if(direction.equals("horizontal")){
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

    public void draw(Graphics2D g) { // Дорисовать направления RIGHT и LEFT
        if(direction.equals("horizontal")) {
            g.drawImage(tiles[0].image, posX, posY, GamePanel.tileSize*5, GamePanel.tileSize*8, null);
            if(isBreak) {
                g.drawImage(tiles[3].image, posX + GamePanel.tileSize*5, posY, GamePanel.tileSize*5, GamePanel.tileSize*8, null);
                for (int i = 0; i < len; i++) {
                    g.drawImage(tiles[2].image, posX + GamePanel.tileSize*(10 + 2*i), posY, GamePanel.tileSize*2, GamePanel.tileSize*8, null);
                }
                g.drawImage(tiles[1].image, posX + GamePanel.tileSize*(10 + 2*(len)), posY, GamePanel.tileSize*4, GamePanel.tileSize*8, null);
            }
            else {
                g.drawImage(tiles[2].image, posX + GamePanel.tileSize*5, posY, GamePanel.tileSize*2, GamePanel.tileSize*8, null);
                for (int i = 0; i < len; i++) {
                    g.drawImage(tiles[2].image, posX + GamePanel.tileSize*(7 + 2*i), posY, GamePanel.tileSize*2, GamePanel.tileSize*8, null);
                }
                g.drawImage(tiles[1].image, posX + GamePanel.tileSize*(7 + 2*(len)), posY, GamePanel.tileSize*4, GamePanel.tileSize*8, null);
            }
        }
    }



}
