package fr.deltastar.pigou.model.panel.module.engine;

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
public class SupplyModule implements ModuleInterface {

    private Component ledGreen;
    private Component switchOnOff;
    private boolean isConnected;

    public SupplyModule() {
        this.ledGreen = new Component(ComponentConstants.OUTPUT, "Led green");
        this.switchOnOff = new Component(ComponentConstants.INPUT, "Supply Engine - Switch");
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
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
        if (DeltaStar.getEngineSystem().isOnline() && ServicePigou.getOrbiterService().isLanding()) {
            if (activate) {
                this.ledGreen.switchOn();
                this.isConnected = true;
                ServicePigou.getSoundService().play(SoundConstants.SUPPLY_CONNECT);
            } else if (!DeltaStar.getEngineSystem().getMainValveModule().isSupply() && !DeltaStar.getEngineSystem().getRcsValveModule().isSupply()) {
                this.ledGreen.switchOff();
                this.isConnected = false;
                ServicePigou.getSoundService().play(SoundConstants.SUPPLY_CONNECT);
            }
        }
    }

    @Override
    public String toString() {
        return "Supply";
    }
}
