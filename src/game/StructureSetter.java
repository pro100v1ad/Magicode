package game;

import display.GamePanel;
import structure.Bridge;
import structure.Structure;

import java.awt.*;
import java.io.*;

public class StructureSetter {

    private Structure[] structure;
    private final int N = 100;

    GamePanel gp;

    public StructureSetter(GamePanel gp) {
        this.gp = gp;
        structure = new Structure[N]; // Инициализация массива
        String path = "/maps/NewWorld/Structure/structure.txt";
//        create(path);
        load(path);
    }

    // TEMP
//    public void create(String path) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
//            String line;
//            for (int i = 0; i < N; i++) {
//                line = "";
//                if (i == 0) line = "bridge_197_120_f_right_8";
//                if (i == 1) line = "bridge_150_120_f_right_8";
//                writer.write(line);
//                writer.newLine();
//            }
//        } catch (IOException e) {
//            System.out.println("Ошибка при записи в файл: " + e.getMessage());
//        }
//    }

    public void load(String path) {
        try (InputStream is = getClass().getResourceAsStream(path);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            if (is == null) {
                System.out.println("Ошибка: файл не найден! " + path);
                return;
            }

            for (int i = 0; i < N; i++) {
                String line = br.readLine();
                if (line == null) {
                    break; // Если файл закончился раньше, чем ожидалось
                }
                String[] parts = line.split("_");
                if (parts[0].equals("bridge")) {
                    String name = parts[0]; // "bridge"
                    int x = Integer.parseInt(parts[1]) * GamePanel.tileSize; // 100
                    int y = Integer.parseInt(parts[2]) * GamePanel.tileSize; // 150
                    boolean flag = parts[3].equals("t"); // true, если "t", иначе false
                    String direction = parts[4]; // "up"
                    int len = Integer.parseInt(parts[5]); // 5

                    // Создаем объект Bridge и добавляем его в массив
                    structure[i] = new Bridge(gp, name, len, direction, flag, x, y);
                }
                // Добавьте обработку других типов структур, если они есть
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        gp.collision.loadStructure(structure);
    }

    public Structure[] getStructure() {
        return structure;
    }

    public void draw(Graphics2D g) {
        for (Structure s : structure) {
            if (s != null) {
                s.draw(g); // Вызов метода draw для каждого объекта в массиве
            }
        }
    }
}