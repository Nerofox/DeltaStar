package fr.deltastar.pigou.model.panel.module.power;

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
public class EpuModule implements ModuleInterface {

    private boolean isConnected;
    private Component ledGreen;
    private Component switchOnOff;

    public EpuModule() {
        this.ledGreen = new Component(ComponentConstants.OUTPUT, "Led green");
        this.switchOnOff = new Component(ComponentConstants.INPUT, "EPU - Switch");
        this.isConnected = false;
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
        //ne peut etre enclenché que si le vaisseau est posé et qu'on est en mode activation
        if (!ServicePigou.getOrbiterService().isLanding() || !DeltaStar.getPowerSystem().getStarterModule().isIsOnline()) {
            ServicePigou.getSoundService().play(SoundConstants.BAD_ACTION);
            return;
        }
        
        if (activate) {
            this.ledGreen.switchOn();
            ServicePigou.getSoundService().play(SoundConstants.EPU_CONNECT);
            this.isConnected = true;
        } else {
            this.isConnected = false;
            this.ledGreen.switchOff();
        }
    }

    @Override
    public String toString() {
        return "EPU";
    }

    public boolean isConnected() {
        return isConnected;
    }
}
