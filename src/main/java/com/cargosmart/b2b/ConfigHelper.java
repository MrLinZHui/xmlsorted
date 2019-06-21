package com.cargosmart.b2b;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;

public class ConfigHelper {
    private static ConfigHelper ourInstance = new ConfigHelper();

    public static ConfigHelper getInstance() {
        return ourInstance;
    }

    private ConfigHelper() {
    }

    public Map<String,List<String>> loadConfiguration(){
      // Map<String,List<List<String>>> map = new HashMap<>();
            Map<String,List<String>>  map = new HashMap<>();
        try {
            File config = new File(this.getClass().getClassLoader().getResource("config.txt").getFile());
            String TP = "";
            //line = br.readLine();
            List<String> configList = Files.readAllLines(config.toPath()).stream().filter(line -> !line.startsWith("#")).collect(Collectors.toList());
            List<String> listPath = new ArrayList<>();
            for(String configstr:configList){
                    if(!TP.equals(configstr.split("=")[0])&&!TP.equals("")){
                        map.put(TP,listPath);
                        listPath = new ArrayList<>();
                        listPath.add(configstr.split("=")[1]);
                        TP = configstr.split("=")[0];
                    }else{
                        if(TP.equals("")){
                            TP = configstr.split("=")[0];
                        }
                        listPath.add(configstr.split("=")[1]);
                    }
            }
           // listPath.add(endstr);
            map.put(TP,listPath);
//            if(line.contains("=")){
//                TP = line.split("=")[0];
//            }
//            List<String> list = new ArrayList<>();
//            String key = "";
//            List<String> list1 = new ArrayList<>();
//            while(line!=null){
//                if(!line.startsWith("#")) {
//                    TP = line.split("=")[0];
//                    list1.add(line);
//                    String[] lines;
//                    if (line.contains("=")) {
//                        lines = line.split("=");
//                        key = lines[0];
//                        if (key.equals(TP)) {
//                            list.add(line.substring(line.indexOf("=") + 1));
//                        } else {
//                            map.put(TP, list);
//                            list = new ArrayList<>();
//                            list.add(line.substring(line.indexOf("=") + 1));
//                            TP = key;
//                        }
//                    }
//                }
//                line = br.readLine();
//            }
//            //list.clear();
//          // list.add(list1.get(list1.size()-1));
//            map.put(key,list);
//            line = br.readLine();
//            if(line.contains("-")){
//                TP = line.split("-")[0];
//            }
//            String startkey="";
//            while (line != null) {
//                List<List<String>> listList = new ArrayList<>();
//                List<String>row = new ArrayList<>();
//                String[] lines;
//                if(line.contains("-")) {
//                    lines = line.split("-");
//                    String key1 = lines[0];
//                    if(lines[2].contains("+")){
//                        String[]keys = lines[2].split("\\+");
//                        for(String key:keys){
//                           // row.add(lines[0]+"-"+lines[1]+"/"+key);
//                            row.add(line.substring(0,line.lastIndexOf("-"))+"/"+key);
//                        }
//
//                    }else{
//                        row.add(line.substring(0,line.lastIndexOf("-"))+"/"+lines[1]);
//                    }
//                    if(TP.equals(lines[0])){
//                        System.out.println("row:"+row);
//                        System.out.println("TP:"+TP+",lines[0]:"+lines[0]);
//                        listList.add(row);
//                    }else{
//                        System.out.println("TP:"+TP+",lines[0]:"+lines[0]);
//                        map.put(TP,listList);
//                        listList.clear();
//                        TP = lines[0];
//                    }
//
//                }
//                line = br.readLine();
//            }
            boolean exists = config.exists();
        }catch ( Exception e){
            e.printStackTrace();
        }
        return map;
    }
}
