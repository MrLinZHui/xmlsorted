package com.cargosmart.b2b;


import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args) throws IOException {
        Map<String, List<String>> configMap = ConfigHelper.getInstance().loadConfiguration();
        FileHelper fileHelper = FileHelper.getInstance();
        Map<String, List<File>> inputFiles = fileHelper.getInputFiles();

        for(String TPName : configMap.keySet()) {
            List<File> inputFileList = inputFiles.get(TPName);
            for(File file : inputFileList) {
                String output = file.getPath().replace("InputData", "SortedInputData");

                Document document = SortHandler.sorted(configMap, TPName, file);

                try (FileWriter fileWriter = new FileWriter(output)) {
                    XMLWriter writer = new XMLWriter(fileWriter);
                    writer.write(document);
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }




}
