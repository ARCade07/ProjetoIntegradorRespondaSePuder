package Musica;

import javax.sound.sampled.*;
import java.io.InputStream;

public class Musica {
    private static Clip clip;
    private static boolean musicaAtivada = false;

    public static void alternarSom() {
        if (musicaAtivada) {
            pararSom();
        } else {
            tocarSom();
        }
    }

    public static void tocarSom() {
        try {
            // Lê o arquivo .wav da pasta resources
            InputStream is = Musica.class.getResourceAsStream("/songs/musica.wav");
            AudioInputStream audio = AudioSystem.getAudioInputStream(is);
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            musicaAtivada = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pararSom() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
        musicaAtivada = false;
    }

    public static boolean isamusicaAtivada() {
        return musicaAtivada;
    }
}
