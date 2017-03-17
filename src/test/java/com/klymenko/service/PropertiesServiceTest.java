package com.klymenko.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;

/**
 * Created by Unicorn on 16.03.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertiesServiceTest {

    @Autowired
    private PropertiesService propertiesService;

    @Test
    public void testUpdateProperties() {
        propertiesService.updateProperties(getPropertiesUpdated());
        Assert.assertEquals(getPropertiesUpdated(), propertiesService.loadProperties());
        propertiesService.updateProperties(getProperties());
        Assert.assertEquals(getProperties(), propertiesService.loadProperties());
    }

    private Properties getProperties() {
        return new Properties()
        {
            {
                put("1","1");
                put("2","1");
                put("5","1");
                put("10","1");
                put("20","1");
                put("50","1");
                put("100","1");
            }
        };
    }
    private Properties getPropertiesUpdated() {
        return new Properties()
        {
            {
                put("1","2");
                put("2","2");
                put("5","2");
                put("10","2");
                put("20","2");
                put("50","2");
                put("100","2");
            }
        };
    }
}
