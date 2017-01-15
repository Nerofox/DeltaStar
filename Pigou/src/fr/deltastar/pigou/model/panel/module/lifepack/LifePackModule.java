package fr.deltastar.pigou.model.panel.module.lifepack;

import fr.deltastar.pigou.model.constant.ComponentConstants;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class LifePackModule implements ModuleInterface {

    private Component ledGreen;
    private Component switchOnOff;
    private boolean isOnline;

    public LifePackModule() {
        this.ledGreen = new Component(ComponentConstants.OUTPUT, "Led green");
        this.switchOnOff = new Component(ComponentConstants.INPUT, "Life-pack - Switch");
    }

    public boolean isOnline() {
        return isOnline;
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
        if (DeltaStar.getLifePackSystem().isOnline()) {
            if (activate && !this.isOnline) {
                this.isOnline = true;
                this.ledGreen.switchOn();
                DeltaStar.getLifePackSystem().launchProcessusO2N2();
            } else if (this.isOnline) {
                DeltaStar.getLifePackSystem().stopProcessusO2N2();
                this.isOnline = false;
                this.ledGreen.switchOff();
            }
        }
    }

    @Override
    public String toString() {
        return "Life-pack";
    }
}
