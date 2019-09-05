package seleniumutils.methods.helpers;

import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import seleniumutils.methods.ApachePOIExcel;
import seleniumutils.methods.ElementObject;
import seleniumutils.methods.GlobalProperties;
import seleniumutils.methods.GetElementUsingBy;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static seleniumutils.methods.GlobalProperties.APPLICATION_PAGE_OBJECTS_PATH;

public class PageObjectGenerator {

    public static Map<String,Map<String,ElementObject>> pageObjects;
    public static HashMap<String,ElementObject> elementObjects;
    static String elementName;
    static String access_name;
    static String type;
    //TODO Add pageobject specific methods here

    /*public static HashMap<String,ElementObject> getPageObjects(String workBookPath){
        XSSFWorkbook workbook = ApachePOIExcel.readXL(workBookPath);
        for(int k=0;k<workbook.getNumberOfSheets();k++) {
            ElementObject ElementObject = new ElementObject(k);
        }
    }*/

    public static Map<String,ElementObject> getPageObject(String sheetName, String excelPath) {
        int sheetIndex = ApachePOIExcel.getSheetIndexFromName(sheetName, excelPath);
        return getPageObject(sheetIndex, excelPath);
    }

    public static Map<String,ElementObject> getPageObject(int sheetIndex, String excelPath) {
        List<ElementObject> elementObjects;
        Map<String,ElementObject> pageObject = new HashMap<>();
        File excelFile = new File(excelPath);
        elementObjects= getElementObjectList(sheetIndex,excelFile);
        elementObjects.parallelStream().forEach(elementObject -> {
            pageObject.put(elementObject.getVariable().toLowerCase(),elementObject);
        });
        return pageObject;
    }

    public static List<ElementObject> getElementObjectList(int sheetIndex, File excelFile) {
        List<ElementObject> elementObjects;
        PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
                .sheetIndex(sheetIndex)
                .preferNullOverDefault(true)
                .build();
        elementObjects = Poiji.fromExcel(excelFile, ElementObject.class, options);
        return elementObjects;
    }

    public static ElementObject getElementObject(String elementName) {
        GetElementUsingBy.setElementName(elementName);
        String regex=".*_(\\d+)$",indexString;

        //Remove index if exists
        if(elementName.matches(regex)) {
            indexString = HelperUtils.stringFetch(elementName, regex,1);
            elementName=elementName.replaceFirst("_(\\d+)$","");
            ElementObject tempObj= new ElementObject(elementObjects.get(elementName));
            String accessName=tempObj.getAccessName();
            accessName=accessName.replaceAll("::index::",indexString);
            tempObj.setAccessName(accessName);
            return tempObj;
        }
        return elementObjects.get(elementName);
    }
                    /*page       elmVar  elemObj*/
    public static Map<String,Map<String,ElementObject>> getPageObjects(String excelPath){
        if(pageObjects!=null && !pageObjects.isEmpty())
            return pageObjects;
        pageObjects= new HashMap<>();
        XSSFWorkbook workbook =ApachePOIExcel.readXL(excelPath);
        int numberOfSheets = workbook.getNumberOfSheets();
        for(int i=0;i<numberOfSheets;i++){
            String sheetName=ApachePOIExcel.getSheetName(i, excelPath);
            Map<String,ElementObject> h= PageObjectGenerator.getPageObject(i,excelPath);
            pageObjects.put(sheetName.toUpperCase(),h);
        }
        return pageObjects;
    }

    public static void main(String[] args){
        long t0 = System.nanoTime();
        Map<String, Object> pageObjects = new HashMap<>();
        XSSFWorkbook workbook =ApachePOIExcel.readXL(APPLICATION_PAGE_OBJECTS_PATH);
        int numberOfSheets = workbook.getNumberOfSheets();
        for(int i=0;i<numberOfSheets;i++){
            Map<String,ElementObject> h= PageObjectGenerator.getPageObject(i,APPLICATION_PAGE_OBJECTS_PATH);
            pageObjects.put(workbook.getSheetName(i).toUpperCase(),h);
        }
        long t1 = System.nanoTime();
        System.out.printf("Poiji time: %.3fs",(t1-t0)* 1e-9);

        long t2 = System.nanoTime();
        File dir = new File( GlobalProperties.TESTRESOURCESPATH+"\\"+GlobalProperties.configProperties.get("application").toLowerCase()+"\\data\\");
        List<File> files = (List<File>) FileUtils.listFiles(dir, new String[]{"xlsx"}, true);
        int index = files.contains("PageObjects.xlsx")?files.indexOf("PageObjects.xlsx"):0;
        PageObjectGenerator.elementObjects = ApachePOIExcel.readXL2FlatHash(files.get(index).getAbsolutePath());
        long t3 = System.nanoTime();
        System.out.printf("\tfor loop time: %.3fs",(t3-t2)* 1e-9);

        long t4 = System.nanoTime();
        Map<String,Map<String,ElementObject>> tmpElementObjects= getPageObjects(APPLICATION_PAGE_OBJECTS_PATH);
        long t5 = System.nanoTime();
        System.out.printf("\tfor loop equivalent time: %.3fs",(t1-t0)* 1e-9);

        int x=10;
        //"C:\\Gaja\\Automation frameworks\\Cucumber_BDD\\src\\test\\resources\\claimcenter\\data\\PageObjects.xlsx"
    }

   /* public static HashMap<String, By> generatePageObjects(){
        for(String k: flatData.keySet()){
            int period = k.lastIndexOf("\\.");
            elementName = k.substring(0,period);
            access_name=flatData.get(k);
            type= k.replaceAll(".*\\.","");
        }
    }
   /* public static HashMap<String, HashMap<String, HashMap<String, String>>> getPageObjects() {
        elementObjects = ApachePOIExcel.getXlData();
        return elementObjects;
    }

    public static String[] getElementObject(String sheetName,String field) {
        elementObjects= ApachePOIExcel.getXlData();
        HashMap<String,String> username = elementObjects.get(sheetName.toLowerCase()).get(field.toLowerCase());
        pageObject[0]=username.keySet().toArray()[0].toString();
        pageObject[1]=username.get(pageObject[0]);
        return pageObject;
    }*/
}
