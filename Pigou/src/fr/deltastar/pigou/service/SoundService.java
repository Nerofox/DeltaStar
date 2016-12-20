package fr.deltastar.pigou.service;

import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 *
 * @author Valentin
 */
public class SoundService {
    
    private MediaPlayer mp;
    
    public void play(String song) {
        URL urlSong = getClass().getResource(song);
        Media media = new Media(urlSong.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }
}
