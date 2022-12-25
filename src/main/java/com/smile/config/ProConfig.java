package com.smile.config;

import java.io.IOException;
import java.util.Properties;

public class ProConfig {

    private static final Properties props = new Properties();

    static {
        try {
            props.load(ProConfig.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperties(String name) {
        return props.getProperty(name);
    }

    private ProConfig() {
    }


}
