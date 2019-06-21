package com.cargosmart.b2b;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import javax.swing.text.Document;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class SortHandlerTest {

    @Test
    public void sorted() throws DocumentException {

        Map<String, List<String>> configMap = ConfigHelper.getInstance().loadConfiguration();
        String TPName = "HEINZ";
        String dir = System.getProperty("user.dir");
        Path path = Paths.get(dir, "00000001_20190325_I_25F06E01256744A5B30C64E30C42A715.xml");

        SortHandler instance = SortHandler.getInstance();
        org.dom4j.Document document = instance.sorted(configMap, TPName, path.toFile());

        String text = document.asXML();
        System.out.println(text);
    }
}
