package Entity;

import display.GamePanel;


public class Entity {
    public double worldX, worldY; // Точные координаты
    public int screenX, screenY;  // Координаты на экране
    public double speed;
    public String direction = "null";

    public int collisionX;
    public int collisionY;
    public int collisionWidth = GamePanel.tileSize;
    public int collisionHeight = GamePanel.tileSize;

    public int collisionCode = 2; // Уникальный код для игрока


}
