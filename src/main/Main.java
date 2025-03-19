package main;


import display.GamePanel;
import javax.swing.*;

/*
Этот класс основной...
 */
public class Main {
    public static JFrame f = new JFrame("");
    public static void main(String[] args) {
        GamePanel panel = new GamePanel();

        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setContentPane(panel);
//        f.setExtendedState(MAXIMIZED_BOTH);

        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setResizable(false);


        panel.setupGame();
        panel.start();
        System.out.println("Start");


    }

    public static void setTitle(String name) {
        f.setTitle(name);
    }
}
