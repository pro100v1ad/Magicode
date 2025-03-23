package object;

import utils.ResourceLoader;

import javax.imageio.ImageIO;

import java.io.IOException;



public class OBJ_Book extends SuperObject{

    ResourceLoader rs = new ResourceLoader();
    public OBJ_Book(int i) {

        name = "Book";

        image = rs.loadImage("/res/objects/book" + i + ".png");

    }

}
