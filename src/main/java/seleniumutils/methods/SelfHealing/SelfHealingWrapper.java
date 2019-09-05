package seleniumutils.methods.SelfHealing;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;
import seleniumutils.methods.GetElementUsingBy;
import seleniumutils.methods.SelfHealing.self_healing.SelfHealing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;


public class SelfHealingWrapper extends GetElementUsingBy {

    public WebElement element;
    public String t;
    public static HashMap<String, String> elementProperties = new HashMap();
    public String src = HistoricalPropertiesUtil.getHistoricalPropertiesPath();

    public String SelfHealingWrapper(String ElementObjectRepo) {
            try {
                SelfHealing selfHealing = new SelfHealing(driver);
                HistoricalProperty hp = get_eTAF_Historical_Properties(ElementObjectRepo);
                System.out.println("Historical Properties: " + hp.toString());
                String updatedJson = selfHealing.shMain(hp.toString());
                System.out.println("New Properties: " + updatedJson);
                return updatedJson;
            }
            catch(Exception e) {
                e.printStackTrace();
                assertTrue(false);}
        return null;
    }



    public HistoricalProperty get_eTAF_Historical_Properties(String element) throws IOException {

        FileInputStream fis = new FileInputStream(new File(src));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet SheetName = workbook.getSheetAt(0);

        HashMap<String, Integer> propertyByColumn = HistoricalPropertiesUtil.getPropertyByColumn();

            HistoricalProperty hp = new HistoricalProperty();
            int totalNumberRows = SheetName.getPhysicalNumberOfRows();

            for (int i = 1; i < totalNumberRows; i++) {
                if(SheetName.getRow(i).getCell(0).getStringCellValue().equals(element)) {
                    for (String key : propertyByColumn.keySet()) {
                        elementProperties.put(key, SheetName.getRow(i).getCell(propertyByColumn.get(key)).getStringCellValue());
                    }
                }
            }

        hp.setAbsLocation(elementProperties.get("abs location"));
       // hp.setAbsLocation("location");
        hp.setAltText(elementProperties.get("alt"));
        hp.setName(elementProperties.get("name"));
        hp.setClassName(elementProperties.get("class"));
        hp.setInnerText(elementProperties.get("innerText"));
        hp.setColor(elementProperties.get("color"));
        hp.setTagName(elementProperties.get("tagName").toLowerCase());
        //String outerHTML = elementProperties.get("outerHTML");
        hp.setOuterText(elementProperties.get("outerHTML").replaceAll("\"", "\\\\\""));
//        hp.setOuterText(elementProperties.get(outerHTML));
        //hp.setOuterText("<input id='Username' name='UPN' type='text' placeholder='GPN number' style='background-color:transparent; border: 1px solid'>");
        hp.setHref(elementProperties.get("href"));
        hp.setHtmlId(elementProperties.get("id"));
        hp.setSize(elementProperties.get("size"));
       // hp.setSize("size");
        System.out.println(hp.toString());

        return hp;
    }


}
