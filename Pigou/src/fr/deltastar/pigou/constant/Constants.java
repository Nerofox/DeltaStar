package fr.deltastar.pigou.constant;

/**
 * Les constantes général ainsi que les config non modifiable en prod sont ici
 * @author Valentin
 */
public class Constants {
    public final static String TITLE_APPLICATION = "PIGOU";
    
    //CONFIG SYSTEMES ELECTRIQUES
    //interval de decompte de l'énergie en millisecondes
    public final static int INTERVAL_POWER = 30000;
    //consommation de base toute les secondes du systèmes electriques
    public final static int NB_CONSOMMATION_BASE = 1;
    //consommation d'un systeme annexe
    public final static int CONSOMMATION_SYSTEM = 3;
    //seuil avant alert du systèmes electriques sur 100
    public final static int LIMIT_POWER_BEFORE_ALERT = 10;
    //temps d'apparition du message d'alerte
    public final static int TIME_DISPLAY_ALERT_POWER = 5000;
    
    //CONFIG SYSTEMES MOTEUR
    //seuil avant alert du systemes des moteurs
    public final static int LIMIT_MAIN_FUEL_ALERT = 1000;
    public final static int LIMIT_RCS_FUEL_ALERT = 100;
    //temps d'apparition du message d'alerte
    public final static int TIME_DISPLAY_ALERT_ENGINE = 5000;
    
    //CONFIG SYSTEMES SUPPORT DE VIE
    //conso par interval de l'oxygen
    public final static int CONSOMMATION_O2N2 = 1;
    //augmentation par interval de la température si radiateur non déployé
    public final static int AUGMENTATION_TEMP_COOLING = 3;
    //interval de rafraichissement
    public final static int INTERVAL_COOLING_O2N2 = 5000;
    //interval rafraichissement avant remplissage
    public final static int INTERVAL_O2N2_SUPPLY = 500;
    
    //seuil avant alert du support de vie
    public final static int LIMIT_O2N2_ALERT = 10;
    //temps d'apparition du message d'alerte
    public final static int TIME_DISPLAY_ALERT_LIFEPACK = 5000;
    //temperature en celsius minimal du systèmes de refroidissement
    public final static int TEMP_COOLING_MIN = 25;
    //temperature en celsius max avant alert du systèmes de refroidissement
    public final static int TEMP_COOLING_ALERT_MAX = 100;
    //temperature en celsius max avant surchauffe
    public final static int TEMP_COOLING_OVERHEATED = 120;
    
    //CONFIG LOGIQUE DU VAISSEAU
    public final static int ALTITUDE_MINIMAL_FORLANDING = 300; //altitude en mètre minimum pour le considéré comme atterit
    public final static int ALTITUDE_MINIMAL_FORLIFE = 20; //altitude en mètre minimum pour considéré l'extérieur comme respirable
    
    //CONFIG POUR LA COMMUNICATION AVEC ORBITER
    public final static String FILENAME_CONFIG_ORBITER = "configOrbiter.pf";
    public final static String PORT_ORBITER_SOCKET = "14000";
    public final static String DELIMITER_CMD_ORBITER = ","; //delimiteur utilisé pour l'envoi des commandes à Orbiter
    public final static String TITLE_ORBITER_DIRECTORY_CHOOSER = "Choose Orbiter directory";
    public final static String MSG_CONFIG_ORBITER_SUCCESS = "Configuration done for orbiter";
    public final static String MSG_CONFIG_ORBITER_BAD = "Configuration Orbiter not found";
    public final static String PATHS_ORBITER_SCENARIO_PIGOU = "\\Scenarios\\PIGOU\\";
    public final static String ORBITER_MISSION_ONE = "Mission1.scn";
    
    //CONFIG POUR LE DELTASTAR VIRTUEL
    //si a true on utilise le deltastar virtuel, sinon le réel
    public final static boolean MODE_VIRTUAL = true;
    public final static String VIRTUAL_IP = "127.0.0.1";
    public final static String VIRTUAL_PORT_A = "10000";
    public final static String VIRTUAL_PORT_B = "11000";
    public final static String VIRTUAL_PORT_C = "12000";
    
    public final static int SIZE_WIDTH_APPLICATION = 800;
    public final static int SIZE_HEIGHT_APPLICATION = 600;
    
    //CONFIG POUR LES COMMUNICATIONS ARDUINO
    public final static int SERIALCOM_DEBIT_COMMUNICATION = 9600;
    public final static String FILENAME_CONFIG_PORT_COM_ARDUINO = "arduinoPort.pf";
    
    //PARTIE AUTO CONFIG SETTING CONTROLLEUR
    public static String FILENAME_CONFIG = "config.pf";
    public static String FILENAME_DELIMITER = ";";
    public static String FILENAME_NOTFOUND_CONFIG_MSG = "WARNING : Config PIGOU is missing for DeltaStar, please config the I/O";
    public static String AUTOCONFIG_VIEW_NB_INPUT = "%d / %d input";
    public static String AUTOCONFIG_VIEW_NB_OUTPUT = "%d / %d output";
    public static String AUTOCONFIG_VIEW_NB_TOTAL = "Total progress %d / %d I/O";
    public static String AUTOCONFIG_MSG_CHOOSEOUTPUT = "Please choose a valid output before next";
    public static String AUTO_CONFIG_MSG_OUTPUTPROGRESS = "Configuration output in progress look at Deltastar and clic on the component on";
    public static String AUTO_CONFIG_MSG_OUTPUTFINISH = "Configuration output is finish, launch configuration input";
    public static String AUTO_CONFIG_MSG_FINISH = "Configuration finished, have fun !";
    
    //PARTIE TESTING DU PANEL (branchement)
    public final static int NB_SWITCH_INPUT = 24;
    
}
