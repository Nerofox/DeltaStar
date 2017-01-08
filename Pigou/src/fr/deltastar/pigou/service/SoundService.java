package fr.deltastar.pigou.service;

import java.net.URL;
import java.util.HashMap;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Valentin
 */
public class SoundService {
    
    private HashMap<String, MediaPlayer> mediaList;

    public SoundService() {
        this.mediaList = new HashMap<>();
    }
    
    /**
     * Lance la lecture d'un son
     * @param song 
     */
    public void play(String song) {
        URL urlSong = getClass().getResource(song);
        Media media = new Media(urlSong.toString());
        
        MediaPlayer mp = this.mediaList.get((String)song);
        //si le son n'est pas trouvé dans la pile on l'ajoute
        if (mp == null) {
            mp = new MediaPlayer(media);
            this.mediaList.put(song, mp);
        } else {
            this.mediaList.remove(mp);
            mp = new MediaPlayer(media);
            this.mediaList.put(song, mp);
        }
        //dans tout les cas on la joue
        mp.play();
    }
    
    /**
     * Stop inopinément une musique en cours de lecture si celle ci existe
     * @param song
     * @return boolean
     */
    public boolean stop(String song) {
        MediaPlayer mp = this.mediaList.get((String)song);
        if (mp != null)
            mp.stop();
        return (mp != null);
    }
}
