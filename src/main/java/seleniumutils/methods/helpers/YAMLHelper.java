package seleniumutils.methods.helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.yaml.snakeyaml.Yaml;
import seleniumutils.methods.YAMLRecord;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class YAMLHelper {
    public static synchronized void YAMLWrite(Map<String, Object> data,String filename){
        try {
            Yaml yaml = new Yaml();
            FileWriter writer = new FileWriter(filename);
            yaml.dump(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static synchronized Map<String,Object> YAMLRead(String filename) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            Map m = mapper.readValue(new File(filename), Map.class);
            return m;
        } catch (IOException e) {
            return new HashMap<String,Object>();
        }
    }
    public static synchronized void appendToYAML(Map<String,Object> hashMap, String filename, Object toBeAppended ) throws IOException{
        DateTimeHelper dateTimeHelper = new DateTimeHelper();
        String timeStamp=dateTimeHelper.getTimeStamp();
        hashMap=YAMLRead(filename);
        //HashMap hMap = new HashMap<String,Object>();
        //hMap.put("policy_number",toBeAppended);
        hashMap.put("policy_number",toBeAppended);
        YAMLWrite(hashMap,filename);
    }
    public static synchronized void appendToYAML(Map<String,Object> hashMap, String filename, Object toBeAppended, String dataKey) throws IOException{
        DateTimeHelper dateTimeHelper = new DateTimeHelper();
        String timeStamp=dateTimeHelper.getTimeStamp();
        hashMap=YAMLRead(filename);
        HashMap<String,Object> hMap = (HashMap<String,Object>)  TestDataHandler.traverseToMap(dataKey.toUpperCase(),hashMap);
        hMap.put("POLICY_NUMBER",toBeAppended.toString());
        //HashMap hMap = new HashMap<String,Object>();
        //hMap.put("policy_number",toBeAppended);
        hashMap.put(dataKey.toUpperCase(),hMap);
        YAMLWrite(hashMap,filename);
    }
}
