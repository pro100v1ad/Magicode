package utils;

import Entity.Entity;
import display.GamePanel;
/*
Херня непонятная, скоро поменяю
 */
public class CollisionCheker {
    GamePanel gp;
    public CollisionCheker(GamePanel gp) {
        this.gp = gp;
    }

//    public void checkTile(Entity entity) {
//
//        int entityLeftWorldX = (int)entity.worldX + entity.solidArea.x;
//        int entityRightWorldX = (int)entity.worldX + entity.solidArea.x + entity.solidArea.width;
//        int entityTopWorldY = (int)entity.worldY + entity.solidArea.y;
//        int entityBottomWorldY = (int)entity.worldY + entity.solidArea.y + entity.solidArea.height;
//
//        int entityLeftCol = entityLeftWorldX/GamePanel.tileSize;
//        int entityRightCol = entityRightWorldX/GamePanel.tileSize;
//        int entityTopRow = entityTopWorldY/GamePanel.tileSize;
//        int entityBottomRow = entityBottomWorldY/GamePanel.tileSize;
//
//        int tileNum1, tileNum2;
//
//        if(GamePanel.keys[0]) {
//            entityTopRow = (int)(entityTopWorldY - entity.speed)/GamePanel.tileSize;
//            tileNum1 = GamePanel.worldMap[entityLeftCol][entityTopRow];
//            tileNum2 = GamePanel.worldMap[entityRightCol][entityTopRow];
//            if(gp.backGround.tiles[tileNum1].collision == true || gp.backGround.tiles[tileNum2].collision == true) {
//                entity.collisionOn = true;
//            }
//        }
//        else if(GamePanel.keys[1]) {
//            entityLeftCol = (int)(entityLeftWorldX - entity.speed)/GamePanel.tileSize;
//            tileNum1 = GamePanel.worldMap[entityLeftCol][entityTopRow];
//            tileNum2 = GamePanel.worldMap[entityLeftCol][entityBottomRow];
//            if(gp.backGround.tiles[tileNum1].collision == true || gp.backGround.tiles[tileNum2].collision == true) {
//                entity.collisionOn = true;
//            }
//        }
//        else if(GamePanel.keys[2]) {
//            entityBottomRow = (int)(entityBottomWorldY + entity.speed)/GamePanel.tileSize;
//            tileNum1 = GamePanel.worldMap[entityLeftCol][entityBottomRow];
//            tileNum2 = GamePanel.worldMap[entityRightCol][entityBottomRow];
//            if(gp.backGround.tiles[tileNum1].collision == true || gp.backGround.tiles[tileNum2].collision == true) {
//                entity.collisionOn = true;
//            }
//        }
//        else if(GamePanel.keys[3]) {
//            entityRightCol = (int)(entityRightWorldX + entity.speed)/GamePanel.tileSize;
//            tileNum1 = GamePanel.worldMap[entityRightCol][entityTopRow];
//            tileNum2 = GamePanel.worldMap[entityRightCol][entityBottomRow];
//            if(gp.backGround.tiles[tileNum1].collision == true || gp.backGround.tiles[tileNum2].collision == true) {
//                entity.collisionOn = true;
//            }
//        }
//    }
public void checkTile(Entity entity) {
    // Определяем границы сущности
    int entityLeftWorldX = (int) (entity.worldX + entity.solidArea.x);
    int entityRightWorldX = (int) (entity.worldX + entity.solidArea.x + entity.solidArea.width);
    int entityTopWorldY = (int) (entity.worldY + entity.solidArea.y);
    int entityBottomWorldY = (int) (entity.worldY + entity.solidArea.y + entity.solidArea.height);

    int entityLeftCol = entityLeftWorldX / GamePanel.tileSize;
    int entityRightCol = entityRightWorldX / GamePanel.tileSize;
    int entityTopRow = entityTopWorldY / GamePanel.tileSize;
    int entityBottomRow = entityBottomWorldY / GamePanel.tileSize;

    // Проверка перемещения вверх
    if (gp.keys[0]) { // UP
        entityTopRow = (int) (entityTopWorldY - entity.speed) / GamePanel.tileSize;
        checkCollision(entity, entityLeftCol, entityRightCol, entityTopRow);
    }
    // Проверка перемещения влево
    else if (gp.keys[1]) { // LEFT
        entityLeftCol = (int) (entityLeftWorldX - entity.speed) / GamePanel.tileSize;
        checkCollision(entity, entityLeftCol, entityLeftCol, entityTopRow);
    }
    // Проверка перемещения вниз
    else if (gp.keys[2]) { // DOWN
        entityBottomRow = (int) (entityBottomWorldY + entity.speed) / GamePanel.tileSize;
        checkCollision(entity, entityLeftCol, entityRightCol, entityBottomRow);
    }
    // Проверка перемещения вправо
    else if (gp.keys[3]) { // RIGHT
        entityRightCol = (int) (entityRightWorldX + entity.speed) / GamePanel.tileSize;
        checkCollision(entity, entityRightCol, entityRightCol, entityTopRow);
    }
}

    private void checkCollision(Entity entity, int leftCol, int rightCol, int row) {
        // Проверка, находятся ли индексы в пределах карты
        if (isWithinBounds(leftCol, row) && isWithinBounds(rightCol, row)) {
            int tileNum1 = GamePanel.worldMap[leftCol][row];
            int tileNum2 = GamePanel.worldMap[rightCol][row];

            // Проверяем коллизию
            if (gp.backGround.tiles[tileNum1].collision || gp.backGround.tiles[tileNum2].collision) {
                entity.collisionOn = true;
            }
        }

        // Дополнительные проверки для углов
        if (isWithinBounds(leftCol, row + 1) || isWithinBounds(rightCol, row + 1)) {
            int tileNum1 = GamePanel.worldMap[leftCol][row + 1];
            int tileNum2 = GamePanel.worldMap[rightCol][row + 1];

            if (gp.backGround.tiles[tileNum1].collision || gp.backGround.tiles[tileNum2].collision) {
                entity.collisionOn = true;
            }
        }
    }

    private boolean isWithinBounds(int col, int row) {
        return col >= 0 && col < GamePanel.worldMap.length && row >= 0 && row < GamePanel.worldMap[0].length;
    }
}
