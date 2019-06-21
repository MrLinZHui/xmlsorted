package com.cargosmart.b2b;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SortHandler {

    private static SortHandler instance = new SortHandler();

    private SortHandler(){

    }

    public static SortHandler getInstance(){
        return instance;
    }

    private static List<Node> sortXml(List<Node> entityNodes, String fieldPath){

        String[] split = fieldPath.split(";");
        String[] keys = split[1].split("\\+");

        List<Node> sortedNodes = entityNodes.stream().sorted((preNode, nextNode) -> {
            int result = 0;
            for(int i=0; i<keys.length; i++) {
                String preFiled = preNode.selectSingleNode("./" + keys[i]).getText();
                String nextFiled = nextNode.selectSingleNode("./" + keys[i]).getText();
                if (preFiled.compareTo(nextFiled) == 0) {
                    continue;
                } else {
                    result = preFiled.compareTo(nextFiled);
                    break;
                }
            }
            return result;
        }).collect(Collectors.toList());

        return sortedNodes;
    }

    public static Document sorted(Map<String, List<String>> configMap, String TPName, File file){

        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        for(String config : configMap.get(TPName)){
            String groupPath = config.substring(0, config.lastIndexOf("/"));
            String entityPath = config.substring(0, config.indexOf(";")).replace(groupPath, ".");

            List<Node> groupNodes = document.selectNodes(groupPath);
            for (Node groupNode : groupNodes) {
                List<Node> entityNodes = groupNode.selectNodes(entityPath);

                if(entityNodes.size()>1) {
                    List<Node> sortedNodes = SortHandler.sortXml(entityNodes, config);
                    Element groupElement = (Element) groupNode;

                    for(Node node : entityNodes) {
                        groupElement.remove(node);
                    }
                    for(Node node : sortedNodes) {
                        groupElement.add(node);
                    }
                }
            }
        }

        return document;
    }
}
