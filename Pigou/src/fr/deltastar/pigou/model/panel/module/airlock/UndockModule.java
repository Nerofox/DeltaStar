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
public class UndockModule implements ModuleInterface {

    private Component ledRed;
    private Component button;

    public UndockModule() {
        this.ledRed = new Component(ComponentConstants.OUTPUT, "Led red");
        this.button = new Component(ComponentConstants.INPUT, "Undock - Big button");
    }
    
    @Override
    public List<Component> getListComponents() {
        List<Component> c = new ArrayList<>();
        c.add(this.ledRed);
        c.add(this.button);
        return c;
    }

    @Override
    public void onAction(boolean activate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Undock";
    }
}
