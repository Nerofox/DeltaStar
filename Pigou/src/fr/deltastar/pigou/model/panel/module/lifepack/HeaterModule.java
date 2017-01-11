package fr.deltastar.pigou.model.panel.module.lifepack;

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
public class HeaterModule implements ModuleInterface {

    private Component ledGreenCoolingPanel;
    private Component switchOnOff;
    
    private Component ledGreenCoolerGood;
    private Component ledRedCoolerBad;
    
    private boolean isDeployed;

    public HeaterModule() {
        this.ledGreenCoolingPanel = new Component(ComponentConstants.OUTPUT, "Led green deploy heater");
        this.ledGreenCoolerGood = new Component(ComponentConstants.OUTPUT, "Led green cooler good");
        this.ledRedCoolerBad = new Component(ComponentConstants.OUTPUT, "Led green cooler bad");
        this.switchOnOff = new Component(ComponentConstants.INPUT, "Heater - Switch");
        this.isDeployed = false;
    }

    public boolean isDeployed() {
        return isDeployed;
    }

    public Component getLedGreenCoolerGood() {
        return ledGreenCoolerGood;
    }

    public Component getLedRedCoolerBad() {
        return ledRedCoolerBad;
    }
    
    @Override
    public List<Component> getListComponents() {
        List<Component> c = new ArrayList<>();
        c.add(this.switchOnOff);
        c.add(this.ledGreenCoolingPanel);
        c.add(this.ledGreenCoolerGood);
        c.add(this.ledRedCoolerBad);
        return c;
    }

    @Override
    public void onAction(boolean activate) {
        if (DeltaStar.getLifePackSystem().isOnline()) {
            if (activate) {
                this.ledGreenCoolingPanel.switchBlink();
                ServicePigou.getSoundService().play(SoundConstants.RADIATOR_CLOSEOPEN);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(8000);
                            ledGreenCoolingPanel.switchOn();
                            isDeployed = true;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(HeaterModule.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }).start();
            } else {
                this.ledGreenCoolingPanel.switchBlink();
                ServicePigou.getSoundService().play(SoundConstants.RADIATOR_CLOSEOPEN);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(8000);
                            ledGreenCoolingPanel.switchOff();
                            isDeployed = false;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(HeaterModule.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }).start();
            }
        }
    }

    @Override
    public String toString() {
        return "Heater";
    }
}
