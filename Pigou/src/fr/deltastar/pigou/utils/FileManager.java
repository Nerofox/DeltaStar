package fr.deltastar.pigou.utils;

import fr.deltastar.pigou.service.ServicePigou;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Valentin
 */
public class FileManager {
    
    public static void save(String pathName, String[] contents) {
        try {
            FileWriter file = new FileWriter(pathName, true);
            BufferedWriter writer = new BufferedWriter(file);
            for (String s : contents)
                writer.write(s + "\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            ServicePigou.getMessageService().displayFatalError("Error write configuration file, check permission or access");
        }
    }
    
    public static String[] open(String pathName) {
        List<String> list = new ArrayList<String>();
        String line = null;
        InputStream file = null;
        InputStreamReader ips = null;
        BufferedReader reader = null;
        try {
            file = new FileInputStream(pathName);
            ips = new InputStreamReader(file);
            reader = new BufferedReader(ips);
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            reader.close();
            ips.close();
            file.close();
        } catch (IOException e) {
            try {
                ServicePigou.getMessageService().displayFatalError("Error load configuration file, check permission or access");
                reader.close();
                ips.close();
                file.close();
            } catch (IOException ex) {
                e.printStackTrace();
            }
        }
        String[] results = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            results[i] = list.get(i);
        }
        return results;
    }
}
