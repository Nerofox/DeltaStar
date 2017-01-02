package fr.deltastar.pigou.communication;

import fr.deltastar.pigou.service.ServicePigou;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Valentin
 */
public class SocketServer {
    
    private ListenerComInterface lci;
    private Thread tConnect;
    private Thread tListen;
    
    private ServerSocket serverSocket;
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;

    public SocketServer(ListenerComInterface lci) {
        this.lci = lci;
    }
    
    public void launch(int port) {
        this.tConnect = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Wait a connection from Orbiter...");
                    serverSocket = new ServerSocket(port);
                    //ligne bloquante tant que non connection
                    client = serverSocket.accept();
                    //une fois connection établi
                    out = new PrintWriter(client.getOutputStream());
                    in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    System.out.println("Connection established to Orbiter");
                } catch (IOException ex) {
                    ServicePigou.getMessageService().displayFatalError("Error connection Orbiter");
                }
                //on indique au listener que la connection est établie avec le client
                lci.onConnect("lua");
            }
        });
        this.tConnect.start();
    }
    
    public void send(String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    out.write(msg);
                    System.out.println("Data send on Orbiter : " + msg);
                    out.flush();
                } catch (Exception e) {
                    System.out.println("Error send output on Orbiter");
                }
            }
        }).start();
    }
    
    public void listen() {
        this.tListen = new Thread(new Runnable() {
            @Override
            public void run() {
                String msg = null;
                while (true) {                    
                    try {
                        msg = in.readLine();
                        if (msg != null && !"".equals(msg.trim())) {
                            System.out.println("Data in on Orbiter : " + msg);
                            lci.onDataReceved(msg);
                        }
                    } catch (IOException ex) {
                        System.out.println("Error listen on Orbiter");
                    }
                }
            }
        });
        this.tListen.start();
    }
    
    public void close() {
        System.out.println("Stoping connection on Orbiter");
        try {
            if (this.tConnect != null && this.tConnect.isAlive())
                this.tConnect.stop();
            if (this.tListen != null && this.tListen.isAlive())
                this.tListen.stop();
            if (this.in != null)
                this.in.close();
            if (this.out != null)
                this.out.close();
            if (this.client != null && this.client.isConnected())
                this.client.close();
            if (this.serverSocket != null)
                this.serverSocket.close();
        } catch (IOException ex) {}
    }
}
