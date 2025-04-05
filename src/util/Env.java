package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Env {
    private static Properties props = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream(".env");
            props.load(fis);
            fis.close();
        } catch (IOException e) {
            System.err.println("Could not load .env file: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
