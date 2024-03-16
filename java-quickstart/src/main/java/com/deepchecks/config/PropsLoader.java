package com.deepchecks.config;

import java.io.IOException;
import java.util.Properties;

public class PropsLoader {

    public static final String PROPS_FILE_NAME = "config.properties";
    Properties prop = new Properties();

    public PropsLoader() {
        init();
    }

    void init() {
        try {
            prop.load(PropsLoader.class.getClassLoader().getResourceAsStream(PROPS_FILE_NAME));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String read(String propName) {
        return prop.getProperty(propName);
    }

    public Boolean readBoolean(String propName) {
        return Boolean.valueOf(prop.getProperty(propName));
    }
}
