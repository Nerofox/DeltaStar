package fr.deltastar.pigou.model.panel.module.airlock;

import fr.deltastar.pigou.constant.CmdOrbiterConstants;
import fr.deltastar.pigou.constant.SoundConstants;
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
public class GearModule implements ModuleInterface {

    private Component ledGreen;
    private Component switchOnOff;

    public GearModule() {
        this.ledGreen = new Component(ComponentConstants.OUTPUT, "Led green");
        this.switchOnOff = new Component(ComponentConstants.INPUT, "Gear - Switch");
    }

    public Component getLedGreen() {
        return ledGreen;
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
            ServicePigou.getOrbiterService().sendCmdToOrbiter(CmdOrbiterConstants.MODE_CMD, CmdOrbiterConstants.OPTION_GEAR);
            ServicePigou.getSoundService().play(SoundConstants.GEAR_PROGRESS_CLOSEOPEN);
            if (activate)
                this.ledGreen.switchTransitionToOn(6000);
            else
                this.ledGreen.switchTransitionToOff(6000);
        }
    }

    @Override
    public String toString() {
        return "Gear";
    }
}
