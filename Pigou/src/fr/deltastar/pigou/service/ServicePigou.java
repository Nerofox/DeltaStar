
package fr.deltastar.pigou.service;

/**
 *
 * @author valentin
 */
public class ServicePigou {
    private static MessageService messageService;

    public static MessageService getMessageService() {
        if (messageService == null)
            messageService = new MessageService();
        return messageService;
    }
}
