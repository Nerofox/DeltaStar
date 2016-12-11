package fr.deltastar.pigou.communication;

/**
 * Class de base pour toute les classes souhaitant recevoir
 * les données entrantes avec l'arduino
 * @author Valentin
 */
public interface ListenerComInputInterface {
    public void onDataReceved(String data);
}
