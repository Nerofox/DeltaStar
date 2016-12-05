package fr.deltastar.pigou.model.panel.module.lifepack;

import fr.deltastar.pigou.model.constant.ComponentConstants;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class HeaterModule implements ModuleInterface {

    private Component ledGreenCoolingPanel;
    private Component switchOnOff;
    
    private Component ledGreenCoolerGood;
    private Component ledRedCoolerBad;

    public HeaterModule() {
        this.ledGreenCoolingPanel = new Component(ComponentConstants.OUTPUT, "Led green deploy heater");
        this.ledGreenCoolerGood = new Component(ComponentConstants.OUTPUT, "Led green cooler good");
        this.ledRedCoolerBad = new Component(ComponentConstants.OUTPUT, "Led green cooler bad");
        this.switchOnOff = new Component(ComponentConstants.INPUT, "Switch");
    }
    
    @Override
    public List<Component> getListComponents() {
        List<Component> c = new ArrayList<>();
        c.add(this.switchOnOff);
        c.add(this.ledGreenCoolingPanel);
        c.add(this.ledGreenCoolerGood);
        c.add(this.ledRedCoolerBad);
        return c;
    }

    @Override
    public void onAction(boolean activate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Heater";
    }
}
