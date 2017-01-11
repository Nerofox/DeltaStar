package fr.deltastar.pigou.model.panel.module.airlock;

import fr.deltastar.pigou.constant.SoundConstants;
import fr.deltastar.pigou.model.constant.ComponentConstants;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import fr.deltastar.pigou.service.ServicePigou;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Valentin
 */
public class InnerDoorModule implements ModuleInterface {

    private Component ledGreen;
    private Component switchOnOff;
    private boolean isOpen;

    public InnerDoorModule() {
        this.ledGreen = new Component(ComponentConstants.OUTPUT, "Led green");
        this.switchOnOff = new Component(ComponentConstants.INPUT, "Inner door - Switch");
    }

    public boolean isIsOpen() {
        return isOpen;
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
            ServicePigou.getSoundService().play(SoundConstants.AIRLOCK_DOOR_OPENCLOSE);
            this.ledGreen.switchBlink();
            if (activate && !DeltaStar.getAirlockSystem().getEmpAirModule().isDepressurized() && !this.isOpen) {
                ServicePigou.getSoundService().play(SoundConstants.AIRLOCK_DOOR_OPENCLOSE);
                this.ledGreen.switchBlink();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(8000);
                            ledGreen.switchOn();
                            isOpen = true;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(InnerDoorModule.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }).start();
            } else if (!DeltaStar.getAirlockSystem().getEmpAirModule().isDepressurized() && this.isOpen) {
                ServicePigou.getSoundService().play(SoundConstants.AIRLOCK_DOOR_OPENCLOSE);
                this.ledGreen.switchBlink();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(8000);
                            ledGreen.switchOff();
                            isOpen = false;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(InnerDoorModule.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }).start();
            }
        } else {
            ServicePigou.getSoundService().play(SoundConstants.BAD_ACTION);
        }
    }

    @Override
    public String toString() {
        return "Inner door";
    }
}
