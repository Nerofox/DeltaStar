package fr.deltastar.pigou.model.panel.module.power;

import fr.deltastar.pigou.constant.SoundConstants;
import fr.deltastar.pigou.model.constant.ComponentConstants;
import fr.deltastar.pigou.model.constant.LcdSystemPowerConstants;
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
public class ApuModule implements ModuleInterface {
    
    private Component ledGreen;
    private Component ledYellow;
    private Component ledRed;
    
    private Component switchOnOff;

    public ApuModule() {
        this.ledGreen = new Component(ComponentConstants.OUTPUT, "Led green");
        this.ledYellow = new Component(ComponentConstants.OUTPUT, "Led yellow");
        this.ledRed = new Component(ComponentConstants.OUTPUT, "Led red");
        this.switchOnOff = new Component(ComponentConstants.INPUT, "APU - Switch");
    }
    
    @Override
    public List<Component> getListComponents() {
        List<Component> c = new ArrayList<>();
        c.add(this.ledGreen);
        c.add(this.ledYellow);
        c.add(this.ledRed);
        c.add(this.switchOnOff);
        return c;
    }

    public Component getLedGreen() {
        return ledGreen;
    }

    public Component getLedYellow() {
        return ledYellow;
    }

    public Component getLedRed() {
        return ledRed;
    }

    public Component getSwitchOnOff() {
        return switchOnOff;
    }

    @Override
    public void onAction(boolean activate) {
        //si système non activé ou EPU non branché on bloque
        if (DeltaStar.getPowerSystem().getStarterModule().isIsOnline()) {
            if (activate && !DeltaStar.getPowerSystem().isOnline() && DeltaStar.getPowerSystem().getEpuModule().isConnected()) {
                ServicePigou.getSoundService().play(SoundConstants.APU_START);
                DeltaStar.getPowerSystem().getArduinoComLcd().setLcdMod(LcdSystemPowerConstants.APU_IN_PROGRESS);
                this.ledRed.switchOff();
                this.ledYellow.switchBlink();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(12000);
                            ledYellow.switchOff();
                            ledGreen.switchOn();
                            ServicePigou.getSoundService().play(SoundConstants.MAIN_AMBIANCE);
                            DeltaStar.getPowerSystem().onActivateSystem();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ApuModule.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }).start();
            } else if (DeltaStar.getPowerSystem().isOnline()) {
                ServicePigou.getSoundService().stop(SoundConstants.MAIN_AMBIANCE);
                ServicePigou.getSoundService().play(SoundConstants.APU_STOP);
                this.ledGreen.switchOff();
                this.ledRed.switchOn();
                DeltaStar.getPowerSystem().onDeactivateSystem();
            }
        } else {
            ServicePigou.getSoundService().play(SoundConstants.BAD_ACTION);
        }
    }

    @Override
    public String toString() {
        return "APU";
    }
}
