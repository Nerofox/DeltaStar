package fr.deltastar.pigou.model.panel.module.lifepack;

import fr.deltastar.pigou.constant.Constants;
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
public class SupplyModule implements ModuleInterface {

    private Component ledGreen;
    private Component switchOnOff;
    private boolean isConnected;
    
    private Thread processusSupply;

    public SupplyModule() {
        this.ledGreen = new Component(ComponentConstants.OUTPUT, "Led green");
        this.switchOnOff = new Component(ComponentConstants.INPUT, "Supply life-pack - Switch");
    }

    public boolean isConnected() {
        return isConnected;
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
        if (DeltaStar.getLifePackSystem().isOnline() && ServicePigou.getOrbiterService().isLanding()) {
            if (activate && this.isConnected == false) {
                this.ledGreen.switchOn();
                this.isConnected = true;
                ServicePigou.getSoundService().play(SoundConstants.SUPPLY_CONNECT);
                this.processusSupply = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true) {
                            try {
                                Thread.sleep(Constants.INTERVAL_O2N2_SUPPLY);
                                if (DeltaStar.getLifePackSystem().getValueO2N2() < 100)
                                    DeltaStar.getLifePackSystem().setValueO2N2(DeltaStar.getLifePackSystem().getValueO2N2() + 1);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(SupplyModule.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
                this.processusSupply.start();
            } else if (this.isConnected == true) {
                this.processusSupply.stop();
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
