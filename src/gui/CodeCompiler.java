package gui;

import display.GamePanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class CodeCompiler extends GUI {
    private BufferedImage[] images;
    private int isButton; // 1, 2, 3, 0
    private GamePanel gp;

    // Текстовый редактор
    private List<String> codeLines = new ArrayList<>();
    private int cursorX = 0;
    private int cursorY = 0;
    private long lastCursorBlink;
    private boolean cursorVisible = true;
    private int viewOffsetX = 0;
    private int viewOffsetY = 0;
    private int charsPerLine = 29;
    private int visibleLines = 32;
    private int charWidth = 8;
    private int lineHeight = 16;
    private int cursorVerticalOffset = -12;
    private int textStartX = GamePanel.tileSize * 23;
    private int textStartY = GamePanel.tileSize * 6;

    // Настройки прокруток
    private int verticalScrollX = GamePanel.tileSize * 40;
    private int verticalScrollY = GamePanel.tileSize * 6;
    private int verticalScrollHeight = GamePanel.tileSize * 24;
    private int horizontalScrollX = GamePanel.tileSize * 23;
    private int horizontalScrollY = GamePanel.tileSize * 40;
    private int horizontalScrollWidth = GamePanel.tileSize * 15;

    // Стиль прокруток
    private int scrollBarWidth = 8;
    private int scrollBarThumbHeight = 30;
    private int scrollBarThumbWidth = 6;
    private Color scrollBarColor = new Color(80, 80, 80, 180);
    private Color scrollThumbColor = new Color(120, 120, 120, 200);
    private Color scrollThumbHoverColor = new Color(150, 150, 150, 220);
    private boolean verticalScrollHovered = false;
    private boolean horizontalScrollHovered = false;
    private boolean verticalScrollDragging = false;
    private boolean horizontalScrollDragging = false;
    private int scrollDragOffset = 0;

    public CodeCompiler(GamePanel gp) {
        this.gp = gp;
        images = new BufferedImage[10];
        images[0] = gp.textureAtlas.textures[7][0].getTexture();
        images[1] = gp.textureAtlas.textures[7][1].getTexture();

        codeLines.add("System.out.println(\"Hello Magicode!\");");
        codeLines.add("// Введите ваш код здесь");
        codeLines.add("// Используйте стрелки для навигации");

        resetCursorBlink();
    }

    public void update() {
        // Мигание курсора
        if (System.currentTimeMillis() - lastCursorBlink > 500) {
            cursorVisible = !cursorVisible;
            resetCursorBlink();
        }

        int x = GamePanel.mouseX;
        int y = GamePanel.mouseY;

        // Проверка наведения на прокрутки
        verticalScrollHovered = (x > verticalScrollX && x < verticalScrollX + scrollBarWidth &&
                y > verticalScrollY && y < verticalScrollY + verticalScrollHeight);
        horizontalScrollHovered = (x > horizontalScrollX && x < horizontalScrollX + horizontalScrollWidth &&
                y > horizontalScrollY && y < horizontalScrollY + scrollBarWidth);

        // Прокрутка колесиком
        if (GamePanel.scroll != 0) {
            viewOffsetY = Math.max(0, Math.min(codeLines.size() - visibleLines,
                    viewOffsetY - GamePanel.scroll));
            GamePanel.scroll = 0;
        }

        // Обработка кликов и перетаскивания
        if (GamePanel.mouseButtons[0]) {
            if (verticalScrollHovered && !verticalScrollDragging) {
                verticalScrollDragging = true;
                scrollDragOffset = y - getVerticalThumbY();
            }
            else if (horizontalScrollHovered && !horizontalScrollDragging) {
                horizontalScrollDragging = true;
                scrollDragOffset = x - getHorizontalThumbX();
            }
            else if (verticalScrollHovered) {
                handleVerticalTrackClick(y);
            }
            else if (horizontalScrollHovered) {
                handleHorizontalTrackClick(x);
            }
            else {
                handleButtonClick(x, y);
            }
        } else {
            verticalScrollDragging = false;
            horizontalScrollDragging = false;
        }

        // Перетаскивание вертикальной полосы
        if (verticalScrollDragging) {
            float relY = (float)(y - verticalScrollY - scrollDragOffset) /
                    (verticalScrollHeight - scrollBarThumbHeight);
            viewOffsetY = (int)(relY * (codeLines.size() - visibleLines));
            viewOffsetY = Math.max(0, Math.min(viewOffsetY, codeLines.size() - visibleLines));
        }

        // Перетаскивание горизонтальной полосы
        if (horizontalScrollDragging) {
            float relX = (float)(x - horizontalScrollX - scrollDragOffset) /
                    (horizontalScrollWidth - scrollBarThumbHeight);
            viewOffsetX = (int)(relX * (getMaxLineLength() - charsPerLine));
            viewOffsetX = Math.max(0, Math.min(viewOffsetX, getMaxLineLength() - charsPerLine));
        }

        // Обновление состояния кнопок
        updateButtonHoverState(x, y);
    }

    private void handleVerticalTrackClick(int y) {
        float relY = (float)(y - verticalScrollY - scrollBarThumbHeight/2) /
                (verticalScrollHeight - scrollBarThumbHeight);
        viewOffsetY = (int)(relY * (codeLines.size() - visibleLines));
        viewOffsetY = Math.max(0, Math.min(viewOffsetY, codeLines.size() - visibleLines));
    }

    private void handleHorizontalTrackClick(int x) {
        float relX = (float)(x - horizontalScrollX - scrollBarThumbHeight/2) /
                (horizontalScrollWidth - scrollBarThumbHeight);
        viewOffsetX = (int)(relX * (getMaxLineLength() - charsPerLine));
        viewOffsetX = Math.max(0, Math.min(viewOffsetX, getMaxLineLength() - charsPerLine));
    }

    private void handleButtonClick(int x, int y) {
        if (x > GamePanel.tileSize*46 && x < GamePanel.tileSize*50 &&
                y > GamePanel.tileSize*5 && y < GamePanel.tileSize*9) {
            gp.stateCompiler = GamePanel.CodeCompilerState.Close;
            gp.guiPlayer.setClickOnMenu(false);
            GamePanel.mouseButtons[0] = false;
        }
        else if (x > GamePanel.tileSize*41 && x < GamePanel.tileSize*48 &&
                y > GamePanel.tileSize*32 && y < GamePanel.tileSize*35) {
            resetCode();
            gp.guiPlayer.setClickOnMenu(false);
            GamePanel.mouseButtons[0] = false;
        }
        else if (x > GamePanel.tileSize*41 && x < GamePanel.tileSize*48 &&
                y > GamePanel.tileSize*37 && y < GamePanel.tileSize*40) {
            executeCode();
            gp.guiPlayer.setClickOnMenu(false);
            GamePanel.mouseButtons[0] = false;
        }
    }

    private void updateButtonHoverState(int x, int y) {
        if (x > GamePanel.tileSize*46 && x < GamePanel.tileSize*50 &&
                y > GamePanel.tileSize*5 && y < GamePanel.tileSize*9) {
            isButton = 1;
        }
        else if (x > GamePanel.tileSize*41 && x < GamePanel.tileSize*48 &&
                y > GamePanel.tileSize*32 && y < GamePanel.tileSize*35) {
            isButton = 2;
        }
        else if (x > GamePanel.tileSize*41 && x < GamePanel.tileSize*48 &&
                y > GamePanel.tileSize*37 && y < GamePanel.tileSize*40) {
            isButton = 3;
        }
        else {
            isButton = 0;
        }
    }

    public void handleKeyPress(int keyCode) {
        if (codeLines.isEmpty()) return;

        String currentLine = codeLines.get(cursorY);

        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                moveCursorLeft(currentLine);
                break;

            case KeyEvent.VK_RIGHT:
                moveCursorRight(currentLine);
                break;

            case KeyEvent.VK_UP:
                moveCursorUp();
                break;

            case KeyEvent.VK_DOWN:
                moveCursorDown();
                break;

            case KeyEvent.VK_ENTER:
                insertNewLine(currentLine);
                break;

            case KeyEvent.VK_BACK_SPACE:
                backspace(currentLine);
                break;

            case KeyEvent.VK_DELETE:
                delete(currentLine);
                break;
        }

        updateViewOffset();
        resetCursorBlink();
    }

    public void handleCharTyped(char c) {
        if (c >= 32 && c <= 126) { // Печатные символы
            if (codeLines.isEmpty()) {
                codeLines.add("");
            }

            String currentLine = codeLines.get(cursorY);
            codeLines.set(cursorY,
                    currentLine.substring(0, cursorX) +
                            c +
                            currentLine.substring(cursorX));
            cursorX++;

            updateViewOffset();
            resetCursorBlink();
        }
    }

    private void moveCursorLeft(String currentLine) {
        if (cursorX > 0) {
            cursorX--;
        } else if (cursorY > 0) {
            cursorY--;
            cursorX = codeLines.get(cursorY).length();
        }
    }

    private void moveCursorRight(String currentLine) {
        if (cursorX < currentLine.length()) {
            cursorX++;
        } else if (cursorY < codeLines.size() - 1) {
            cursorY++;
            cursorX = 0;
        }
    }

    private void moveCursorUp() {
        if (cursorY > 0) {
            cursorY--;
            cursorX = Math.min(cursorX, codeLines.get(cursorY).length());
        }
    }

    private void moveCursorDown() {
        if (cursorY < codeLines.size() - 1) {
            cursorY++;
            cursorX = Math.min(cursorX, codeLines.get(cursorY).length());
        }
    }

    private void insertNewLine(String currentLine) {
        String remainder = currentLine.substring(cursorX);
        codeLines.set(cursorY, currentLine.substring(0, cursorX));
        codeLines.add(cursorY + 1, remainder);
        cursorY++;
        cursorX = 0;
    }

    private void backspace(String currentLine) {
        if (cursorX > 0) {
            codeLines.set(cursorY,
                    currentLine.substring(0, cursorX - 1) +
                            currentLine.substring(cursorX));
            cursorX--;
        } else if (cursorY > 0) {
            String prevLine = codeLines.get(cursorY - 1);
            codeLines.set(cursorY - 1, prevLine + currentLine);
            codeLines.remove(cursorY);
            cursorY--;
            cursorX = prevLine.length();
        }
    }

    private void delete(String currentLine) {
        if (cursorX < currentLine.length()) {
            codeLines.set(cursorY,
                    currentLine.substring(0, cursorX) +
                            currentLine.substring(cursorX + 1));
        } else if (cursorY < codeLines.size() - 1) {
            String nextLine = codeLines.get(cursorY + 1);
            codeLines.set(cursorY, currentLine + nextLine);
            codeLines.remove(cursorY + 1);
        }
    }

    private void updateViewOffset() {
        // Горизонтальная прокрутка
        int maxHorizontalScroll = Math.max(0, getCurrentLineLength() - charsPerLine);
        if (cursorX < viewOffsetX) {
            viewOffsetX = Math.max(0, cursorX - 2);
        } else if (cursorX >= viewOffsetX + charsPerLine) {
            viewOffsetX = Math.min(maxHorizontalScroll, cursorX - charsPerLine + 2);
        }
        viewOffsetX = Math.max(0, Math.min(viewOffsetX, maxHorizontalScroll));

        // Вертикальная прокрутка
        int maxVerticalScroll = Math.max(0, codeLines.size() - visibleLines);
        if (cursorY < viewOffsetY) {
            viewOffsetY = Math.max(0, cursorY - 1);
        } else if (cursorY >= viewOffsetY + visibleLines) {
            viewOffsetY = Math.min(maxVerticalScroll, cursorY - visibleLines + 1);
        }
        viewOffsetY = Math.max(0, Math.min(viewOffsetY, maxVerticalScroll));
    }

    private int getCurrentLineLength() {
        return codeLines.isEmpty() ? 0 : codeLines.get(cursorY).length();
    }

    private int getMaxLineLength() {
        return codeLines.stream()
                .mapToInt(String::length)
                .max()
                .orElse(charsPerLine);
    }

    private int getVerticalThumbY() {
        if (codeLines.size() <= visibleLines) return verticalScrollY;
        float ratio = (float)viewOffsetY / (codeLines.size() - visibleLines);
        return verticalScrollY + (int)(ratio * (verticalScrollHeight - scrollBarThumbHeight));
    }

    private int getHorizontalThumbX() {
        int maxScrollX = Math.max(1, getMaxLineLength() - charsPerLine);
        float ratio = (float)viewOffsetX / maxScrollX;
        return horizontalScrollX + (int)(ratio * (horizontalScrollWidth - scrollBarThumbHeight));
    }

    public void draw(Graphics2D g) {
        if (gp.stateCompiler.equals(GamePanel.CodeCompilerState.Open)) {
            drawCompiler(g);
            drawCode(g);
            drawScrollBars(g);
        }
    }

    private void drawCompiler(Graphics2D g) {
        g.drawImage(images[0], 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);

        // Отрисовка кнопок с hover-эффектом
        g.setFont(my_font.deriveFont(16f));

        // Кнопка закрытия
        if (isButton == 1) {
            g.drawImage(images[1],
                    0,
                    0,
                    GamePanel.WIDTH,
                    GamePanel.HEIGHT,
                    null);
        }

        // Кнопка сброса
        g.setColor(isButton == 2 ? Color.ORANGE : Color.WHITE);
        g.drawString("Сбросить", GamePanel.tileSize * 42, GamePanel.tileSize * 32 + 28);

        // Кнопка запуска
        g.setColor(isButton == 3 ? Color.ORANGE : Color.WHITE);
        g.drawString("Запустить", GamePanel.tileSize * 42, GamePanel.tileSize * 37 + 28);
    }

    private void drawCode(Graphics2D g) {
        g.setFont(my_font.deriveFont(14f));
        FontMetrics fm = g.getFontMetrics();
        lineHeight = fm.getHeight();

        // Отрисовка видимых строк кода
        for (int i = 0; i < Math.min(visibleLines, codeLines.size() - viewOffsetY); i++) {
            int lineIndex = i + viewOffsetY;
            if (lineIndex < codeLines.size()) {
                String line = codeLines.get(lineIndex);
                int start = Math.min(viewOffsetX, line.length());
                int end = Math.min(viewOffsetX + charsPerLine, line.length());
                String visibleText = line.substring(start, end);

                g.setColor(Color.WHITE);
                g.drawString(
                        visibleText,
                        textStartX,
                        textStartY + i * lineHeight + fm.getAscent()
                );
            }
        }

        // Отрисовка курсора
        if (cursorVisible &&
                cursorY >= viewOffsetY &&
                cursorY < viewOffsetY + visibleLines) {

            String currentLine = codeLines.get(cursorY);
            int visibleStart = Math.min(viewOffsetX, currentLine.length());
            int cursorPosInVisible = cursorX - visibleStart;

            if (cursorPosInVisible >= 0 && cursorX <= currentLine.length()) {
                String textBeforeCursor = currentLine.substring(
                        visibleStart,
                        Math.min(cursorX, currentLine.length())
                );

                int textWidth = fm.stringWidth(textBeforeCursor);
                int cursorScreenX = textStartX + textWidth;
                int cursorScreenY = textStartY + (cursorY - viewOffsetY) * lineHeight + fm.getAscent();

                g.setColor(Color.YELLOW);
                g.fillRect(
                        cursorScreenX,
                        cursorScreenY + cursorVerticalOffset,
                        2,
                        lineHeight - fm.getDescent()
                );
            }
        }
    }

    private void drawScrollBars(Graphics2D g) {
        // Вертикальная полоса прокрутки
        g.setColor(scrollBarColor);
        g.fillRect(verticalScrollX, verticalScrollY, scrollBarWidth, verticalScrollHeight);

        // Горизонтальная полоса прокрутки
        g.fillRect(horizontalScrollX, horizontalScrollY, horizontalScrollWidth, scrollBarWidth);

        // Вертикальный бегунок
        int vThumbY = getVerticalThumbY();
        g.setColor(verticalScrollHovered || verticalScrollDragging ? scrollThumbHoverColor : scrollThumbColor);
        g.fillRoundRect(verticalScrollX + 1, vThumbY, scrollBarThumbWidth, scrollBarThumbHeight, 3, 3);

        // Горизонтальный бегунок
        int hThumbX = getHorizontalThumbX();
        g.fillRoundRect(hThumbX, horizontalScrollY + 1, scrollBarThumbHeight, scrollBarThumbWidth, 3, 3);
    }

    private void resetCursorBlink() {
        lastCursorBlink = System.currentTimeMillis();
        cursorVisible = true;
    }

    private void resetCode() {
        codeLines.clear();
        codeLines.add("System.out.println(\"Hello Magicode!\");");
        codeLines.add("// Введите ваш код здесь");
        cursorX = 0;
        cursorY = 0;
        viewOffsetX = 0;
        viewOffsetY = 0;
    }

    private void executeCode() {
        String code = String.join("\n", codeLines);
        System.out.println("Executing code:\n" + code);
        // Здесь будет логика выполнения кода
    }

    public String getCode() {
        return String.join("\n", codeLines);
    }
}