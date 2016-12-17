package fr.deltastar.pigou.model.panel.module.computer;

import fr.deltastar.pigou.model.constant.ComponentConstants;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class AutoPilotOrbitMModule implements ModuleInterface {

    private Component ledGreen;
    private Component button;

    public AutoPilotOrbitMModule() {
        this.ledGreen = new Component(ComponentConstants.OUTPUT, "Led green");
        this.button = new Component(ComponentConstants.INPUT, "AP orbit less - Button");
    }
    
    @Override
    public List<Component> getListComponents() {
        List<Component> c = new ArrayList<>();
        c.add(this.ledGreen);
        c.add(this.button);
        return c;
    }

    @Override
    public void onAction(boolean activate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Auto pilot orbit less";
    }
}
