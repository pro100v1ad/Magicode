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
        textures[0][0].loadTexture("/res/tiles/grass/grassCenter.png");
        textures[0][1].loadTexture("/res/tiles/grass/grassUp.png");
        textures[0][2].loadTexture("/res/tiles/grass/grassDown.png");
        textures[0][3].loadTexture("/res/tiles/grass/grassLeft.png");
        textures[0][4].loadTexture("/res/tiles/grass/grassRight.png");
        textures[0][5].loadTexture("/res/tiles/grass/grassUpLeft.png");
        textures[0][6].loadTexture("/res/tiles/grass/grassUpRight.png");
        textures[0][7].loadTexture("/res/tiles/grass/grassDownLeft.png");
        textures[0][8].loadTexture("/res/tiles/grass/grassDownRight.png");

        textures[1][0].loadTexture("/res/tiles/water/waterCenter.png");
        textures[1][1].loadTexture("/res/tiles/water/waterUp.png");
        textures[1][2].loadTexture("/res/tiles/water/waterDown.png");
        textures[1][3].loadTexture("/res/tiles/water/waterLeft.png");
        textures[1][4].loadTexture("/res/tiles/water/waterRight.png");
        textures[1][5].loadTexture("/res/tiles/water/waterUpLeft.png");
        textures[1][6].loadTexture("/res/tiles/water/waterUpRight.png");
        textures[1][7].loadTexture("/res/tiles/water/waterDownLeft.png");
        textures[1][8].loadTexture("/res/tiles/water/waterDownRight.png");

        textures[2][0].loadTexture("/res/structure/bridge/horizontal/bridgeStart.png");
        textures[2][1].loadTexture("/res/structure/bridge/horizontal/bridgeEnd.png");
        textures[2][2].loadTexture("/res/structure/bridge/horizontal/bridgePart.png");
        textures[2][3].loadTexture("/res/structure/bridge/horizontal/bridgeBreak.png");

        textures[3][0].loadTexture("/res/structure/bridge/vertical/bridgeStart.png");
        textures[3][1].loadTexture("/res/structure/bridge/vertical/bridgeEnd.png");
        textures[3][2].loadTexture("/res/structure/bridge/vertical/bridgePart.png");
        textures[3][3].loadTexture("/res/structure/bridge/vertical/bridgeBreak.png");

        textures[4][0].loadTexture("/res/player/up/playerUp1.png");
        textures[4][1].loadTexture("/res/player/up/playerUp2.png");
        textures[4][2].loadTexture("/res/player/up/playerUp3.png");
        textures[4][3].loadTexture("/res/player/up/playerUp4.png");

        textures[4][4].loadTexture("/res/player/down/playerDown1.png");
        textures[4][5].loadTexture("/res/player/down/playerDown2.png");
        textures[4][6].loadTexture("/res/player/down/playerDown3.png");
        textures[4][7].loadTexture("/res/player/down/playerDown4.png");

        textures[4][8].loadTexture("/res/player/left/playerLeft1.png");
        textures[4][9].loadTexture("/res/player/left/playerLeft2.png");
        textures[4][10].loadTexture("/res/player/left/playerLeft3.png");
        textures[4][11].loadTexture("/res/player/left/playerLeft4.png");

        textures[4][12].loadTexture("/res/player/right/playerRight1.png");
        textures[4][13].loadTexture("/res/player/right/playerRight2.png");
        textures[4][14].loadTexture("/res/player/right/playerRight3.png");
        textures[4][15].loadTexture("/res/player/right/playerRight4.png");

        textures[5][0].loadTexture("/res/gui/GUI_player.png");
        textures[5][1].loadTexture("/res/gui/player/healthBar.png");
        textures[5][2].loadTexture("/res/gui/player/healthPart.png");
        textures[5][3].loadTexture("/res/gui/player/healthPartEnd1.png");
        textures[5][4].loadTexture("/res/gui/player/healthPartEnd2.png");

        textures[6][0].loadTexture("/res/gui/menu/menuButtonPassive.png");
        textures[6][1].loadTexture("/res/gui/menu/menuButtonActive.png");

    }


}