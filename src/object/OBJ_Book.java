package object;

import javax.imageio.ImageIO;

import java.io.IOException;

import static utils.ResourceLoader.loadImage;

public class OBJ_Book extends SuperObject{


    public OBJ_Book(int i) {

        name = "Book";

        image = loadImage("objects/book" + i + ".png");

    }

}
