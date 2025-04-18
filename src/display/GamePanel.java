package display;

import Entity.Player;
import game.BackGround;
import game.StructureSetter;
import graphics.TextureAtlas;
import gui.*;
import gui.Code.CodeCompiler;
import main.Main;
import object.ObjectSetter;
import object.SuperObject;
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
    public static enum GameState {
        StartMenu,
        Game,
        GameMenu;
    }
    public enum CodeCompilerState {
        Open,
        Close;
    }
    public GameState state = GameState.StartMenu;
    public CodeCompilerState stateCompiler = CodeCompilerState.Close;

    public static boolean[] keys = new boolean[256]; // Содержит список состояния нажатия всех необходимых клавиш
    public static boolean[] mouseButtons = new boolean[3]; // Для левой, средней и правой кнопок
    public static boolean mouseClick = false;
    public static int mouseX, mouseY; // У вас они уже есть
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
    public static final int worldWidth = tileSize*maxWorldCol;
    public static final int worldHeight = tileSize*maxWorldRow;
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
    public Listeners listeners = new Listeners(this);

    public Collision collision = new Collision(worldWidth, worldHeight, this);
    public Interaction interaction = new Interaction(worldWidth, worldHeight, this);

    public TextureAtlas textureAtlas = new TextureAtlas(8, 16);
    public BackGround backGround = new BackGround(this);
    public StructureSetter sSetter = new StructureSetter(this);
    public ObjectSetter oSetter = new ObjectSetter(this);
    public Player player = new Player(this);

    
    //GUI
    public MessageBox messageBox = new MessageBox("Нажми на F, чтобы подобрать!");
    public GUI_Player guiPlayer = new GUI_Player(this);
    public GUI_Menu guiMenu = new GUI_Menu(this);
    public GUI_StartMenu guiStartMenu = new GUI_StartMenu(this);


    public CodeCompiler codeCompiler = new CodeCompiler(this); // TEMP, потом будет создаваться при необходимости
// Конец объявления классов Необходимых в процессе разработки

    public GamePanel() { // Конструктор (что-то делает)
        super();



        setPreferredSize(new Dimension(WIDTH, HEIGHT)); // устанавливает размеры окна приложения
        setFocusable(true);
        requestFocus();
        addKeyListener(listeners); // Добавляет возможность считывать клавиши
        addMouseListener(listeners);
        addMouseMotionListener(listeners);
        addMouseWheelListener(listeners);


    }

    public void setupGame() {
        messageBox.setPosition(WIDTH/2, HEIGHT/3); // Устанавливаем позицию
        messageBox.setVisible(false);
//        playMusic(0);
    }

    public void checkClick() {
        guiPlayer.setClickOnMenu(true);
        guiMenu.setClickOnMenu(true);
        guiStartMenu.setClickOnMenu(true);
        codeCompiler.setClickOnMenu(true);
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


    public void start() {
        if(running) return;
        running = true;

        thread1 = new Thread(this::run1);
        thread2 = new Thread(this::run2);
        thread1.start();
        thread2.start();
    }

    public void update1() {
        if(state.equals(GameState.Game)) {
            player.update();
        }

    }

    public void update2() {
        if(state.equals(GameState.Game)) {
            collision.loadObject(oSetter.obj);
            messageBox.update();
            // В методе update() или обработке ввода
            SuperObject interactObj = interaction.isPlayerInInteractionZone();
            if (interactObj != null) {
//            System.out.println("Игрок может взаимодействовать с " + interactObj.getName() + interactObj.getInteractionCode());
                messageBox.setVisible(true); // Делаем видимым
                // Обработка взаимодействия
                if (keys[5]) {
                    oSetter.removeObject(interactObj);
                }
            } else {
                messageBox.setVisible(false); // Делаем невидимым
            }
        } else if(state.equals(GameState.GameMenu)) {
            guiMenu.update();
        } else if(state.equals(GameState.StartMenu)) {
            guiStartMenu.update();
        }

        if(codeCompiler != null && stateCompiler == CodeCompilerState.Open) {
            codeCompiler.update();
        }
        // Теперь здесь можно обрабатывать другие сущности
    }
    public void render1(){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        backGround.draw(g);

            // Рисуем объекты
        oSetter.draw(g); // Разобраться почему не отображаются на экране!! и сделать коллизию
        sSetter.draw(g);
        player.draw(g);

            // Для отладки можно временно включить:
//        collision.drawEntity(g);
//        collision.drawTiles(g);
//        collision.drawCollision(g);
//        interaction.drawInteractionZones(g);
        // Рисование GUI
        messageBox.draw(g);
        guiPlayer.draw(g);
        guiMenu.draw(g);
        guiStartMenu.draw(g);

        if(codeCompiler != null) {
            codeCompiler.draw(g);
        }
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
