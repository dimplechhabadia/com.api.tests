package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = new Properties();

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/env.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load env.properties file: " + e.getMessage());
        }
    }

    // Fetch property by key
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Fetch Base URL dynamically based on environment
    public static String getBaseUrl() {
        String env = properties.getProperty("env").toUpperCase();
        return properties.getProperty("PROD_BASE_URL");  // Fetch corresponding Base URL
    }

    public static void printAllProperties() {
        System.out.println("🔍 Loaded Properties:");
        properties.forEach((key, value) -> System.out.println(key + " = " + value));
    }
}
