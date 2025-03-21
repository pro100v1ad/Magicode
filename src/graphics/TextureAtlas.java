package graphics;

import utils.ResourceLoader;

import java.awt.image.BufferedImage;
/*
Тоже хз пока зач этот класс
 */
public class TextureAtlas {

    public Texture textures[][];

    public TextureAtlas(int row, int col) {
        textures = new Texture[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                textures[i][j] = new Texture();
            }
        }
        textures[0][0].loadTexture("tiles/grass/grassCenter.png");
        textures[0][1].loadTexture("tiles/grass/grassUp.png");
        textures[0][2].loadTexture("tiles/grass/grassDown.png");
        textures[0][3].loadTexture("tiles/grass/grassLeft.png");
        textures[0][4].loadTexture("tiles/grass/grassRight.png");
        textures[0][5].loadTexture("tiles/grass/grassUpLeft.png");
        textures[0][6].loadTexture("tiles/grass/grassUpRight.png");
        textures[0][7].loadTexture("tiles/grass/grassDownLeft.png");
        textures[0][8].loadTexture("tiles/grass/grassDownRight.png");

        textures[1][0].loadTexture("tiles/water/waterCenter.png");
        textures[1][1].loadTexture("tiles/water/waterUp.png");
        textures[1][2].loadTexture("tiles/water/waterDown.png");
        textures[1][3].loadTexture("tiles/water/waterLeft.png");
        textures[1][4].loadTexture("tiles/water/waterRight.png");
        textures[1][5].loadTexture("tiles/water/waterUpLeft.png");
        textures[1][6].loadTexture("tiles/water/waterUpRight.png");
        textures[1][7].loadTexture("tiles/water/waterDownLeft.png");
        textures[1][8].loadTexture("tiles/water/waterDownRight.png");

        textures[2][0].loadTexture("structure/bridge/horizontal/bridgeStart.png");
        textures[2][1].loadTexture("structure/bridge/horizontal/bridgeEnd.png");
        textures[2][2].loadTexture("structure/bridge/horizontal/bridgePart.png");
        textures[2][3].loadTexture("structure/bridge/horizontal/bridgeBreak.png");

        textures[3][0].loadTexture("structure/bridge/vertical/bridgeStart.png");
        textures[3][1].loadTexture("structure/bridge/vertical/bridgeEnd.png");
        textures[3][2].loadTexture("structure/bridge/vertical/bridgePart.png");
        textures[3][3].loadTexture("structure/bridge/vertical/bridgeBreak.png");
    }


}