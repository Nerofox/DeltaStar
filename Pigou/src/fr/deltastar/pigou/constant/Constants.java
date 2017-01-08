/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.deltastar.pigou.constant;

/**
 *
 * @author Valentin
 */
public class Constants {
    public final static String TITLE_APPLICATION = "PIGOU";
    
    //CONFIG SYSTEMES ELECTRIQUES
    //interval de decompte de l'énergie en millisecondes
    public final static int INTERVAL_POWER = 5000;
    //consommation de base toute les secondes du systèmes electriques
    public final static int NB_CONSOMMATION_BASE = 1;
    //consommation d'un systeme annexe
    public final static int CONSOMMATION_SYSTEM = 3;
    
    //CONFIG POUR LA COMMUNICATION AVEC ORBITER
    public final static String FILENAME_CONFIG_ORBITER = "configOrbiter.pf";
    public final static String PORT_ORBITER_SOCKET = "14000";
    public final static String DELIMITER_CMD_ORBITER = ","; //delimiteur utilisé pour l'envoi des commandes à Orbiter
    public final static int ALTITUDE_MINIMAL_FORLANDING = 300; //altitude en mètre minimum pour le considéré comme atterit
    public final static String TITLE_ORBITER_DIRECTORY_CHOOSER = "Choose Orbiter directory";
    public final static String MSG_CONFIG_ORBITER_SUCCESS = "Configuration done for orbiter";
    public final static String MSG_CONFIG_ORBITER_BAD = "Configuration Orbiter not found";
    public final static String PATHS_ORBITER_SCENARIO_PIGOU = "\\Scenarios\\PIGOU\\";
    public final static String ORBITER_MISSION_ONE = "Mission1.scn";
    
    //CONFIG POUR LE DELTASTAR VIRTUEL
    public final static boolean MODE_VIRTUAL = true;
    public final static String VIRTUAL_IP = "127.0.0.1";
    public final static String VIRTUAL_PORT_A = "10000";
    public final static String VIRTUAL_PORT_B = "11000";
    public final static String VIRTUAL_PORT_C = "12000";
    
    public final static int SIZE_WIDTH_APPLICATION = 800;
    public final static int SIZE_HEIGHT_APPLICATION = 600;
    
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
}
