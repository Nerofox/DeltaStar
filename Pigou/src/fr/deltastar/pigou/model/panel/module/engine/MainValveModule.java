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
public class MainValveModule implements ModuleInterface {

    private Component ledGreen;
    private Component switchOnOff;
    private boolean isSupply;

    public MainValveModule() {
        this.ledGreen = new Component(ComponentConstants.OUTPUT, "Led green");
        this.switchOnOff = new Component(ComponentConstants.INPUT, "Main valve - Switch");
        this.isSupply = false;
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
            if (activate && !this.isSupply) {
                this.ledGreen.switchOn();
                this.isSupply = true;
                ServicePigou.getOrbiterService().sendCmdToOrbiter(CmdOrbiterConstants.MODE_FUELSUPPLY, CmdOrbiterConstants.OPTION_MAIN);
            } else if (this.isSupply) {
                this.ledGreen.switchOff();
                this.isSupply = false;
                ServicePigou.getOrbiterService().sendCmdToOrbiter(CmdOrbiterConstants.MODE_FUELSUPPLY, CmdOrbiterConstants.OPTION_MAIN);
            }
        }
    }

    @Override
    public String toString() {
        return "Main valve";
    }
}
