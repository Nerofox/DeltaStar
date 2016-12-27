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
    
    //CONFIG POUR LA COMMUNICATION AVEC ORBITER
    public final static String IP_ORBITER_SOCKET = "127.0.0.1";
    public final static String PORT_ORBITER_SOCKET = "14000";
    
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
