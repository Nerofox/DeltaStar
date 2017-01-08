package fr.deltastar.pigou.model.panel.module.computer;

import fr.deltastar.pigou.constant.CmdOrbiterConstants;
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
public class AutoPilotProgradeModule implements ModuleInterface {

    private Component ledGreen;
    private Component button;

    public AutoPilotProgradeModule() {
        this.ledGreen = new Component(ComponentConstants.OUTPUT, "Led green");
        this.button = new Component(ComponentConstants.INPUT, "AP prograde - Button");
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
        DeltaStar.getComputerSystem().changeAp(CmdOrbiterConstants.OPTION_APPRO, this.ledGreen);
    }

    @Override
    public String toString() {
        return "Auto pilot prograde";
    }
}
