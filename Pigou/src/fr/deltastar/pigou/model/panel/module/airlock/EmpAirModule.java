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
public class EmpAirModule implements ModuleInterface {

    private Component ledGreen;
    private Component switchOnOff;
    private boolean isDepressurized;

    public EmpAirModule() {
        this.ledGreen = new Component(ComponentConstants.OUTPUT, "Led green");
        this.switchOnOff = new Component(ComponentConstants.INPUT, "Emp air - Switch");
    }

    public boolean isIsDepressurized() {
        return isDepressurized;
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
        if (DeltaStar.getAirlockSystem().isOnline() && 
                !DeltaStar.getAirlockSystem().getInnerDoorModule().isIsOpen() &&
                !DeltaStar.getAirlockSystem().getOuterDoorModule().isIsOpen()) {
            this.ledGreen.switchBlink();
            if (activate) {
                ServicePigou.getSoundService().play(SoundConstants.EMP_AIR_LOW);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(12000);
                            ledGreen.switchOn();
                            isDepressurized = true;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(EmpAirModule.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }).start();
            } else {
                ServicePigou.getSoundService().play(SoundConstants.EMP_AIR_UP);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(12000);
                            ledGreen.switchOff();
                            isDepressurized = false;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(EmpAirModule.class.getName()).log(Level.SEVERE, null, ex);
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
        return "Emp air";
    }
}
