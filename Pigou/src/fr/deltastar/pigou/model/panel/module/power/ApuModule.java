package fr.deltastar.pigou.model.panel.module.power;

import fr.deltastar.pigou.model.constant.ComponentConstants;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class ApuModule implements ModuleInterface {

    private Component ledGreen;
    private Component ledYellow;
    private Component ledRed;
    
    private Component switchOnOff;

    public ApuModule() {
        this.ledGreen = new Component(ComponentConstants.OUTPUT, "Led green");
        this.ledYellow = new Component(ComponentConstants.OUTPUT, "Led yellow");
        this.ledRed = new Component(ComponentConstants.OUTPUT, "Led red");
        this.switchOnOff = new Component(ComponentConstants.INPUT, "APU - Switch");
    }
    
    @Override
    public List<Component> getListComponents() {
        List<Component> c = new ArrayList<>();
        c.add(this.ledGreen);
        c.add(this.ledYellow);
        c.add(this.ledRed);
        c.add(this.switchOnOff);
        return c;
    }

    @Override
    public void onAction(boolean activate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "APU";
    }
}
