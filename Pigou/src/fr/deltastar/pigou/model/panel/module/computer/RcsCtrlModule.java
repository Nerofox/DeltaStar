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
public class RcsCtrlModule implements ModuleInterface {

    private Component button;

    public RcsCtrlModule() {
        this.button = new Component(ComponentConstants.INPUT, "RCS control - Button");
    }
    
    @Override
    public List<Component> getListComponents() {
        List<Component> c = new ArrayList<>();
        c.add(this.button);
        return c;
    }

    @Override
    public void onAction(boolean activate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "RCS control";
    }
}
