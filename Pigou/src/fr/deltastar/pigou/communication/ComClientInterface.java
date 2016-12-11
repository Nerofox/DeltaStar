package fr.deltastar.pigou.communication;

import fr.deltastar.pigou.controller.StatusComViewController;

/**
 *
 * @author valentin
 */
public interface ComClientInterface {
    public void connect(String port, ListenerComInputInterface listenerCom, String arduinoId);
    public void listenInput();
    public void sendData(String data);
    public void closeConnection();
}
