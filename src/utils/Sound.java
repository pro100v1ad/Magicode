package utils;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class Sound {

    private Clip clip;
    private String[] soundFiles = new String[30];

    public Sound() {
        // Указываем пути к звуковым файлам относительно ресурсов
        soundFiles[0] = "/res/Sound/Magicode.wav";
    }

    public void setFile(int i) {
        try {
            InputStream audioSrc = getClass().getResourceAsStream(soundFiles[i]);
            if (audioSrc == null) {
                System.out.println("Ошибка: звуковой файл не найден! " + soundFiles[i]);
                return;
            }

            // Оберните InputStream в BufferedInputStream
            BufferedInputStream bufferedInputStream = new BufferedInputStream(audioSrc);
            AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedInputStream);
            clip = AudioSystem.getClip();
            clip.open(ais);
            System.out.println("Звуковой файл успешно загружен: " + soundFiles[i]);
        } catch (Exception e) {
            System.out.println("Ошибка в Sound: " + e.getMessage());
        }
    }

    public void play() {
        if (clip == null) {
            System.out.println("Ошибка: звуковой файл не загружен!");
            return;
        }
        clip.setFramePosition(0);
        clip.start();
    }

    public void loop() {
        if (clip == null) {
            System.out.println("Ошибка: звуковой файл не загружен!");
            return;
        }
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if (clip == null) {
            System.out.println("Ошибка: звуковой файл не загружен!");
            return;
        }
        clip.stop();
        clip.close();
    }
}