package gui;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class MessageBox extends GUI {
    private String message;
    private boolean visible;
    private float opacity;
    private int width = 200;
    private int height = 50;
    private int cornerRadius = 20;
    private Font font = new Font("Arial", Font.PLAIN, 16);

    public MessageBox(String message) {
        this.message = message;
        this.visible = true;
        this.opacity = 0f; // Начинаем с прозрачности 0 для анимации появления
    }

    public void update() {
        if (visible && opacity < 1f) {
            opacity += 0.05f;
            if (opacity > 1f) opacity = 1f;
        }
    }

    public void draw(Graphics2D g) {
        if (!visible || opacity <= 0f) return;

        // Сохраняем оригинальные настройки
        Graphics2D g2d = (Graphics2D)g.create();

        // Включаем сглаживание
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Устанавливаем прозрачность
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
        g2d.setComposite(alphaComposite);

        // Рисуем фон с закругленными углами
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(
                screenX, screenY, width, height, cornerRadius, cornerRadius);

        // Градиент для фона
        GradientPaint gradient = new GradientPaint(
                screenX, screenY, new Color(70, 70, 70),
                screenX, screenY + height, new Color(40, 40, 40));

        g2d.setPaint(gradient);
        g2d.fill(roundedRectangle);

        // Рисуем бордюр
        g2d.setColor(new Color(150, 150, 150));
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(roundedRectangle);

        // Настраиваем шрифт и цвет текста
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);

        // Разбиваем текст на строки, если он слишком длинный
        FontMetrics fm = g2d.getFontMetrics();
        String[] lines = splitMessage(message, fm, width - 20);

        // Рисуем текст
        int textY = screenY + 20;
        for (String line : lines) {
            int textX = screenX + (width - fm.stringWidth(line)) / 2;
            g2d.drawString(line, textX, textY);
            textY += fm.getHeight();
        }

        // Освобождаем ресурсы
        g2d.dispose();
    }

    private String[] splitMessage(String message, FontMetrics fm, int maxWidth) {
        // Простая реализация переноса строк
        StringBuilder sb = new StringBuilder();
        String[] words = message.split(" ");
        String currentLine = "";

        for (String word : words) {
            if (fm.stringWidth(currentLine + word) <= maxWidth) {
                currentLine += word + " ";
            } else {
                sb.append(currentLine.trim()).append("\n");
                currentLine = word + " ";
            }
        }
        sb.append(currentLine.trim());

        return sb.toString().split("\n");
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setPosition(int x, int y) {
        this.screenX = x;
        this.screenY = y;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
}