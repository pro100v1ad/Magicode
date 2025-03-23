package display;

import Entity.Player;
import game.BackGround;
import game.StructureSetter;
import graphics.Layer;
import graphics.TextureAtlas;
import main.Main;
import object.SuperObject;
import structure.Structure;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/*
В этом классе происходят все моменты
 */

public class GamePanel extends JComponent {

    public static final String TITLE = "Magicode";
    public static boolean running; // Отвечает за то, что запущена игра или нет
    public static int isUpgrade = 1;

    public static boolean[] keys = new boolean[256]; // Содержит список состояния нажатия всех необходимых клавиш
    public static int scroll = 0;

    public static final int MAS_HEIGHT = 45;
    public static final int MAS_WIDTH = 72;

    public static final int originalTileSize = 16;
    public static double scale = 1;
    public static int tileSize = (int)(originalTileSize*scale);
    public static int WIDTH = MAS_WIDTH*originalTileSize;
    public static int HEIGHT = MAS_HEIGHT*originalTileSize;

    // Настройка FPS UPS
    public static final float UPDATE_RATE = 40.0f;
    public static final float DRAW_RATE = 60.0f;
    public static final float UPDATE_RATE_Speed = UPDATE_RATE/100;
    //    public static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;
    public static final long IDLE_TIME = 1;
// Конец настройки FPS UPS

    // Настройки карты мира
    public static final int maxWorldCol = 300;
    public static final int maxWorldRow = 250;
    public final int worldWidth = tileSize*maxWorldCol;
    public final int worldHeight = tileSize*maxWorldRow;
    public static Layer[][] worldMap = new Layer[maxWorldRow][maxWorldCol];
    public static int whoHaveCollision[] = new int[100];
    // Конец настройки карты мира
// Объявление классов необходимых для работы игры
    private Thread thread1;
    private Thread thread2;
    private BufferedImage image;
    private Graphics2D g;
    private Sound sound = new Sound();
// Конец объявления классов необходимых для работы игры

    // Объявление классов Необходимых в процессе разработки
    public TextureAtlas textureAtlas = new TextureAtlas(8, 10);
    public BackGround backGround = new BackGround(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public StructureSetter sSetter = new StructureSetter(this);
    public Player player = new Player(this);
    public SuperObject obj[] = new SuperObject[10];
//    public Collision collision = new Collision();


// Конец объявления классов Необходимых в процессе разработки

    public GamePanel() { // Конструктор (что-то делает)
        super();
//        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
//        WIDTH = (int)dimension.getWidth()*3/4;
//        HEIGHT = (int)dimension.getHeight()*3/4;


        setPreferredSize(new Dimension(WIDTH, HEIGHT)); // устанавливает размеры окна приложения
        setFocusable(true); // ??
        requestFocus(); // ??

        addKeyListener(new Listeners(this)); // Добавляет возможность считывать клавиши
//        addMouseWheelListener(new Listeners(this)); // Добавляет возможность считывать колесико мыши



    }

    public void setupGame() {
        aSetter.setObject();

        playMusic(0);
    }

    public void run1() { // Тут вся логика FPS и UPS, в подробности лучше не вдаваться

        int fps = 0;
        int upd = 0;
        int updl = 0;

        long count = 0;
        float deltaUpdate = 0;
        float deltaDraw = 0;
        long lastTime = Time.get();

        // Интервалы для update и draw
        final float UPDATE_INTERVAL = (float)Time.SECOND / UPDATE_RATE; // 20 раз в секунду
        final float DRAW_INTERVAL = Time.SECOND / DRAW_RATE; // 60 раз в секунду

        image = new BufferedImage(WIDTH, HEIGHT, 1);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        while (running) {
            long now = Time.get();
            long elapsedTime = now - lastTime;
            lastTime = now;

            count += elapsedTime;

            // Обновление логики
            deltaUpdate += (elapsedTime / UPDATE_INTERVAL);
            while (deltaUpdate > 1) {
                update1();
                upd++;
                deltaUpdate--;
            }

            // Отрисовка
            deltaDraw += (elapsedTime / DRAW_INTERVAL);
            if (deltaDraw > 1) {
                render1();
//                System.out.println("Render!");
                fps++;
                deltaDraw--;
            }

            // Если не нужно ничего обновлять или отрисовывать, спим
            if (deltaUpdate <= 1 && deltaDraw <= 1) {
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Обновление заголовка окна
            if (count >= Time.SECOND) {
                Main.setTitle(TITLE + " || Fps: " + fps + " | Upd: " + upd + " | Updl: " + updl);
                upd = 0;
                fps = 0;
                updl = 0;
                count = 0;
            }

        } // end while

    }

    public void run2() {

        int upd = 0;
        int updl = 0;

        long count = 0;
        float deltaUpdate = 0;
        long lastTime = Time.get();

        // Интервалы для update и draw
        final float UPDATE_INTERVAL = (float)Time.SECOND / UPDATE_RATE; // 20 раз в секунду

        image = new BufferedImage(WIDTH, HEIGHT, 1);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        while(running) {
            long now = Time.get();
            long elapsedTime = now - lastTime;
            lastTime = now;

            count += elapsedTime;

            // Обновление логики
            deltaUpdate += (elapsedTime / UPDATE_INTERVAL);
            while (deltaUpdate > 1) {
                update2();
                upd++;
                deltaUpdate--;
            }

            // Если не нужно ничего обновлять, спим
            if (deltaUpdate <= 1) {
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } // end while
    }


    public void start() { // Вспомогательная функция для запуска потока

        if(running) return;
        running = true;

        thread2 = new Thread(this::run2);
        thread2.start();

        thread1 = new Thread(this::run1);
        thread1.start(); // Вызывает функцию run(), она выше.


    }

    public void update1() {
            player.update();

    }

    public void update2() {
//        if (!player.direction.equals("null")) {
//            // Обновляем карту коллизий только если игрок двигался
//            collision.resetCollisionMap(maxWorldCol * tileSize, maxWorldRow * tileSize);
//            collision.loadCollisionMapFromPlayerPosition((int) player.worldX, (int) player.worldY + tileSize * 3, tileSize * 2, tileSize * 1);
//
//            // Проверяем только область вокруг игрока (10x10 тайлов)
//            int playerTileX = (int) player.worldX / tileSize;
//            int playerTileY = (int) player.worldY / tileSize;
//            int range = 10; // Проверяем область 10x10 тайлов вокруг игрока
//
//            for (int i = playerTileY - range; i < playerTileY + range; i++) {
//                for (int j = playerTileX - range; j < playerTileX + range; j++) {
//                    if (i >= 0 && i < maxWorldRow && j >= 0 && j < maxWorldCol) {
//                        if (worldMap[i][j].getCollision()) {
//                            collision.loadCollisionMapFromTiles(i, j, tileSize);
//                        }
//                    }
//                }
//            }
//
//            // Обновляем коллизии для структур
//            for (Structure s : sSetter.getStructure()) {
//                if (s != null) {
//                    collision.loadCollisionMapFromStructure(s);
//                }
//            }
//
//            // Проверка коллизий для игрока
//
//        }
    }

    // Вспомогательный метод для проверки коллизий игрока

    public void render1(){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        backGround.draw(g);

        for(int i = 0; i < obj.length; i++) {
            if(obj[i] != null) {
                obj[i].draw(g, this);
            }
        }


        sSetter.draw(g);
        player.draw(g);
        draw();
    }

    public void draw() { // Просто рисует полученный результат
        Graphics g2 = this.getGraphics();
        if(g2 != null) {
            g2.drawImage(image, 0, 0, null);
            g2.dispose();
        }
    }

    public void playMusic(int i) {

        sound.setFile(i);
        sound.play();
        sound.loop();

    }

    public void stopMusic() {

        sound.stop();
    }

    public void playSE(int i) {

        sound.setFile(i);
        sound.play();
    }

}
