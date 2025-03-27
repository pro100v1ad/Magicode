package object;

import display.GamePanel;

import java.awt.*;

public class ObjectSetter {

    GamePanel gp;
    public SuperObject obj[];
    public final int N = 100;
    public ObjectSetter(GamePanel gp) {
        this.gp = gp;
        obj = new SuperObject[N];
        setObject();
        gp.interaction.loadInteractionMapFromObjects(obj);

    }

    public void setObject () {
        obj[0] = new OBJ_Book(gp, 1, GamePanel.tileSize*125, GamePanel.tileSize*125, GamePanel.tileSize*2, GamePanel.tileSize*2);
        obj[1] = new OBJ_Book(gp,1,GamePanel.tileSize*120, GamePanel.tileSize*130, GamePanel.tileSize*2, GamePanel.tileSize*2);

    }

    public void removeObject(SuperObject object) {
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] == object) {
                gp.interaction.clearObjectFromMap(obj[i]);
                gp.collision.clearObject(obj[i]);
                obj[i] = null;
                break;
            }
        }
    }

    public void draw(Graphics2D g) {
        for (SuperObject object : obj) {
            if (object != null) {
                object.draw(g); // Вызов метода draw для каждого объекта в массиве
            }
        }
    }

}
