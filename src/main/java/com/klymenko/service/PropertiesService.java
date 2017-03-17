package com.klymenko.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Unicorn on 16.03.2017.
 */
@Service
public class PropertiesService {

    private final String propertyFileName = "coin-inventory.properties";

    public void updateProperties(Properties availableCoinsPropertiesCopy) {
        String filePath = this.getClass().getClassLoader().getResource(propertyFileName).getFile();
        try(FileOutputStream fos = new FileOutputStream(new File(filePath))) {
            availableCoinsPropertiesCopy.store(fos, "");
        } catch(IOException e) {
            throw new RuntimeException();
        }
    }

    public Properties loadProperties() {
        try(InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propertyFileName)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
