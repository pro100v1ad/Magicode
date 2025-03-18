package utils;

import Tile.Tiles;
import display.GamePanel;

/*
Класс отвечающий за все что связанно с коллизией
 */

public class Collision {

    int[][] CollisionMap;
    int MapX, MapY;

    public Collision() {

    }

    public void resetCollisionMap(int MapX, int MapY) { // Очищает карту препятствий от всех препятствий и игрока
        CollisionMap = new int[MapY][MapX];
        this.MapX = MapX;
        this.MapY = MapY;
        for(int i = 0; i < MapY; i++) {
            for(int j = 0; j < MapX; j++) {
                CollisionMap[i][j] = 0;
            }
        }

        for(int i = 0; i < MapY; i++) {
            for(int j = 0; j < MapX; j++) {
                if(i == 0) CollisionMap[i][j] = 1;
                if(i == MapY-1) CollisionMap[i][j] = 1;
                if(j == 0) CollisionMap[i][j] = 1;
                if(j == MapX-1) CollisionMap[i][j] = 1;
            }
        }
    }

    public void loadCollisionMapFromTiles(int y, int x, int scale) { // Загружает на карту препятствий все препятствия

        for (int i = y * scale; i < (1 + y) * scale; i++) {
            for (int j = x * scale; j < (1 + x) * scale; j++) {
                try {
                    CollisionMap[i][j] = 1;
                }catch (Exception e) {
                    System.out.println("loadCollisionMapFromTiles ERROR" + i + " and  " + j + " !");
                }

            }
        }
    }

    public void loadCollisionMapFromPlayerPosition(int x, int y, int w, int h) { // Загружает на карту препятствий игрока
        for(int i = y; i < y + h; i++) {
            for(int j = x; j < x + w; j++) {
                try {
                    if(i < GamePanel.maxWorldRow*GamePanel.tileSize && j < GamePanel.maxWorldCol*GamePanel.tileSize) CollisionMap[i][j] = 2;
                } catch (Exception e) {
                    System.out.println("loadCollisionMapFromPlayerPosition ERROR");
                }

            }
        }
    }

    public boolean detectCollision(String direction, int x, int y, int w, int h) { // На основе существующей карты препятствий, проверяет можно ли походить.
        for(int i = y; i < y + h; i++) {
            for(int j = x; j < x + w; j++) {
                try {
                    if (i < GamePanel.maxWorldRow*GamePanel.tileSize && j < GamePanel.maxWorldCol*GamePanel.tileSize-1) {
                        if(!direction.equals("null") && CollisionMap[i][j] == 2) {
                            if(direction.equals("up") && CollisionMap[i-1][j] == 1) return true;
                            if(direction.equals("down") && CollisionMap[i+1][j] == 1) return true;
                            if(direction.equals("left") && CollisionMap[i][j-1] == 1) return true;
                            if(direction.equals("right") && CollisionMap[i][j+1] == 1) return true;
                        }
                    }

                } catch (Exception e) {
                    System.out.println("i: " + i + " and j: " + j);
                }

            }
        }
        return false;
    }

}
