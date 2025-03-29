package gui;

import java.awt.*;
import java.io.InputStream;

public class GUI {
    protected int screenX;
    protected int screenY;
    protected boolean clickOnMenu = false;
    protected Font my_font;


    public GUI() {

        try {
            InputStream is = getClass().getResourceAsStream("/res/font/my_font.ttf");
            if (is != null) {
                my_font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(36f); // Регистрируем и задаём размер
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки шрифта: " + e.getMessage());
            my_font = new Font("Arial", Font.PLAIN, 24); // Шрифт по умолчанию
        }

    }

    public void setClickOnMenu(boolean click) {
        this.clickOnMenu = click;
    }

    public void draw(Graphics2D g){}
}
