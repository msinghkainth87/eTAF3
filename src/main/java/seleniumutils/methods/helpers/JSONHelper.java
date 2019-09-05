package seleniumutils.methods.helpers;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import seleniumutils.methods.GlobalProperties;

/**
 * @author Gajendra Mahadevan
 */

public class JSONHelper {

    /** Thread-safe write to a json file
     * @param jsonObject : String : The JSON object
     * @param path : String : The path where the file resides
     * @param filename : String : The filename
     */
    @SuppressWarnings("unchecked")

    public static synchronized void JSONWrite(String jsonObject,String path, String filename){

        // try-with-resources statement based on post comment below :)
        File f = new File(path,filename);

        try {
            if(!f.getParentFile().exists())
                f.getParentFile().mkdirs();
            f.createNewFile();
            FileWriter file = new FileWriter(f);
            file.write(jsonObject);
            file.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /** Thread-safe read from file
     * @param path : String : The path where the file resides
     * @param filename : String : The filename
     * @return : A JSONObject read from the file
     */
    public static synchronized JSONObject JSONRead(String path,String filename) {
        // try-with-resources statement based on post comment below :)
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            File file = new File(path, filename);
            //create the file if it does not exist
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
                Object obj = parser.parse(fileReader);
                jsonObject = (JSONObject) obj;
                fileReader.close();
            } else {
                if(!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                file.createNewFile();
                jsonObject = new JSONObject(new HashMap());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public static synchronized void appendToJSON(HashMap<String,Object> hashMap, String path, String filename, TypeReference valueTypeRef, Object toBeAppended) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        DateTimeHelper dateTimeHelper = new DateTimeHelper();
        String timeStamp=dateTimeHelper.getTimeStamp();
        hashMap=objectMapper.readValue(JSONHelper.JSONRead(path,filename).toString(),valueTypeRef);
        HashMap hMap = new HashMap<String,Object>();
        hMap.put("Policy_Number",toBeAppended);
        hashMap.put(timeStamp,hMap);

        String jsonObject = objectMapper.writeValueAsString(hashMap);
        JSONHelper.JSONWrite(jsonObject,path,filename);
    }
}

