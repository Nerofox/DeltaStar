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
public class AutoPilotKillRotModule implements ModuleInterface {

    private Component button;

    public AutoPilotKillRotModule() {
        this.button = new Component(ComponentConstants.INPUT, "AP kill rotation - Button");
    }
    
    @Override
    public List<Component> getListComponents() {
        List<Component> c = new ArrayList<>();
        c.add(this.button);
        return c;
    }

    @Override
    public void onAction(boolean activate) {
        DeltaStar.getComputerSystem().changeAp(CmdOrbiterConstants.OPTION_APKILLROT, null);
    }

    @Override
    public String toString() {
        return "Auto pilot kill rotation";
    }
}
