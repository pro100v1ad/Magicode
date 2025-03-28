package utils;

import Entity.Player;
import display.GamePanel;

import java.awt.event.*;
import java.security.Key;

/*
Класс для считывания нажатий мышки и клавиатуры
 */
public class Listeners implements MouseListener, MouseMotionListener, KeyListener, MouseWheelListener {
    GamePanel gp;
    public Listeners(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // e.getButton() возвращает номер кнопки:
        // MouseEvent.BUTTON1 - левая кнопка
        // MouseEvent.BUTTON2 - средняя кнопка (колесо)
        // MouseEvent.BUTTON3 - правая кнопка

        if (e.getButton() == MouseEvent.BUTTON1) {
//            System.out.println("Левый клик на координатах: (" + e.getX() + ", " + e.getY() + ")");
            gp.checkClick();
            // Можно передать событие в GamePanel или Player

        }

//        if (e.getButton() == MouseEvent.BUTTON3) {
//            System.out.println("Правый клик!");
//        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Определяем какая кнопка была нажата
        if (e.getButton() == MouseEvent.BUTTON1) { // Левая кнопка
            GamePanel.mouseButtons[0] = true;
        } else if (e.getButton() == MouseEvent.BUTTON2) { // Средняя кнопка
            GamePanel.mouseButtons[1] = true;
        } else if (e.getButton() == MouseEvent.BUTTON3) { // Правая кнопка
            GamePanel.mouseButtons[2] = true;
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Определяем какая кнопка была отпущена
        if (e.getButton() == MouseEvent.BUTTON1) {
            GamePanel.mouseButtons[0] = false;
        } else if (e.getButton() == MouseEvent.BUTTON2) {
            GamePanel.mouseButtons[1] = false;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            GamePanel.mouseButtons[2] = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        GamePanel.mouseX = e.getX();
        GamePanel.mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        GamePanel.mouseX = e.getX();
        GamePanel.mouseY = e.getY();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        if(key == KeyEvent.VK_D) {
            GamePanel.keys[3] = true;
        }
        if(key == KeyEvent.VK_A) {
            GamePanel.keys[1] = true;
        }
        if(key == KeyEvent.VK_W) {
            GamePanel.keys[0] = true;
        }
        if(key == KeyEvent.VK_S) {
            GamePanel.keys[2] = true;
        }
        if(key == KeyEvent.VK_SHIFT) {
            GamePanel.keys[4] = true;
        }
        if(key == KeyEvent.VK_F) {
            GamePanel.keys[5] = true;
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();
        if(key == KeyEvent.VK_D) {
            GamePanel.keys[3] = false;
        }
        if(key == KeyEvent.VK_A) {
            GamePanel.keys[1] = false;
        }
        if(key == KeyEvent.VK_W) {
            GamePanel.keys[0] = false;
        }
        if(key == KeyEvent.VK_S) {
            GamePanel.keys[2] = false;
        }
        if(key == KeyEvent.VK_SHIFT) {
            GamePanel.keys[4] = false;
        }
        if(key == KeyEvent.VK_F) {
            GamePanel.keys[5] = false;
        }

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) { // Считывание колесика мыши
//        // Обработка прокрутки колесика мыши
//        int notches = e.getWheelRotation();
//        if (notches < 0) {
//            if(GamePanel.scale < 1.5) {
//                GamePanel.scale += 0.01;
//                gp.player.worldX += 0.1;
//                gp.player.worldY += 0.1;
//            }
////            System.out.println(GamePanel.scale);
//            GamePanel.scroll = 2;
//
//            // Здесь можно добавить логику для обработки прокрутки вверх
//        } else {
//            if(GamePanel.scale > 0.75) {
//                GamePanel.scale -= 0.01;
//                gp.player.worldX -= 1;
//                gp.player.worldY -= 1;
//            }
////            System.out.println(GamePanel.scale);
//            GamePanel.scroll = 2;
//            // Здесь можно добавить логику для обработки прокрутки вниз
//        }
    }
}