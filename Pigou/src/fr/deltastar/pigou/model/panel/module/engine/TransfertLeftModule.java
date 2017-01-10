package fr.deltastar.pigou.model.panel.module.engine;

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
public class TransfertLeftModule implements ModuleInterface {

    private Component switchOnOff;

    public TransfertLeftModule() {
        this.switchOnOff = new Component(ComponentConstants.INPUT, "Transfert left - Switch");
    }
    
    @Override
    public List<Component> getListComponents() {
        List<Component> c = new ArrayList<>();
        c.add(this.switchOnOff);
        return c;
    }

    @Override
    public void onAction(boolean activate) {
        if (DeltaStar.getEngineSystem().isOnline()) {
            if (activate) {
                ServicePigou.getOrbiterService().sendCmdToOrbiter(CmdOrbiterConstants.MODE_FUELTRANSFERT, CmdOrbiterConstants.OPTION_RCSTOMAIN);
            } else {
                ServicePigou.getOrbiterService().sendCmdToOrbiter(CmdOrbiterConstants.MODE_FUELTRANSFERT, CmdOrbiterConstants.OPTION_NONE);
            }
        }
    }

    @Override
    public String toString() {
        return "Transfert left";
    }
}
