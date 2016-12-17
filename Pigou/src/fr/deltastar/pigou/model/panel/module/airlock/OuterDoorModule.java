package fr.deltastar.pigou.model.panel.module.airlock;

import fr.deltastar.pigou.model.constant.ComponentConstants;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class OuterDoorModule implements ModuleInterface {

    private Component ledGreen;
    private Component switchOnOff;

    public OuterDoorModule() {
        this.ledGreen = new Component(ComponentConstants.OUTPUT, "Led green");
        this.switchOnOff = new Component(ComponentConstants.INPUT, "Outer door - Switch");
    }
    
    @Override
    public List<Component> getListComponents() {
        List<Component> c = new ArrayList<>();
        c.add(this.ledGreen);
        c.add(this.switchOnOff);
        return c;
    }

    @Override
    public void onAction(boolean activate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Outer door";
    }
}
