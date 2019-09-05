package seleniumutils.methods;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static seleniumutils.methods.GlobalProperties.APPLICATION_DATA_PATH;

public class YAMLReader {
    private Map<String, Object> ymlData;
    private static Map<String, Object> masterDataMap;

    public static Map<String, Object> getMasterDataMap() {
        if(masterDataMap==null)
            readAllYAMLData();
        return masterDataMap;
    }

    public static void setMasterDataMap(Map<String, Object> masterDataMap) {
        YAMLReader.masterDataMap = masterDataMap;
    }



    public YAMLReader(String filename) {
        Yaml yaml = new Yaml();
        try {
            InputStream inputStream = new FileInputStream(filename);
            ymlData = yaml.load(inputStream);
        }catch(Exception e){
            e.printStackTrace();
        }
                //this.getClass().getClassLoader().getResourceAsStream(filename);

    }

    public static Map<String,Object> readAllYAMLData(){
        String[] extensions = new String[]{"yaml"};
        File dir = new File( APPLICATION_DATA_PATH);
        List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
        masterDataMap=new HashMap<>();
        for( File file: files){
            try {
                String filePath = file.getAbsolutePath();
                YAMLReader yml = new YAMLReader(filePath);
                String filename=file.getName();
                masterDataMap.put(FilenameUtils.removeExtension(filename).toUpperCase(),yml.getYmlData());
            }catch(Exception e){e.printStackTrace();}
        }
        return masterDataMap;
    }
    public Map<String, Object> getYmlData() {
        return ymlData;
    }


    public static YAMLRecord recurseYAML(String key, Map<String, Object> map) {

        YAMLRecord yamlRecord=new YAMLRecord();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            //System.out.println("Key is:"+ entry.getKey());


            if (entry.getValue() instanceof Map) {
                //Match found
                if (key.equalsIgnoreCase(entry.getKey())) {
                    yamlRecord.dataMap = (Map<String, Object>) entry.getValue();
                    yamlRecord.dataFound =true;
                    yamlRecord.data=null;
                    break;
                }
                //no match found drill-down
                else {
                    yamlRecord=recurseYAML(key, (Map<String, Object>) entry.getValue());
                    if(yamlRecord.dataFound ==true)
                        break;
                }

            } else {
                if (key.equalsIgnoreCase(entry.getKey())) {
                    yamlRecord.data = entry.getValue().toString();
                    yamlRecord.dataMap=null;
                    yamlRecord.dataFound =true;
                    break;
                }
            }
        }
        if(yamlRecord==null)
            System.out.println("No matching key found!");
    return yamlRecord;
    }

    /*public static void main (String[] args){
        String filepath = "preapp.yaml";
        Map<String, Object> masterDataMap;
        //YAMLReader data = new YAMLReader(filepath);
        masterDataMap=YAMLReader.readAllYAMLData();
        Object x= recurseYAML("input_validation",masterDataMap);
        System.out.println(x);
    }*/
}

