
package fr.deltastar.pigou.service;

/**
 *
 * @author valentin
 */
public class ServicePigou {
    private static MessageService messageService;
    private static ComArduinoService comArduinoService;
    private static OrbiterService orbiterService;
    private static SoundService soundService;

    public static OrbiterService getOrbiterService() {
        if (orbiterService == null)
            orbiterService = new OrbiterService();
        return orbiterService;
    }
    
    public static SoundService getSoundService() {
        if (soundService == null)
            soundService = new SoundService();
        return soundService;
    }
    
    public static ComArduinoService getComArduinoService() {
        if (comArduinoService == null)
            comArduinoService = new ComArduinoService();
        return comArduinoService;
    }
    
    public static MessageService getMessageService() {
        if (messageService == null)
            messageService = new MessageService();
        return messageService;
    }
}
