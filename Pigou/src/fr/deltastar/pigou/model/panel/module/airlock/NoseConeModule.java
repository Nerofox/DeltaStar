package fr.deltastar.pigou.model.panel.module.airlock;

import fr.deltastar.pigou.constant.CmdOrbiterConstants;
import fr.deltastar.pigou.model.constant.ComponentConstants;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import fr.deltastar.pigou.service.ServicePigou;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class NoseConeModule implements ModuleInterface {

    private Component ledGreen;
    private Component switchOnOff;
    private boolean isOpen;

    public NoseConeModule() {
        this.ledGreen = new Component(ComponentConstants.OUTPUT, "Led green");
        this.switchOnOff = new Component(ComponentConstants.INPUT, "Nose cone - Switch");
    }

    public boolean isIsOpen() {
        return isOpen;
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
        if (DeltaStar.getAirlockSystem().isOnline()) {
            ServicePigou.getOrbiterService().sendCmdToOrbiter(CmdOrbiterConstants.MODE_CMD, CmdOrbiterConstants.OPTION_NOSE);
            this.isOpen = activate;
            if (activate) {
                this.ledGreen.switchTransitionToOn(17000);
            } else {
                this.ledGreen.switchTransitionToOff(17000);
            }
        }
    }

    @Override
    public String toString() {
        return "Nose cone";
    }
}
