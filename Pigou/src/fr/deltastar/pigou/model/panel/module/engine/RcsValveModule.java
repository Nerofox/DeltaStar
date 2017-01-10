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
public class RcsValveModule implements ModuleInterface {

    private Component ledGreen;
    private Component switchOnOff;
    private boolean isSupply;

    public RcsValveModule() {
        this.ledGreen = new Component(ComponentConstants.OUTPUT, "Led green");
        this.switchOnOff = new Component(ComponentConstants.INPUT, "RCS valve - Switch");
    }

    public boolean isSupply() {
        return isSupply;
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
        if (DeltaStar.getEngineSystem().getSupplyModule().isConnected()) {
            ServicePigou.getOrbiterService().sendCmdToOrbiter(CmdOrbiterConstants.MODE_FUELSUPPLY, CmdOrbiterConstants.OPTION_RCS);
            if (activate) {
                this.ledGreen.switchOn();
                this.isSupply = true;
            } else {
                this.ledGreen.switchOff();
                this.isSupply = false;
            }
        }
    }

    @Override
    public String toString() {
        return "RCS valve";
    }
}
