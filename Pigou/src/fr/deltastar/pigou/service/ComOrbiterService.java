package fr.deltastar.pigou.service;

import fr.deltastar.pigou.communication.ListenerComInterface;
import fr.deltastar.pigou.communication.SocketServer;
import fr.deltastar.pigou.constant.Constants;

/**
 * Service de gestion de la communication avec Orbiter via un script lua
 * @author Valentin
 */
public class ComOrbiterService implements ListenerComInterface {
    
    SocketServer serverSocket;

    public ComOrbiterService() {
        this.serverSocket = new SocketServer(this);
    }
    
    public void launch() {
        this.serverSocket.launch(Integer.parseInt(Constants.PORT_ORBITER_SOCKET));
    }
    
    public void stop() {
        this.serverSocket.close();
    }

    @Override
    public void onDataReceved(String data) {
        
    }

    @Override
    public void onConnect(String arduinoId) {
        this.serverSocket.listen();
    }
}
