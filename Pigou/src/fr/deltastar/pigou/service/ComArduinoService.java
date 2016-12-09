package fr.deltastar.pigou.service;

import fr.deltastar.pigou.communication.ComArduino;
import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.model.constant.ComponentConstants;

/**
 *
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
    
    public void launch() {
        if (Constants.MODE_VIRTUAL) {
            this.arduinoA.start(Constants.VIRTUAL_PORT_A, ComponentConstants.OUTPUT, 13, 4, 3);
            this.arduinoB.start(Constants.VIRTUAL_PORT_B, ComponentConstants.OUTPUT, 15, 3, 3);
            this.arduinoC.start(Constants.VIRTUAL_PORT_C, ComponentConstants.INPUT, 14, 4, 3);
        } else {
            this.arduinoA.start(this.portComA, ComponentConstants.OUTPUT, 13, 4, 3);
            this.arduinoB.start(this.portComB, ComponentConstants.OUTPUT, 15, 3, 3);
            this.arduinoC.start(this.portComC, ComponentConstants.INPUT, 14, 4, 3);
        }
    }
    
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
