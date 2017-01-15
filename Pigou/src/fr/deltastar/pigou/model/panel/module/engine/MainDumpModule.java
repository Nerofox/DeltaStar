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
public class MainDumpModule implements ModuleInterface {

    private Component ledGreen;
    private Component switchOnOff;
    private boolean isDump;

    public MainDumpModule() {
        this.ledGreen = new Component(ComponentConstants.OUTPUT, "Led green");
        this.switchOnOff = new Component(ComponentConstants.INPUT, "Main dump - Switch");
        this.isDump = false;
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
        if (DeltaStar.getEngineSystem().isOnline()) {
            if (activate && !this.isDump) {
                this.isDump = true;
                this.ledGreen.switchBlink();
                ServicePigou.getOrbiterService().sendCmdToOrbiter(CmdOrbiterConstants.MODE_FUELDUMP, CmdOrbiterConstants.OPTION_MAIN);
            } else if (this.isDump) {
                this.isDump = false;
                this.ledGreen.switchOff();
                ServicePigou.getOrbiterService().sendCmdToOrbiter(CmdOrbiterConstants.MODE_FUELDUMP, CmdOrbiterConstants.OPTION_MAIN);
            }
        }
    }

    @Override
    public String toString() {
        return "Main dump";
    }
}
