package seleniumutils.methods.helpers;
import java.util.HashMap;
import java.util.Map;

import seleniumutils.methods.ElementObject;
import seleniumutils.methods.GlobalProperties;
import seleniumutils.methods.YAMLReader;
import seleniumutils.methods.YAMLRecord;

import static seleniumutils.methods.BaseTest.inputObj;

/**
 * The type Test data handler.
 */
public class TestDataHandler {
    private static String dataFormat=GlobalProperties.getConfigProperties().get("input_data_type");
    private static Boolean allowDynamicData=GlobalProperties.getConfigProperties().get("allowDynamicData").equalsIgnoreCase("true");
    private static String inputData;
    private static Boolean parallelDataInjection=GlobalProperties.getConfigProperties().get("parallelDataInjection").equalsIgnoreCase("true");


    /**
     * Instantiates a new Test data handler.
     */
    public TestDataHandler() {
        dataFormat = dataFormat;
    }

    /**
     * Gets expected data.
     *
     * @return the expected data
     */
    public String getExpectedData() {
        return expectedData;
    }

    /**
     * Sets expected data.
     *
     * @param expectedData the expected data
     */
    public void setExpectedData(String expectedData) {
        this.expectedData = expectedData;
    }

    private String expectedData;

    /**
     * Gets input data.
     *
     * @return the input data
     */
    public String getInputData() {
        return inputData;
    }

    /**
     * Sets input data.
     *
     * @param inputData the input data
     */
    public void setInputData(String inputData) {
        TestDataHandler.inputData = inputData;
    }

    /**
     * Get the first occurrence of a data in a master Data Yaml containing all the YAML files.
     *
     * @param key the key
     * @return the string
     */
    public String getData(String key){
        String data="";
        switch(dataFormat){
            case "YAML":
                Map<String,Object> traverseMap= YAMLReader.getMasterDataMap();
                YAMLRecord ymlrec= YAMLReader.recurseYAML(key,traverseMap);
                if(ymlrec.getData()==null)
                    if(ymlrec.getDataMap()==null)
                        System.out.println("Data not found");
                    else
                        System.out.println("Expected: String data\nActual: Data Map found");
                else
                    if(ymlrec.getDataMap()!=null)
                        System.out.println("Recursive parsing logic fault: Inspect");
                    else
                        data=ymlrec.getData();
                break;
            case "EXCEL":
                //TODO use already existing ApachePOI methods to finish this
                break;
            case "JSON":

                break;



        }
        return data;

    }

    /**
     * Traverse to parent map.
     *
     * @param key       the key
     * @param searchMap the search map
     * @return the map
     */
    public static Map<String, Object> traverseToParentMap(String key, Map<String, Object> searchMap){//key=input_data.username.super_user | value = eqe147
        //traverse throught the complex key to the last leaf
        if(key.contains(".")){
            //before period
            String tempKey=key.substring(0,key.indexOf("."));
            //after period
            key=key.substring(key.indexOf(".")+1,key.length());
            if(searchMap.containsKey(tempKey))
                searchMap= traverseToParentMap(key, (Map<String,Object>)searchMap.get(tempKey));
            else
                System.out.println("invalid key for data");//logger method goes here
        }
        //search for the leaf key in the map
        //YAMLRecord ymlrec= (YAMLRecord) YAMLReader.recurseYAML(key,map);

        return searchMap;
    }

    /**
     * Traverse to map.
     *
     * @param key       the key
     * @param searchMap the search map
     * @return the map
     */
    public static Map<String, Object> traverseToMap(String key, Map<String, Object> searchMap){//key=input_data.username.super_user | value = eqe147
        String tempKey=key.substring(key.lastIndexOf(".")+1,key.length());
        searchMap = traverseToParentMap(key,searchMap);
        if(searchMap!=null && searchMap.get(tempKey) instanceof Map)
            return (Map<String, Object>) searchMap.get(tempKey);
        return null;
    }

    /**
     * Inject input data.
     *
     * @param inputMap the input map
     * @param pageName the page where the input elements are present in the object repository
     */
    public static void injectInputData(Map<String,Object> inputMap, String pageName){
        /*Input_data yaml should be of the format
          KEY_LEVEL_1:
            KEY_LEVEL_2:
                .
                    .
                        actual_object_reference: value <Value should be processable>
         */
        /*1. loop through each key
          2. for each key element and value data
            a. Identify the type of the keyelement
            b. Get the accessors from object repository
            b. Call the appropriate element input method and pass the data as input( We can create a common reusable step to do this)
        */
        StringBuilder element;

        MVELTemplateController controller = new MVELTemplateController();
        if(parallelDataInjection) {
            inputMap.entrySet().parallelStream().forEach((Map.Entry<String, Object> entry) -> {
                if (entry.getValue() instanceof Map) {
                    throw new ClassCastException("Nested maps are not expected in injectInputData method. Please inspect");
                }
                String dataValue = (String) entry.getValue();
                //element = new StringBuilder(pageName + "." + entry.getKey());
                //String value =controller.evaluateMethodsFromFiles("C:\\Gaja\\Automation frameworks\\CucumberBDD\\src\\main\\resources\\Helpers.mvel");

                if (allowDynamicData) {
                    //Fake generate regex
                    try {
                        dataValue = DataUtils.generateFakeData(dataValue);
                    }catch(Throwable t){
                        t.printStackTrace();
                    }
                }
                fillElement((pageName + "." + entry.getKey()).toString().toLowerCase(), dataValue);
            });
        }else {
            for (Map.Entry<String, Object> entry : inputMap.entrySet()) {
                if (entry.getValue() instanceof Map) {
                    throw new ClassCastException("Nested maps are not expected in injectInputData method. Please inpsect");
                }
                String dataValue = (String) entry.getValue();
                element = new StringBuilder(pageName + "." + entry.getKey());
                //String value =controller.evaluateMethodsFromFiles("C:\\Gaja\\Automation frameworks\\CucumberBDD\\src\\main\\resources\\Helpers.mvel");

                if (allowDynamicData) {
                    //Fake generate regex
                    try {
                        dataValue = DataUtils.generateFakeData(dataValue);
                    }catch(Throwable t){
                        t.printStackTrace();
                    }
                }
                fillElement(element.toString().toLowerCase(), dataValue);
            }
        }

    }

    /**
     * Traverse and get string.
     *
     * @param key       the key
     * @param searchMap the search map
     * @return the string
     */
    public static String traverseAndGet(String key, Map<String, Object> searchMap){//key=input_data.username.super_user | value = eqe147

        try {
            searchMap = traverseToParentMap(key, searchMap);
        }catch(NullPointerException n){
            n.printStackTrace();
        }
        key=key.substring(key.lastIndexOf(".")+1,key.length());
        return (String)searchMap.get(key);
    }

    //TODO In progress
    /**
     * Generic fill element method to input a value to an element. Determination of element type happens dynamically at run-time
     *
     * @param element the element
     * @param value   the value to be input to the element
     */
    public static void fillElement(String element,String value) {

        ElementObject pObj= PageObjectGenerator.getElementObject(element);
        inputObj.fillElement(pObj.getAccessType(),pObj.getAccessName(),value);
    }

    //TODO In progress
    /**
     * Generic fill element method to input a value to an element. Determination of element type happens dynamically at run-time
     *
     * @param element the element
     * @param value   the value to be input to the element
     */
 /*   public static void validateElement(String element,String value) {

        ElementObject pObj= PageObjectGenerator.elementObjects.get(element);
        assertionObj.validateElement(pObj.getAccessType(),pObj.getAccessName(),value);
    }*/
    /**
     * Main.
     *
     * @param args the args
     */
    public static void main (String[] args){
       // String filepath = "preapp.yaml";
        TestDataHandler test = new TestDataHandler();

        Map<String,Object> tMap=YAMLReader.getMasterDataMap();
        Map<String,Object> newMap= new HashMap<>();
        String x;
        String key="INPUT_DATA.SUPER_USER";
        String tempKey=key.substring(0,key.indexOf("."));
        //newMap=test.traverseToParentMap("INPUT_DATA.USERNAME.SUPER_USER",tMap);
        //x=test.traverseAndGet("INPUT_DATA.SUPER_USER",tMap);
        newMap=TestDataHandler.traverseToMap("LOGIN.SUPER_USER",tMap);
        TestDataHandler.injectInputData(newMap,tempKey);
        //test.getData("input_validation");

        //YAMLReader data = new YAMLReader(filepath);

        //Object x= YAMLReader.recurseYAML("input_validation",masterDataMap);
        System.out.println();
    }

}