package fr.deltastar.pigou.model.panel.system;

import fr.deltastar.pigou.constant.SoundConstants;
import fr.deltastar.pigou.model.panel.BaseSystem;
import fr.deltastar.pigou.model.panel.Component;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import fr.deltastar.pigou.model.panel.module.warning.WarningModule;
import fr.deltastar.pigou.service.ServicePigou;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Valentin
 */
public class WarningSystem extends BaseSystem {

    private WarningModule warningModule;
    
    private int lastErrorCode;
    
    public WarningSystem() {
        this.warningModule = new WarningModule();
        super.isOnline = true; //ce système est toujours disponible
    }

    public WarningModule getWarningModule() {
        return warningModule;
    }
    
    /**
     * Affiche une alerte sur le tableau de bord
     * errorCode doit être une constante de WarningConstants
     * @param errorCode 
     */
    public void displayAlert(int errorCode) {
        this.lastErrorCode = errorCode;
        ServicePigou.getSoundService().play(SoundConstants.WARNING_ALERT);
        this.warningModule.getListComponents().get(errorCode).switchBlink();
    }
    
    public void stopAlert() {
        ServicePigou.getSoundService().stop(SoundConstants.WARNING_ALERT);
        for (Component c :this.warningModule.getListComponents())
            c.switchOff();
    }

    @Override
    public void onActivateSystem() {}

    @Override
    public void onDeactivateSystem() {}
    
    @Override
    public String toString() {
        return "Warning";
    }

    @Override
    public List<ModuleInterface> getListModuleInterface() {
        List<ModuleInterface> mi = new ArrayList<>();
        mi.add(this.warningModule);
        return mi;
    }
}
