package com.cargosmart.b2b;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class ConfigHelperTest {

    @Test
    public void loadConfigurationTest() {
        Map<String,List<String>>lists = ConfigHelper.getInstance().loadConfiguration();
        System.out.println(lists);
        Assert.assertTrue(lists.size() > 0);
    }
}
