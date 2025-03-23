//package utils;
//
//import display.GamePanel;
//import structure.Bridge;
//import structure.Structure;
//
///*
//Класс отвечающий за все что связанно с коллизией
// */
//
//public class Collision {
//
//    public int[][] CollisionMap;
//    int MapX, MapY;
//
//
//    public void resetCollisionMap(int MapX, int MapY) { // Очищает карту препятствий от всех препятствий и игрока
//        CollisionMap = new int[MapY][MapX];
//        this.MapX = MapX;
//        this.MapY = MapY;
//        for(int i = 0; i < MapY; i++) {
//            for(int j = 0; j < MapX; j++) {
//                CollisionMap[i][j] = 0;
//            }
//        }
//
//        for(int i = 0; i < MapY; i++) {
//            for(int j = 0; j < MapX; j++) {
//                if(i == 0) CollisionMap[i][j] = 1;
//                if(i == MapY-1) CollisionMap[i][j] = 1;
//                if(j == 0) CollisionMap[i][j] = 1;
//                if(j == MapX-1) CollisionMap[i][j] = 1;
//            }
//        }
//    }
//
//    public void loadCollisionMapFromStructure(Structure struct) {
//        if(struct.getName().equals("bridge")) {
//            Bridge br = (Bridge) struct;
//            int x = br.getWorldX();
//            int y = br.getWorldY();
//            int w = br.getW();
//            int h = br.getH();
//
//            for (int i = y; i < y + h; i++) {
//                for (int j = x; j < x + w; j++) {
//                    try {
//                        if(br.getBreak()) CollisionMap[i][j] = 1;
//                        else {
//                            // ПОЧИНИТЬ ГРАНИЦЫ
//                            if(CollisionMap[i][j] == 1) CollisionMap[i][j] = 0;
//                            if(i == y+1) CollisionMap[i][j] = 1; //  в 6 слоев сделать
//                            if(i == y+h-1) CollisionMap[i][j] = 1;
//                        }
//                    }catch (Exception e) {
//                        System.out.println("loadCollisionMapFromTiles ERROR" + i + " and  " + j + " !");
//                    }
//
//                }
//            }
//        }
//    }
//
//    public void loadCollisionMapFromTiles(int y, int x, int scale) { // Загружает на карту препятствий все препятствия
//
//        for (int i = y * scale; i < (1 + y) * scale; i++) {
//            for (int j = x * scale; j < (1 + x) * scale; j++) {
//                try {
//                    if(CollisionMap[i][j] != 2) CollisionMap[i][j] = 1;
//                }catch (Exception e) {
//                    System.out.println("loadCollisionMapFromTiles ERROR" + i + " and  " + j + " !");
//                }
//            }
//        }
//    }
//
//    public void loadCollisionMapFromPlayerPosition(int x, int y, int w, int h) {
//        // Очищаем только область, где был игрок
//        for (int i = y; i < y + h; i++) {
//            for (int j = x; j < x + w; j++) {
//                if (i >= 0 && i < MapY && j >= 0 && j < MapX) {
//                    CollisionMap[i][j] = 0; // Очищаем старую позицию игрока
//                }
//            }
//        }
//
//        // Устанавливаем новую позицию игрока
//        for (int i = y; i < y + h; i++) {
//            for (int j = x; j < x + w; j++) {
//                if (i >= 0 && i < MapY && j >= 0 && j < MapX) {
//                    CollisionMap[i][j] = 2; // Новая позиция игрока
//                }
//            }
//        }
//    }
//
//    public boolean detectCollision(String direction, int x, int y, int w, int h) {
//        int startX = x;
//        int startY = y;
//        int endX = x + w;
//        int endY = y + h;
//
//        // Проверка коллизий в зависимости от направления
//        switch (direction) {
//            case "up":
//                startY -= 1; // Проверяем строку выше
//                endY = y;
//                break;
//            case "down":
//                startY = y + h; // Проверяем строку ниже
//                endY = y + h + 1;
//                break;
//            case "left":
//                startX -= 1; // Проверяем столбец слева
//                endX = x;
//                break;
//            case "right":
//                startX = x + w; // Проверяем столбец справа
//                endX = x + w + 1;
//                break;
//            default:
//                return false; // Если направление не указано, коллизий нет
//        }
//
//        // Проверяем только границы объекта
//        for (int i = startY; i < endY; i++) {
//            for (int j = startX; j < endX; j++) {
//                if (i >= 0 && i < MapY && j >= 0 && j < MapX && CollisionMap[i][j] == 1) {
//                    return true; // Коллизия обнаружена
//                }
//            }
//        }
//        return false; // Коллизий нет
//    }
//
//}
