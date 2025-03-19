package utils;

import display.GamePanel;
import object.OBJ_Book;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject () {
        gp.obj[0] = new OBJ_Book(1);
        gp.obj[0].worldX = 150;
        gp.obj[0].worldY = 150;

        gp.obj[1] = new OBJ_Book(1);
        gp.obj[1].worldX = 50;
        gp.obj[1].worldY = 50;

    }

}
