package fr.deltastar.pigou.communication;

/**
 * Class de base pour toute les classes souhaitant recevoir
 * les données entrantes avec l'arduino
 * @author Valentin
 */
public interface ListenerComInterface {
    /**
     * Déclenché dés que des données entrante ont eu lieu
     * @param data 
     */
    public void onDataReceved(String data);
    /**
     * Déclenché dés qu'un arduino s'est connecté avec succès
     * @param arduinoId 
     */
    public void onConnectArduino(String arduinoId);
}
