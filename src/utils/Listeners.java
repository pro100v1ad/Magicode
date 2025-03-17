package utils;

import display.GamePanel;

import java.awt.event.*;
/*
Класс для считывания нажатий мышки и клавиатуры
 */
public class Listeners implements MouseListener, MouseMotionListener, KeyListener {

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

    }
}