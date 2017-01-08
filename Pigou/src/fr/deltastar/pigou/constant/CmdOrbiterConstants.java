package fr.deltastar.pigou.constant;

/**
 * Met a disposition les commandes qu'il est possible
 * d'envoyer a Orbiter, une commandes est toujours accompagnée
 * de deux valeur un MODE et une OPTION
 * @author Valentin
 */
public class CmdOrbiterConstants {
    
    //ENVOI MESSAGE
    public final static String MODE_MSG = "MSG";
    
    //MODE CMD
    public final static String MODE_CMD = "CMD";
    public final static String OPTION_HUDMODE = "HUDMODE";
    public final static String OPTION_RCSMODE = "RCSMODE";
    public final static String OPTION_DEDOCK = "DEDOCK";
    public final static String OPTION_APLH = "APLH";
    public final static String OPTION_APPRO = "APPRO";
    public final static String OPTION_APRETRO = "APRETRO";
    public final static String OPTION_APORBITMORE = "APORBITMORE";
    public final static String OPTION_APORBITLESS = "APORBITLESS";
    public final static String OPTION_APKILLROT = "APKILLROT";
    public final static String OPTION_APHOLDALTITUDE = "APHOLDALTITUDE";
    public final static String OPTION_NOSE = "NOSE";
    public final static String OPTION_GEAR = "GEAR";
    
    //MODE FUELSUPPLY OR FUELDUMP
    public final static String MODE_FUELLOCK = "FUELLOCK";
    public final static String MODE_FUELSUPPLY = "FUELSUPPLY";
    public final static String MODE_FUELDUMP = "FUELDUMP";
    //option pour les trois modes ci dessus
    public final static String OPTION_MAIN = "MAIN";
    public final static String OPTION_RCS = "RCS";
    
    //MODE TRANSFERT
    public final static String MODE_FUELTRANSFERT = "FUELTRANSFERT";
    public final static String OPTION_MAINTORCS = "MAINTORCS";
    public final static String OPTION_RCSTOMAIN = "RCSTOMAIN";
    public final static String OPTION_NONE = "NONE";
}
