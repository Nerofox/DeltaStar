package fr.deltastar.pigou.model.panel.module.power;

import fr.deltastar.pigou.constant.SoundConstants;
import fr.deltastar.pigou.model.constant.ComponentConstants;
import fr.deltastar.pigou.model.constant.LcdSystemPowerConstants;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import fr.deltastar.pigou.model.panel.system.EngineSystem;
import fr.deltastar.pigou.model.panel.system.LifePackSystem;
import fr.deltastar.pigou.model.panel.system.PowerSystem;
import fr.deltastar.pigou.service.ServicePigou;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class StarterModule implements ModuleInterface {

    private boolean isOnline;
    private Component keyOnOff;

    public StarterModule() {
        this.keyOnOff = new Component(ComponentConstants.INPUT, "Starter - Key");
    }
    
    @Override
    public List<Component> getListComponents() {
        List<Component> c = new ArrayList<>();
        c.add(this.keyOnOff);
        return c;
    }

    @Override
    public void onAction(boolean activate) {
        PowerSystem ps = DeltaStar.getPowerSystem();
        EngineSystem es = DeltaStar.getEngineSystem();
        LifePackSystem lps = DeltaStar.getLifePackSystem();
        if (activate) {
            ServicePigou.getSoundService().play(SoundConstants.WELCOME);
            this.isOnline = true;
            //changement texte des LCD
            ps.getArduinoComLcd().setLcdMod(LcdSystemPowerConstants.NO_CONNECTION_APU);
            es.getArduinoComLcd().setLcdMod(LcdSystemPowerConstants.NO_CONNECTION_APU);
            lps.getArduinoComLcd().setLcdMod(LcdSystemPowerConstants.NO_CONNECTION_APU);
            //allumage led rouge apu
            ps.getApuModule().getLedRed().switchOn();
        } else {
            //coupure de l'apu et epu si connecté
            DeltaStar.getPowerSystem().getEpuModule().onAction(false);
            DeltaStar.getPowerSystem().getApuModule().onAction(false);
            //changement texte des LCD
            ps.getArduinoComLcd().setLcdMod(LcdSystemPowerConstants.NO_DISPLAY);
            es.getArduinoComLcd().setLcdMod(LcdSystemPowerConstants.NO_DISPLAY);
            lps.getArduinoComLcd().setLcdMod(LcdSystemPowerConstants.NO_DISPLAY);
            this.isOnline = false;
            DeltaStar.getPowerSystem().getApuModule().getLedRed().switchOff();
        }
    }

    public boolean isIsOnline() {
        return isOnline;
    }

    @Override
    public String toString() {
        return "Starter";
    }
}
