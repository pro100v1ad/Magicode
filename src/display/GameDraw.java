//package display;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//
//public class GameDraw extends JPanel implements Runnable { // Используем JPanel вместо JComponent
//
//    private Thread thread;
//    private BufferedImage image;
//    private Graphics2D g;
//
//    private GamePanel gp;
//    private static final int FPS = 100; // Устанавливаем FPS равным 100
//    private static final long FRAME_TIME = 1000 / FPS; // Время одного кадра в миллисекундах
//
//    public GameDraw(GamePanel gp) {
//        super();
//        this.gp = gp;
//        setPreferredSize(new Dimension(GamePanel.WIDTH, GamePanel.HEIGHT)); // Устанавливаем размеры панели
//        setBackground(Color.BLACK); // Устанавливаем цвет фона
//    }
//
//    @Override
//    public void run() {
//        long lastTime = System.currentTimeMillis();
//        long delta;
//
//        while (GamePanel.running) {
//            long currentTime = System.currentTimeMillis();
//            delta = currentTime - lastTime;
//
//            if (delta >= FRAME_TIME) {
//                render(); // Вызов метода render() для обновления графики
//                lastTime = currentTime;
//            }
//
//            try {
//                Thread.sleep(Math.max(0, FRAME_TIME - delta)); // Ожидание до следующего кадра
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void start() { // Вспомогательная функция для запуска потока
//        if (!GamePanel.running) return;
//        thread = new Thread(this);
//        thread.start(); // Вызывает функцию run(), она выше.
//    }
//
//    public void render() { // Функция, которая вызывается 100 раз в секунду, в ней обновляется вся графика
//        if (image == null) {
//            image = new BufferedImage(GamePanel.WIDTH, GamePanel.HEIGHT, BufferedImage.TYPE_INT_ARGB);
//            g = (Graphics2D) image.getGraphics();
//        }
//
//        g.setColor(Color.BLACK);
//        g.fillRect(0, 0, getWidth(), getHeight());
//
//        gp.backGround.draw(g);
//        gp.player.draw(g);
//        draw();
//    }
//
//    public void draw() { // Просто рисует полученный результат
//        Graphics g2 = this.getGraphics();
//        g2.drawImage(image, 0, 0, null);
//        g2.dispose();
//
//    }
//}