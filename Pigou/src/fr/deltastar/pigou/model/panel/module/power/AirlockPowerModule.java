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
public class AirlockPowerModule implements ModuleInterface {

    private Component ledGreen;
    private Component switchOnOff;
    private boolean isOn;

    public AirlockPowerModule() {
        this.ledGreen = new Component(ComponentConstants.OUTPUT, "Led green");
        this.switchOnOff = new Component(ComponentConstants.INPUT, "Airlock power - Switch");
        this.isOn = false;
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
        if (DeltaStar.getPowerSystem().isOnline()) {
            if (activate && !this.isOn) {
                this.isOn = true;
                DeltaStar.getAirlockSystem().onActivateSystem();
            } else if (this.isOn) {
                this.isOn = false;
                DeltaStar.getAirlockSystem().onDeactivateSystem();
            }
        } else {
            ServicePigou.getSoundService().play(SoundConstants.BAD_ACTION);
        }
    }

    @Override
    public String toString() {
        return "Airlock";
    }
}
