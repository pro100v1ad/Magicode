package display;

import Entity.Player;
import game.BackGround;
import main.Main;
import utils.CollisionCheker;
import utils.Listeners;
import utils.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
/*
В этом классе происходят все моменты
 */

public class GamePanel extends JComponent implements Runnable {

    public static final String TITLE = "Magicode";
    public static boolean running; // Отвечает за то, что запущена игра или нет

    public static boolean[] keys = new boolean[256]; // Содержит список состояния нажатия всех необходимых клавиш


    public static final int MAS_HEIGHT = 45;
    public static final int MAS_WIDTH = 72;

    public static final int originalTileSize = 16;
    public static final int scale = 1;
    public static final int tileSize = originalTileSize*scale;
    public static int WIDTH = MAS_WIDTH*originalTileSize;
    public static int HEIGHT = MAS_HEIGHT*originalTileSize;

// Настройка FPS UPS
    public static final float UPDATE_RATE = 100.0f;
    public static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;
    public static final long IDLE_TIME = 1;
// Конец настройки FPS UPS

// Настройки карты мира
    public static final int maxWorldCol = 50;
    public static final int maxWorldRow = 50;
    public final int worldWidth = tileSize*maxWorldCol;
    public final int worldHeight = tileSize*maxWorldRow;
    public static int[][] worldMap = new int[maxWorldRow][maxWorldCol];
// Конец настройки карты мира
// Объявление классов необходимых для работы игры
    private Thread thread;
    private BufferedImage image;
    private Graphics2D g;
// Конец объявления классов необходимых для работы игры

// Объявление классов Необходимых в процессе разработки
    public CollisionCheker cChecker = new CollisionCheker(this);
    public BackGround backGround = new BackGround(this);
    public Player player = new Player(this, backGround);
// Конец объявления классов Необходимых в процессе разработки

    public GamePanel() { // Конструктор (что-то делает)
        super();
//        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
//        WIDTH = (int)dimension.getWidth()*3/4;
//        HEIGHT = (int)dimension.getHeight()*3/4;

//        for(int i = 0; i < GamePanel.MAS_HEIGHT; i++) {
//            for(int j = 0; j < GamePanel.MAS_WIDTH; j++) {
//                GamePanel.worldMap[i][j] = 1;
//            }
//        }

        setPreferredSize(new Dimension(WIDTH, HEIGHT)); // устанавливает размеры окна приложения
        setFocusable(true); // ??
        requestFocus(); // ??

        addKeyListener(new Listeners()); // Добавляет возможность считывать клавиши
    }

    @Override
    public void run() { // Тут вся логика FPS и UPS, в подробности лучше не вдаваться

        int fps = 0;
        int upd = 0;
        int updl = 0;


        long count = 0;
        float delta = 0;
        long lastTime = Time.get();

        image = new BufferedImage(WIDTH, HEIGHT, 1);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        while (running) {
            long now = Time.get();

            long elapsedTime = now - lastTime;
            lastTime = now;

            count += elapsedTime;
            boolean render = false;
            delta += (elapsedTime / UPDATE_INTERVAL);
            while (delta > 1) {
                update();
                upd++;/////////
                delta--;
                if(render) {
                    updl++;
                } else {
                    render = true;
                }
            }

            if(render) {
                render();
                fps++;///////////
            }else {
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(count >= Time.SECOND) {
                Main.setTitle(TITLE + " || Fps: " + fps + " | Upd: " + upd + " | Updl: " + updl);
                upd = 0;
                fps = 0;
                updl = 0;
                count = 0;
            }

        } // end while

    }

    public void start() { // Вспомогательная функция для запуска потока

        if(running) return;
        running = true;
        thread = new Thread(this);
        thread.start(); // Вызывает функцию run(), она выше.
    }

    public void update() { // Функция, которая вызывается 100 раз в секунду, в ней обновляется вся логика
        player.update();
    }

    public void render(){ // Функция, которая вызывается 100 раз в секунду, в ней обновляется вся графика
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        backGround.draw(g);
        player.draw(g);
        draw();
    }

    public void draw() { // Просто рисует полученный результат
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();

    }

}
