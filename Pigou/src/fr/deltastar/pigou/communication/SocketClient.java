package fr.deltastar.pigou.communication;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Valentin
 */
public class SocketClient {
    private final String SERVER_IP = "127.0.0.1";
    private int port;
    
    private Thread listenerInput;
    private Socket socket;
    private BufferedReader in;
    
    private ListenerCom listenerCom;

    /**
     * Constructeur
     * initialise la connection
     * @param port
     * @param listenerCom 
     */
    public SocketClient(int port, ListenerCom listenerCom) {
        this.port = port;
        this.listenerCom = listenerCom;
        try {
            this.socket = new Socket(SERVER_IP, port);
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Lance un thread qui va ecouter les données potentiel entrante
     * retourne les données si existantes dans le ListenerCom
     */
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
                            System.out.println("Data receved : " + msg);
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.listenerInput.start();
    }
    
    /**
     * Envoi des données au serveur socket
     * @param data 
     */
    public void sendData(String data) {
        try {
            PrintWriter out = new PrintWriter(this.socket.getOutputStream());
            out.write(data);
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Ferme la connection
     */
    public void closeConnection() {
        try {
            this.listenerInput.stop();
            this.in.close();
            this.socket.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
