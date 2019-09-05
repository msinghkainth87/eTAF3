package seleniumutils.methods.SelfHealing;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import seleniumutils.methods.GetElementUsingBy;
import seleniumutils.methods.GlobalProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static seleniumutils.methods.GlobalProperties.getApplicationDataPath;

public class HistoricalPropertiesUtil extends GetElementUsingBy {



    public static HashMap<String, Object> elementName = new HashMap<>();

    public static HashMap<String, Integer> getPropertyByColumn() {
        return propertyByColumn;
    }

    public static HashMap<String, Integer> propertyByColumn;

    public static HashMap<String, HashMap<String, String>> getHealedElements() {
        return healedElements;
    }

    public static HashMap<String, HashMap<String, String>> healedElements = new HashMap<>();

    static String HISTORICAL_PROPERTIES_PATH = GlobalProperties.getApplicationDataPath() + "\\HistoricalProperties.xlsx";

    public static String getHistoricalPropertiesPath() {
        return HISTORICAL_PROPERTIES_PATH;
    }


    public static void buildPropertyList() throws IOException {
        //Create the Historical Properties.xlsx if it does not exist
        if(!new File(HISTORICAL_PROPERTIES_PATH).exists());
            generateHistoricalPropertyxlsx();

        FileInputStream fis = new FileInputStream(new File(HISTORICAL_PROPERTIES_PATH));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet SheetName = workbook.getSheetAt(0);
        propertyByColumn = new HashMap();
        int totalNumberColumns = SheetName.getRow(0).getPhysicalNumberOfCells();
        for (int i = 1; i < totalNumberColumns; i++) {
            propertyByColumn.put(SheetName.getRow(0).getCell(i).getStringCellValue(), i);
        }
    }

    public static void flushHistoricalProperties() throws IOException {

        FileInputStream fis = new FileInputStream(new File(HISTORICAL_PROPERTIES_PATH));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet SheetName = workbook.getSheetAt(0);

        int totalNumberRows = SheetName.getLastRowNum();
        int totalNumberColumns = SheetName.getRow(0).getPhysicalNumberOfCells();
        int newRow = totalNumberRows + 1;
        boolean elementIsListed = false;
        HashMap<String, String> elementPropertiesMap;

        for (String name : elementName.keySet()) {
            elementPropertiesMap = (HashMap) elementName.get(name);
            for(int i=1; i <= totalNumberRows; i++) {
                if (SheetName.getRow(i).getCell(0).getCellType() == CellType.STRING) {
                    if (SheetName.getRow(i).getCell(0).getStringCellValue().equalsIgnoreCase(name)) {
                        elementIsListed = true;
                        for (String property : elementPropertiesMap.keySet()) {
                            if(elementPropertiesMap.get(property) != null) {
                                for (int j = 1; j < totalNumberColumns; j++) {
                                    if (SheetName.getRow(0).getCell(j).getStringCellValue().equals(property)) {
                                        SheetName.getRow(i).createCell(j).setCellValue(elementPropertiesMap.get(property));

                                        break;
                                    }
                                }
                            }

                        }
                        break;
                    }
                }

            }
            if(elementIsListed == false){
                SheetName.createRow(newRow);
                for (String property : elementPropertiesMap.keySet()) {
                    SheetName.getRow(newRow).createCell(0).setCellValue(name);
                    for (int j = 1; j < totalNumberColumns; j++) {
                        if (SheetName.getRow(0).getCell(j).getStringCellValue().equals(property)) {
                            try {
                                SheetName.getRow(newRow).createCell(j).setCellValue(elementPropertiesMap.get(property));
                            }
                            catch (NullPointerException e) {
                                System.out.println(name + " " + property + " not available");
                            }
                            break;
                        }
                    }
                }
                newRow++;
            }
            elementIsListed = false;
        }
        try{
            FileOutputStream ExportResults = new FileOutputStream(new File(HISTORICAL_PROPERTIES_PATH));
            workbook.write(ExportResults);
        }
        catch(Exception e){
            System.out.println("********Unable to update HistoricalProperties.xlsx because file was left open********");
        }

    }

    public static void updateObjectRepository() throws IOException {
        FileInputStream fis = new FileInputStream(getApplicationDataPath() + "\\PageObjects.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        for (String healedElement : healedElements.keySet()) {
            HashMap<String, String> elementProperties = healedElements.get(healedElement);
            Map.Entry<String,String> entry = elementProperties.entrySet().iterator().next();
            String key = entry.getKey();
            String value = entry.getValue();
            String[] parts = healedElement.split("\\.");
            XSSFSheet SheetName = workbook.getSheet(parts[0]);
            int totalNumberRows = SheetName.getLastRowNum();
            int totalNumberColumns = SheetName.getRow(0).getPhysicalNumberOfCells();
            outerloop:
            for(int i=1; i <= totalNumberRows; i++) {
                if (SheetName.getRow(i).getCell(1).getStringCellValue().equalsIgnoreCase(parts[1])) {
                    for (int j = 3; j < totalNumberColumns; j++) {
                        if (SheetName.getRow(0).getCell(j).getStringCellValue().equalsIgnoreCase(key)) {
                            for (int z = 3; z < 11; z++) {
                                SheetName.getRow(i).createCell(z);
                            }
                            SheetName.getRow(i).createCell(j).setCellValue(value);
                            break outerloop;
                        }
                    }
                }

            }
        }
        try {
            FileOutputStream ExportResults = new FileOutputStream(getApplicationDataPath() + "\\PageObjects.xlsx");
            workbook.write(ExportResults);
        }
        catch(Exception e){
            System.out.println("********Unable to update PageObjects.xlsx because file was left open********");
        }

    }

    public void updateHistoricalProperties(String element, String accessType, String accessName) {

        WebElement elementFromRepo = new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(getElementByAttributesAfterSelfHeal(accessType, accessName)));
        HashMap<String, String> elementProperties = new HashMap();
        for (String property : propertyByColumn.keySet()) {

            if(property.equals("abs location")){
                if(elementFromRepo.getLocation() == null){
                    elementProperties.put(property, "");
                }
                else{
                    elementProperties.put(property, elementFromRepo.getLocation().toString());
                }
            }

            else if(property.equals("size")){
                if(elementFromRepo.getSize() == null){
                    elementProperties.put(property, "");
                }
                else{
                    elementProperties.put(property, elementFromRepo.getSize().toString());
                }
            }

            else if(property.equals("background-color")){
                if(elementFromRepo.getCssValue(property) == null){
                    elementProperties.put(property, "");
                }
                else{
                    elementProperties.put(property, elementFromRepo.getCssValue(property));
                }
            }

            else{
                if(elementFromRepo.getAttribute(property) == null){
                    elementProperties.put(property, "");
                }
                else{
                    elementProperties.put(property, elementFromRepo.getAttribute(property));
                }
            }

        }

        elementName.put(element, elementProperties);
    }

    public void healedElementRepository(String elementName, String attr, String access_name) {
        HashMap<String, String> elementProperties = new HashMap();
        elementProperties.put(attr, access_name);
        healedElements.put(elementName, elementProperties);
    }

    public static void generateHistoricalPropertyxlsx(){
        String[] columns = {"Variable","tagName","abs location","alt","innerText","outerHTML","color","href","id","size","class","name"};
/*      java.util.List<String> PropertyList = new ArrayList<>();
        PropertyList.add("Variable");
        PropertyList.add("tagName");
        PropertyList.add("abs location");
        PropertyList.add("alt");
        PropertyList.add("innerText");
        PropertyList.add("outerHTML");
        PropertyList.add("color");
        PropertyList.add("href");
        PropertyList.add("id");
        PropertyList.add("size");
        PropertyList.add("class");
        PropertyList.add("name");*/

        // Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Create a blank sheet
        XSSFSheet SheetName = workbook.createSheet("Sheet 1");


        SheetName.createRow(0);
        for(int i = 0; i < columns.length; i++) {
            SheetName.getRow(0).createCell(i).setCellValue(columns[i]);
        }
        try {
            // this Writes the workbook
            FileOutputStream out = new FileOutputStream(new File(HISTORICAL_PROPERTIES_PATH));
            workbook.write(out);
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}


