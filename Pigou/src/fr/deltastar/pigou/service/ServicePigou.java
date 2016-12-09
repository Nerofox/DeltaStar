
package fr.deltastar.pigou.service;

/**
 *
 * @author valentin
 */
public class ServicePigou {
    private static MessageService messageService;
    private static ComArduinoService comArduinoService;

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
