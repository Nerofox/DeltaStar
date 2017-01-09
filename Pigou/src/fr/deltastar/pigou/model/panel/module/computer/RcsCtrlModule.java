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
        if (DeltaStar.getComputerSystem().isOnline()) {
            ServicePigou.getOrbiterService().sendCmdToOrbiter(CmdOrbiterConstants.MODE_CMD, CmdOrbiterConstants.OPTION_RCSMODE);
        }
    }

    @Override
    public String toString() {
        return "RCS control";
    }
}
