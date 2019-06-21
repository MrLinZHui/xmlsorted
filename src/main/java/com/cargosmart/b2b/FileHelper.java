package com.cargosmart.b2b;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class FileHelper {
    private static FileHelper ourInstance = new FileHelper();
    private static String inputRoot = "D:\\1_B2BEDI_Revamp";

    private Map<String, List<File>> inputMap;
    private ConfigHelper configHelper;

    public static FileHelper getInstance() {
        return ourInstance;
    }

    private FileHelper() {
        configHelper = ConfigHelper.getInstance();
        inputMap = new HashMap<>();
        for(String TPName : configHelper.loadConfiguration().keySet()) {
            inputMap.put(TPName, new ArrayList<>());
        }
    }

    public Map<String, List<File>> getInputFiles() {
        Map<String, List<String>> configMap = configHelper.loadConfiguration();

        try {
            Set<String> TPSet = configMap.keySet();
                Files.walkFileTree(Paths.get(inputRoot), new SimpleFileVisitor<Path>(){
                    @Override
                    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                        for(String TPName : TPSet) {
                            String filePath = path.toFile().getPath();
                            if(filePath.contains(TPName) && filePath.contains("\\InputData\\")) {
                                inputMap.get(TPName).add(path.toFile());
                            }
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        for (String TPName : TPSet) {
                            if(dir.toString().contains(TPName)) {
                                Path sortedPath = Paths.get(dir.toString(), "SortedInputData");
                                if (dir.toString().endsWith(TPName) && !Files.exists(sortedPath)) {
                                    Files.createDirectory(sortedPath);
                                }
                                return FileVisitResult.CONTINUE;
                            } else {
                                return FileVisitResult.SKIP_SUBTREE;
                            }
                        }
                        return super.postVisitDirectory(dir, exc);
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("can't find TP's input file");
        }

        return inputMap;
    }
}
