package fr.deltastar.pigou.communication;

/**
 * Class de base pour toute les classes souhaitant communiquer
 * avec les class de communication
 * @author Valentin
 */
public interface ListenerCom {
    public void onDataReceved(String data);
}
