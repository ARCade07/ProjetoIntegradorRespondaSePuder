package Musica;
//Classes de audio
import javax.sound.sampled.*;
//Permite ler o arquivo de música como fluxo de dados
import java.io.InputStream;

public class Musica {
    //objeto responsável pela reprodução do áudio
    private static Clip clip;
    //variavel que indica se a música está tocando ou não
    private static boolean musicaAtivada = true;

    //método para ativação/desativação da música
    public static void alternarMusica() {
        musicaAtivada = !musicaAtivada;
        if (!musicaAtivada) {
            pararMusica();
        } else {
            tocarMusica();
        }
    }

    public static void tocarMusica() {
        // Se a música está desativada ou já está tocando:
        if (!musicaAtivada || (clip!= null && clip.isRunning())){
            return;
        }
        try {
            // Lê o arquivo .wav da pasta resources
            InputStream is = Musica.class.getResourceAsStream("/songs/musica1.wav");
            if (is == null) {
                System.out.println("Arquivo de música não encontrado");
            }
            // Cria fluxo de áudio à partir da variável is
            AudioInputStream audio = AudioSystem.getAudioInputStream(is);
            // Obtém um Clip de áudio (responsável por tocar e parar a música)
            clip = AudioSystem.getClip();
            //Abre o Clip com o conteúdo do audio para reproduzí-lo
            clip.open(audio);
            //responsável pelo loop da reprodução da música
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        
    }

    public static void pararMusica() {
        //Condicional: não tem um Clip de áudio nem está tocndo
        if (clip != null && clip.isRunning()) {
            //Para a musica
            clip.stop();
            //fecha o recurso
            clip.close();
            // Libera o Clip para ser recriado
            clip = null;
        }
    }
}
