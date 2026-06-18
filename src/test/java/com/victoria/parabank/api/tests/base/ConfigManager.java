package com.victoria.parabank.api.tests.base;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigManager {
    private static Properties properties;

    static {
        try {

            FileInputStream file = new FileInputStream("src/test/resources/environment.properties");
            properties = new Properties();
            properties.load(file);

        } catch (Exception e) {
            throw new RuntimeException("Could not load environment.properties file!");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}