package com.cargosmart.b2b;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

public class FileHelperTest {

    @Test
    public void runTest() {
        FileHelper fileHelper = FileHelper.getInstance();
        Map<String, List<File>> inputFiles = fileHelper.getInputFiles();
        System.out.println(inputFiles);
        Assert.assertTrue(inputFiles.size() > 0);
    }
}
