package fr.deltastar.pigou.model.panel.module.computer;

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
public class AutoPilotHoldAltitudeModule implements ModuleInterface {

    private Component ledGreen;
    private Component button;
    private boolean isActivate;

    public AutoPilotHoldAltitudeModule() {
        this.ledGreen = new Component(ComponentConstants.OUTPUT, "Led green");
        this.button = new Component(ComponentConstants.INPUT, "AP hold altitude - Button");
    }
    
    @Override
    public List<Component> getListComponents() {
        List<Component> c = new ArrayList<>();
        c.add(this.ledGreen);
        c.add(this.button);
        return c;
    }

    /**
     * Le hold altitude fonctionne en parralèle au autre AP
     * @param activate 
     */
    @Override
    public void onAction(boolean activate) {
        if (DeltaStar.getComputerSystem().isOnline()) {
            if (isActivate) {
                this.ledGreen.switchOff();
                this.isActivate = false;
            } else {
                this.ledGreen.switchOn();
                this.isActivate = true;
            }
            ServicePigou.getOrbiterService().sendCmdToOrbiter(CmdOrbiterConstants.MODE_CMD, CmdOrbiterConstants.OPTION_APHOLDALTITUDE);
        }
    }

    @Override
    public String toString() {
        return "Auto pilot hold altitude";
    }
}
