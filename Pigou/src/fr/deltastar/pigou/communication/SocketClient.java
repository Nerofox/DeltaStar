package fr.deltastar.pigou.communication;

import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.controller.StatusComViewController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gestion simple d'un client socket
 * @author Valentin
 */
public class SocketClient implements ComClientInterface {
    private int port;
    private String arduinoId;
    
    private Thread listenerInput;
    private Socket socket;
    private BufferedReader in;
    
    private ListenerComInterface listenerCom;
    private StatusComViewController statusComViewController;

    /**
     * initialise la connection
     * @param port
     * @param listenerCom 
     */
    @Override
    public void connect(String port, ListenerComInterface listenerCom, String arduinoId) {
        this.port = Integer.parseInt(port);
        this.listenerCom = listenerCom;
        this.arduinoId = arduinoId;
        try {
            if (StatusComViewController.getInstance() != null)
                StatusComViewController.getInstance().setStatusInProgress(this.arduinoId);
            this.socket = new Socket(Constants.VIRTUAL_IP, this.port);
            System.out.println("Connection established on " + this.port);
            if (StatusComViewController.getInstance() != null)
                StatusComViewController.getInstance().setStatusOk(this.arduinoId);
            this.listenerCom.onConnectArduino(this.arduinoId);
        } catch (IOException ex) {
            System.out.println("Connection error on " + this.port);
            if (StatusComViewController.getInstance() != null)
                StatusComViewController.getInstance().setStatusKo(this.arduinoId, "Connection error on " + this.port);
        }
    }
    
    /**
     * Lance un thread qui va ecouter les données potentiel entrante
     * retourne les données si existantes dans le ListenerCom
     */
    @Override
    public void listenInput() {
        this.listenerInput = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String msg = "";
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while(true) {
                        msg = in.readLine();
                        if (msg != null && !"".equals(msg.trim())) {
                            listenerCom.onDataReceved(msg);
                            if (StatusComViewController.getInstance() != null)
                                StatusComViewController.getInstance().addDataInput(msg);
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Error listen input on " + port);
                }
            }
        });
        this.listenerInput.start();
    }
    
    /**
     * Envoi des données au serveur socket
     * @param data 
     */
    @Override
    public void sendData(String data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PrintWriter out = null;
                try {
                    out = new PrintWriter(socket.getOutputStream());
                    out.write(data);
                    out.flush();
                } catch (IOException ex) {
                    System.out.println("Error send output on " + port);
                    if (StatusComViewController.getInstance() != null)
                        StatusComViewController.getInstance().setStatusKo(arduinoId, "Send error on " + port);
                } finally {
                    out.close();
                }
            }
        }).start();
    }
    
    /**
     * Ferme la connection
     */
    @Override
    public void closeConnection() {
        try {
            if (this.listenerInput != null)
                this.listenerInput.stop();
            if (this.in != null)
                this.in.close();
            if (this.socket != null)
                this.socket.close();
            System.out.println("Close connection on " + Constants.VIRTUAL_IP + ":" + this.port);
            if (StatusComViewController.getInstance() != null)
                StatusComViewController.getInstance().setStatusKo(this.arduinoId, "Close connection on " + Constants.VIRTUAL_IP + ":" + this.port);
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean isConnect() {
        return this.socket.isConnected();
    }
}
