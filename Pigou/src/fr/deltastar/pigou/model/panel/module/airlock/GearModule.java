package fr.deltastar.pigou.model.panel.module.airlock;

import fr.deltastar.pigou.constant.CmdOrbiterConstants;
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
public class GearModule implements ModuleInterface {

    private Component ledGreen;
    private Component switchOnOff;

    public GearModule() {
        this.ledGreen = new Component(ComponentConstants.OUTPUT, "Led green");
        this.switchOnOff = new Component(ComponentConstants.INPUT, "Gear - Switch");
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
        ServicePigou.getOrbiterService().sendCmdToOrbiter(CmdOrbiterConstants.MODE_CMD, CmdOrbiterConstants.OPTION_GEAR);
        ServicePigou.getSoundService().play(SoundConstants.GEAR_PROGRESS_CLOSEOPEN);
        if (activate) {
            this.ledGreen.switchBlink();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(6000);
                        ledGreen.switchOn();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GearModule.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        } else {
            this.ledGreen.switchBlink();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(6000);
                        ledGreen.switchOff();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GearModule.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        }
    }

    @Override
    public String toString() {
        return "Gear";
    }
}
