package utils;

import main.Main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Sound {

    Clip clip;
    File soundFile[] = new File[30];
    public Sound() {
        try {
            soundFile[0] = new File("res/Sound/Magicode.wav"); //Звуковой файл
        } catch (Exception e) {
            System.out.println("Sound Constructor Error");
        }
    }

    public void setFile(int i) {

        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile[i]);
            clip = AudioSystem.getClip();

            clip.open(ais);
        }catch (Exception e) {
            System.out.println("Ошибка в Sound");
        }

    }
    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop(); //Останавливаем
        clip.close(); //Закрываем
    }
}
