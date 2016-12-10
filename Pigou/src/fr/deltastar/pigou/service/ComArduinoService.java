package fr.deltastar.pigou.service;

import fr.deltastar.pigou.communication.ComArduino;
import fr.deltastar.pigou.communication.ListenerComInterface;
import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.model.constant.ArduinoPortConstants;
import fr.deltastar.pigou.model.constant.ComponentConstants;

/**
 * Service de gestion de la communication avec arduino
 * @author Valentin
 */
public class ComArduinoService {
    
    private ComArduino arduinoA;
    private ComArduino arduinoB;
    private ComArduino arduinoC;
    
    private String portComA;
    private String portComB;
    private String portComC;

    public ComArduinoService() {
        this.arduinoA = new ComArduino();
        this.arduinoB = new ComArduino();
        this.arduinoC = new ComArduino();
    }
    
    /**
     * Lance les communications avec l'arduino avec le mode
     * d'écoute standard pour l'arduino C
     */
    public void launch() {
        this.launch(this.arduinoC);
    }
    
    /**
     * Lance les communications avec l'arduino avec
     * un écouteur d'entré personnalisé
     * @param lci 
     */
    public void launch(ListenerComInterface lci) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Constants.MODE_VIRTUAL) {
                    arduinoA.start(Constants.VIRTUAL_PORT_A, ComponentConstants.OUTPUT, 13, 4, 3, lci);
                    arduinoB.start(Constants.VIRTUAL_PORT_B, ComponentConstants.OUTPUT, 15, 3, 3, lci);
                    arduinoC.start(Constants.VIRTUAL_PORT_C, ComponentConstants.INPUT, 14, 4, 3, lci);
                } else {
                    arduinoA.start(portComA, ComponentConstants.OUTPUT, 13, 4, 3, lci);
                    arduinoB.start(portComB, ComponentConstants.OUTPUT, 15, 3, 3, lci);
                    arduinoC.start(portComC, ComponentConstants.INPUT, 14, 4, 3, lci);
                }
            }
        }).start();
    }
    
    /**
     * Stop la communication avec les arduinos
     */
    public void stop() {
        this.arduinoA.closeConnection();
        this.arduinoB.closeConnection();
        this.arduinoC.closeConnection();
    }

    public ComArduino getArduinoA() {
        return arduinoA;
    }

    public ComArduino getArduinoB() {
        return arduinoB;
    }

    public ComArduino getArduinoC() {
        return arduinoC;
    }

    public void setPortComA(String portComA) {
        this.portComA = portComA;
    }

    public void setPortComB(String portComB) {
        this.portComB = portComB;
    }

    public void setPortComC(String portComC) {
        this.portComC = portComC;
    }
}
