package utils;

import Entity.Entity;
import display.GamePanel;
import graphics.Layer;
import object.OBJ_Book;
import object.SuperObject;
import structure.Bridge;
import structure.Structure;

import java.awt.*;
/*
Класс отвечающий за все что связанно с коллизией
 */

public class Collision {
    private int[][] collisionMap; // true - есть коллизия
    private GamePanel gp;
    private int mapX, mapY;
    public Collision(int mapX, int mapY, GamePanel gp) {
        this.gp = gp;
        this.mapX = mapX;
        this.mapY = mapY;
        collisionMap = new int[mapY][mapX];

        for(int i = 0; i < mapY; i++) {
            for(int j = 0; j < mapX; j++) {
                collisionMap[i][j] = 0;
            }
        }

    }


    public void loadMap(Layer[][] worldMap, int row, int col) {
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                if(worldMap[i][j].getCollision()) {

                    for(int i2 = i*GamePanel.tileSize; i2 < (i+1)*GamePanel.tileSize; i2++) {
                        for(int j2 = j*GamePanel.tileSize; j2 < (j+1)*GamePanel.tileSize; j2++) {
                            try {
                                if(collisionMap[i2][j2] != 0) {
                                    System.out.println("Почему-то там уже стоит 1");
                                }
                                collisionMap[i2][j2] = 1;
                            } catch (Exception e) {
                                System.out.println("Выход за массив: " + i + " and " + j);
                            }

                        }
                    }

                }
            }
        }


    }

    public void loadStructure(Structure[] structures) {

        for(int i = 0; i < structures.length; i++) {
            if(structures[i] != null) {
                if(structures[i].getName().equals("bridge")) {
                    Bridge bridge = (Bridge) structures[i];

                    if(bridge.getBreak()) {
                        for(int row = bridge.getWorldY(); row < bridge.getWorldY()+ bridge.getH(); row++) {
                            for(int col = bridge.getWorldX(); col < bridge.getWorldX() + bridge.getW(); col++) {
                                collisionMap[row][col] = 1;
                            }
                        }
                    } else {
                        for(int row = bridge.getWorldY(); row < bridge.getWorldY()+ bridge.getH(); row++) {
                            for(int col = bridge.getWorldX(); col < bridge.getWorldX() + bridge.getW(); col++) {
                                if(bridge.getDirection().equals("left") || bridge.getDirection().equals("right")) {
                                    collisionMap[row][col] = 0;
                                    if (row <= bridge.getWorldY() + GamePanel.tileSize / 2) collisionMap[row][col] = 1;
                                    if (row >= bridge.getWorldY() + bridge.getH() - GamePanel.tileSize * 2 - 1) collisionMap[row][col] = 1;
                                } else {
                                    collisionMap[row][col] = 0;
                                    if (col <= bridge.getWorldX() + GamePanel.tileSize / 2) collisionMap[row][col] = 1;
                                    if (col >= bridge.getWorldX() + bridge.getW() - GamePanel.tileSize - 1) collisionMap[row][col] = 1;
                                }
                            }
                        }
                    }

                }
            }
        }

    }

    public void loadObject(SuperObject[] object) {

        for(int i = 0; i < object.length; i++) {
            if(object[i] != null) {

                if(object[i].getName().equals("Book")) {
                    OBJ_Book book = (OBJ_Book) object[i];
                    int x = book.worldX;
                    int y = book.worldY;
                    int w = book.collisionWidth;
                    int h = book.collisionHeight;

                    for (int row = y; row < y + h; row++) {
                        for(int col = x; col < x + w; col++) {
                            collisionMap[row][col] = 1;
                        }
                    }

                }

            }
        }

    }

    public void clearObject(SuperObject object) {

        if(object != null) {

            if(object.getName().equals("Book")) {
                OBJ_Book book = (OBJ_Book) object;
                int x = book.worldX;
                int y = book.worldY;
                int w = book.collisionWidth;
                int h = book.collisionHeight;

                for (int row = y; row < y + h; row++) {
                    for(int col = x; col < x + w; col++) {
                        collisionMap[row][col] = 0;
                    }
                }

            }

        }

    }

    public boolean checkCollisionUp(Entity entity) {
        int x = (int)entity.worldX;
        int y = (int)entity.worldY;
        int w = entity.collisionWidth;
        int h = entity.collisionHeight;
        int code = entity.collisionCode;
        boolean flag = false;
        for(int i = x; i < x + w; i++) {
//            if(collisionMap[y][i] != entity.collisionCode) System.out.println(" Разберись почему нет кода сущности тут " + entity.collisionCode);
            if(collisionMap[y + h - h/3+1][i] != entity.collisionCode) System.out.println(" Разберись почему нет кода сущности тут up" + entity.collisionCode);
            try{
//                if(collisionMap[y-1][i] == 1) flag = true;
                if(collisionMap[y + h - h/3-1][i] == 1) flag = true;
            }catch (Exception e) {
                System.out.println("Вышло за пределы коллизии массива");
            }
            if(flag) break;
        }
        if(!flag) {
            movePlayerPosition("up", x, y, w, h, code);
        }
        return !flag;
    }
    public boolean checkCollisionDown(Entity entity) {
        int x = (int)entity.worldX;
        int y = (int)entity.worldY;
        int w = entity.collisionWidth;
        int h = entity.collisionHeight;
        int code = entity.collisionCode;
        boolean flag = false;
        for(int i = x; i < x + w; i++) {
            if(collisionMap[y+h-1][i] != entity.collisionCode) System.out.println(" Разберись почему нет кода сущности тут down" + entity.collisionCode);
            try{
                if(collisionMap[y+h][i] == 1) flag = true;
            }catch (Exception e) {
                System.out.println("Вышло за пределы коллизии массива");
            }
            if(flag) break;
        }
        if(!flag) {
            movePlayerPosition("down", x, y, w, h, code);
        }
        return !flag;
    }
    public boolean checkCollisionRight(Entity entity) {
        int x = (int)entity.worldX;
        int y = (int)entity.worldY;
        int w = entity.collisionWidth;
        int h = entity.collisionHeight;
        int code = entity.collisionCode;
        boolean flag = false;
        for(int i = y; i < y + h; i++) {
            if(i > y + h - h/3) {
                if (collisionMap[i][x + w - 1] != entity.collisionCode)
                    System.out.println(" Разберись почему нет кода сущности тут right" + entity.collisionCode);
                try {
                    if (collisionMap[i][x + w] == 1) flag = true;
                } catch (Exception e) {
                    System.out.println("Вышло за пределы коллизии массива");
                }
                if (flag) break;
            }
        }
        if(!flag) {
            movePlayerPosition("right", x, y, w, h, code);
        }

        return !flag;
    }
    public boolean checkCollisionLeft(Entity entity) {
        int x = (int)entity.worldX;
        int y = (int)entity.worldY;
        int w = entity.collisionWidth;
        int h = entity.collisionHeight;
        int code = entity.collisionCode;
        boolean flag = false;
        for(int i = y; i < y + h; i++) {
            if(i > y + h - h/3) {
                if (collisionMap[i][x] != entity.collisionCode)
                    System.out.println(" Разберись почему нет кода сущности тут left" + entity.collisionCode);
                try {
                    if (collisionMap[i][x - 1] == 1) flag = true;
                } catch (Exception e) {
                    System.out.println("Вышло за пределы коллизии массива");
                }
                if (flag) break;
            }
        }
        if(!flag) {
            movePlayerPosition("left", x, y, w, h, code);
        }
        return !flag;
    }


    public void movePlayerPosition(String direction, int x, int y, int w, int h, int code) {

        for(int i = y; i < y + h; i++) {
            for(int j = x; j < x + w; j++) {
                if(collisionMap[i][j] == code) collisionMap[i][j] = 0;
            }
        }

        switch (direction) {
            case "up": if(y > 0) y--; break;
            case "down": if(y < mapY) y++; break;
            case "left": if(x > 0) x--; break;
            case "right": if(x < mapX) x++; break;
        }

        for(int i = y; i < y + h; i++) {
            for(int j = x; j < x + w; j++) {
                if(collisionMap[i][j] == 0 && i > y + h - h/3) collisionMap[i][j] = code;
            }
        }
//        System.out.println();
//        System.out.println();
//        for(int i = y-5; i < y + h +5; i++) {
//            for(int j = x-5; j < x + w +5; j++) {
//                System.out.print(collisionMap[i][j] + " ");
//            }
//            System.out.println();
//        }


    }

    public void drawEntity(Graphics2D g) {
        g.setColor(Color.YELLOW);

        // Перебираем всю карту коллизий
        for (int worldY = 0; worldY < GamePanel.worldHeight; worldY++) {
            for (int worldX = 0; worldX < GamePanel.worldWidth; worldX++) {

                // Если в этой точке есть коллизия сущности (например, игрока)
                if (collisionMap[worldY][worldX] == 2) {

                    // Преобразуем мировые координаты в экранные
                    int screenX = (int) (worldX - gp.player.worldX + gp.player.screenX);
                    int screenY = (int) (worldY - gp.player.worldY + gp.player.screenY);

                    // Проверяем, находится ли точка в зоне видимости камеры
                    if (worldX > gp.player.worldX - gp.player.screenX - 32 &&
                            worldX < gp.player.worldX + gp.player.screenX + 32 &&
                            worldY > gp.player.worldY - gp.player.screenY - 32 &&
                            worldY < gp.player.worldY + gp.player.screenY + 32) {

                        // Рисуем пиксель коллизии
                        g.fillRect(screenX, screenY, 1, 1);
                    }
                }
            }
        }
    }

    public void drawCollision(Graphics2D g) {
        g.setColor(Color.BLUE);

        // Перебираем всю карту коллизий
        for (int worldY = 0; worldY < GamePanel.worldHeight; worldY++) {
            for (int worldX = 0; worldX < GamePanel.worldWidth; worldX++) {

                // Если в этой точке есть коллизия сущности (например, игрока)
                if (collisionMap[worldY][worldX] == 1) {

                    // Преобразуем мировые координаты в экранные
                    int screenX = (int) (worldX - gp.player.worldX + gp.player.screenX);
                    int screenY = (int) (worldY - gp.player.worldY + gp.player.screenY);

                    // Проверяем, находится ли точка в зоне видимости камеры
                    if (worldX > gp.player.worldX - gp.player.screenX - 32 &&
                            worldX < gp.player.worldX + gp.player.screenX + 32 &&
                            worldY > gp.player.worldY - gp.player.screenY - 32 &&
                            worldY < gp.player.worldY + gp.player.screenY + 32) {

                        // Рисуем пиксель коллизии
                        g.fillRect(screenX, screenY, 1, 1);
                    }
                }
            }
        }
    }

    public void drawTiles(Graphics2D g) {
        g.setColor(Color.RED);

        // Перебираем все тайлы мира
        for (int worldRow = 0; worldRow < GamePanel.maxWorldRow; worldRow++) {
            for (int worldCol = 0; worldCol < GamePanel.maxWorldCol; worldCol++) {

                // Проверяем, есть ли у тайла коллизия
                if (gp.backGround.worldMap[worldRow][worldCol].getCollision()) {

                    // Мировые координаты тайла
                    int worldX = worldCol * GamePanel.tileSize;
                    int worldY = worldRow * GamePanel.tileSize;

                    // Преобразуем мировые координаты в экранные (относительно камеры)
                    int screenX = (int) (worldX - gp.player.worldX + gp.player.screenX);
                    int screenY = (int) (worldY - gp.player.worldY + gp.player.screenY);

                    // Проверяем, находится ли тайл в зоне видимости камеры
                    if (worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX &&
                            worldX - GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                            worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY &&
                            worldY - GamePanel.tileSize < gp.player.worldY + gp.player.screenY) {

                        // Рисуем квадрат коллизии
                        g.drawRect(screenX, screenY, GamePanel.tileSize, GamePanel.tileSize);
                    }
                }
            }
        }
    }
}