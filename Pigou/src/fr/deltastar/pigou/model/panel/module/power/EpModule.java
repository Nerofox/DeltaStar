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
public class EpModule implements ModuleInterface {

    private Component ledRed;
    private Component switchOnOff;

    public EpModule() {
        this.ledRed = new Component(ComponentConstants.OUTPUT, "Led red");
        this.switchOnOff = new Component(ComponentConstants.INPUT, "EP - Switch");
    }
    
    @Override
    public List<Component> getListComponents() {
        List<Component> c = new ArrayList<>();
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
        return "EP";
    }
}
