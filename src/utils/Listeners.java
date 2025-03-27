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

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
//        GamePanel.mouseX = e.getX();
//        GamePanel.mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        GamePanel.mouseX = e.getX();
//        GamePanel.mouseY = e.getY();
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