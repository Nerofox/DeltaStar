package fr.deltastar.pigou.model.panel.system;

import fr.deltastar.pigou.constant.Constants;
import fr.deltastar.pigou.model.constant.LcdSystemLifePackConstants;
import fr.deltastar.pigou.model.panel.BaseSystem;
import fr.deltastar.pigou.model.panel.DeltaStar;
import fr.deltastar.pigou.model.panel.ModuleInterface;
import fr.deltastar.pigou.model.panel.SystemLcdInterface;
import fr.deltastar.pigou.model.panel.module.lifepack.*;
import fr.deltastar.pigou.service.ServicePigou;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Valentin
 */
public class LifePackSystem extends BaseSystem implements SystemLcdInterface {

    private HeaterModule heaterModule;
    private LifePackModule lifePackModule;
    private SupplyModule supplyModule;
    
    /**
     * Control et regularise le système de refroidissement
     * dés l'activation de l'APU
     */
    private Thread processusCooling;
    /**
     * Control le systeme O2/N2 et de la consommation
     */
    private Thread processusO2N2;
    /**
     * Surveille les conditions de vie du pilote
     */
    private Thread processusLife;
    
    /**
     * Valeur en pourcentage de la quantité restante de O2/N2
     */
    private int valueO2N2;
    /**
     * Valeur sur 3 chiffres max de la température du circuit electronique
     */
    private int temperature;

    public LifePackSystem() {
        this.heaterModule = new HeaterModule();
        this.lifePackModule = new LifePackModule();
        this.supplyModule = new SupplyModule();
        this.arduinoComLcd = ServicePigou.getComArduinoService().getArduinoC();
        this.arduinoComLcd.setSli(this);
        this.valueO2N2 = 100;
        this.temperature = Constants.TEMP_COOLING_MIN;
        if (this.processusLife == null) {
            /**
             * Cet étape surveille dés le lancement de la partie que le pilote peut
             * survivre dans l'enceinte du vaisseau
             */
            this.processusLife = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {                        
                        try {
                            Thread.sleep(Constants.INTERVAL_COOLING_O2N2);
                            //si le vaisseau est pas dépendant de l'extérieur
                            if (!ServicePigou.getOrbiterService().isPossibleToLive()) {
                                //si pas de support de survie ou plus d'oxygen/azote
                                if (valueO2N2 == 0 || !DeltaStar.getLifePackSystem().getLifePackModule().isOnline()) {
                                    DeltaStar.deadGame();
                                }
                            }
                        } catch (InterruptedException ex) {
                            Logger.getLogger(LifePackSystem.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
        }
        this.processusLife.start();
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getValueO2N2() {
        return valueO2N2;
    }

    public void setValueO2N2(int valueO2N2) {
        this.valueO2N2 = valueO2N2;
    }

    public HeaterModule getHeaterModule() {
        return heaterModule;
    }

    public LifePackModule getLifePackModule() {
        return lifePackModule;
    }
    
    @Override
    public int getArgOne() {
        return valueO2N2;
    }

    @Override
    public void setArgOne(int argOne) {
        this.valueO2N2 = argOne;
    }

    @Override
    public int getArgTwo() {
        return this.temperature;
    }

    @Override
    public void setArgTwo(int argTwo) {
        this.temperature = argTwo;
    }
    
    @Override
    public void onActivateSystem() {
        super.setIsOnline(true);
        this.arduinoComLcd.setLcdMod(LcdSystemLifePackConstants.DISPLAY_O2N2_CELSIUS);
        DeltaStar.getPowerSystem().onAuxSystem(true);
        DeltaStar.getPowerSystem().getLifePackPowerModule().getLedGreen().switchOn();
    }

    @Override
    public void onDeactivateSystem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Déclenche les processus de gestion O2/N2
     */
    public void launchProcessusO2N2() {
        if (this.processusO2N2 == null) {
            this.processusO2N2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(Constants.INTERVAL_COOLING_O2N2);
                        valueO2N2 -= Constants.CONSOMMATION_O2N2;
                        if (valueO2N2 < 0)
                            valueO2N2 = 0;
                        //selon O2/N2 actuel on agit
                        //seuil d'alert
                        if (valueO2N2 <= Constants.LIMIT_O2N2_ALERT) {
                            DeltaStar.getLifePackSystem().getArduinoComLcd().setLcdMod(LcdSystemLifePackConstants.O2N2_LOW);
                            Thread.sleep(Constants.INTERVAL_COOLING_O2N2);
                            DeltaStar.getLifePackSystem().getArduinoComLcd().setLcdMod(LcdSystemLifePackConstants.DISPLAY_O2N2_CELSIUS);
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LifePackSystem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
        this.processusO2N2.start();
    }
    
    public void stopProcessusO2N2() {
        this.processusO2N2.stop();
    }
    
    /**
     * Déclenche les processus de gestion du refroidissement du systeme
     * Deviens une procédure une fois l'APU branchée
     */
    public void launchProcessusCooling() {
        if (this.processusCooling == null) {
            this.processusCooling = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {                        
                        try {
                            Thread.sleep(Constants.INTERVAL_COOLING_O2N2);
                            
                            //selon si radiateur déployé
                            if (DeltaStar.getLifePackSystem().getHeaterModule().isDeployed() && temperature >= Constants.TEMP_COOLING_MIN) {
                                temperature -= 1;
                            } else if (!DeltaStar.getLifePackSystem().getHeaterModule().isDeployed()) {
                                temperature += Constants.AUGMENTATION_TEMP_COOLING;
                            }
                            
                            //selon temperature atteinte on agit
                            //seuil d'alert du systeme
                            if (temperature >= Constants.TEMP_COOLING_ALERT_MAX) {
                                DeltaStar.getLifePackSystem().getArduinoComLcd().setLcdMod(LcdSystemLifePackConstants.SYSTEM_OVERHEATED);
                                Thread.sleep(Constants.INTERVAL_COOLING_O2N2);
                                DeltaStar.getLifePackSystem().getArduinoComLcd().setLcdMod(LcdSystemLifePackConstants.DISPLAY_O2N2_CELSIUS);
                            }
                            //seuil final avant surchauffe du système
                            if (temperature >= Constants.TEMP_COOLING_OVERHEATED) {
                                DeltaStar.getPowerSystem().onDeactivateSystem();
                                break;
                            }
                            
                        } catch (InterruptedException ex) {
                            Logger.getLogger(LifePackSystem.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
        }
        this.processusCooling.start();
    }
    
    /**
     * Stop le processus lancé ci dessus
     */
    public void stopProcessusCooling() {
        this.processusCooling.stop();
    }
    
    @Override
    public String toString() {
        return "Life-pack";
    }

    @Override
    public List<ModuleInterface> getListModuleInterface() {
        List<ModuleInterface> mi = new ArrayList<>();
        mi.add(this.heaterModule);
        mi.add(this.lifePackModule);
        mi.add(this.supplyModule);
        return mi;
    }
}
