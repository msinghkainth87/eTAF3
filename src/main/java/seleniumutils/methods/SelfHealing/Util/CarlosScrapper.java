package seleniumutils.methods.SelfHealing.Util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CarlosScrapper {


    public class ScrapeElementProperties {


        public void scrapeProperties(WebDriver driver, String browserType, String URL, String logicalName, String passedTag)
                throws FileNotFoundException {
            try {
                // Initialization of workbook and work sheet
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Element Properites");

                // Initializes WebDriver and passes it the browser & URL


                // Creates a List of WebElements
                List<WebElement> foundFontTags = driver.findElements(By.tagName(passedTag));

                // Provides a count of items within the List of WebElements
                int tagQuantity = foundFontTags.size();
                System.out.println("Quantity of <" + passedTag + "> tag links on this page: " + tagQuantity);

                // Counter used for adaptable array size
                int arrayCounter = tagQuantity + 1;

                // Array initialization rows adapt to the amount of elements, columns are static
                // to match the data we are looking to output on the excel
                String[][] elementPropertyData = new String[arrayCounter][13];

                //counter for id number of the element found
                int elementCounter = 0;

                // Creating first row and population headers
                Row headers = sheet.createRow(0);
                headers.createCell(0).setCellValue("ID");
                headers.createCell(1).setCellValue("Logical_Name");
                headers.createCell(2).setCellValue("tagName");
                headers.createCell(3).setCellValue("absLocation");
                headers.createCell(4).setCellValue("size");
                headers.createCell(5).setCellValue("color");
                headers.createCell(6).setCellValue("name");
                headers.createCell(7).setCellValue("href");
                headers.createCell(8).setCellValue("alt_text");
                headers.createCell(9).setCellValue("classname");
                headers.createCell(10).setCellValue("innertext");
                headers.createCell(11).setCellValue("outertext");
                headers.createCell(12).setCellValue("id");

                // For loop to iterate through all WebElements of the specified tag on the page
                for (WebElement foundFontTag : foundFontTags) {
                    String grabText = foundFontTag.getText();
                    String grabTag = foundFontTag.getAttribute("tagName");
                    String grabLocation = foundFontTag.getLocation().toString(); //String grabLocation = foundFontTag.getAttribute("absLocation");
                    String grabSize = foundFontTag.getSize().toString();
                    String grabColor = foundFontTag.getCssValue("background-color").toString();
                    String grabName = foundFontTag.getAttribute("name");
                    String grabHREF = foundFontTag.getAttribute("href");
                    String grabAlt = foundFontTag.getAttribute("alt");
                    String grabClass = foundFontTag.getAttribute("class");
                    String grabInnerText = foundFontTag.getAttribute("innerText");
                    String grabOuterHTML = foundFontTag.getAttribute("outerHTML");
                    String grabId = foundFontTag.getAttribute("id");

                    // If extraction matches increase counter, else increase nonMatchCounter and
                    // print non matching text
                    elementCounter++;

                    System.out.println("The text is: " + grabText);
                    System.out.println("The tag is: " + grabTag);
                    System.out.println("The location is" + grabLocation);
                    System.out.println("The size is: " + grabSize);
                    System.out.println("The color is: " + grabColor);
                    System.out.println("The name is: " + grabName);
                    System.out.println("The HREF is: " + grabHREF);
                    System.out.println("The alttext is: " + grabAlt);
                    System.out.println("The class is: " + grabClass);
                    System.out.println("The innertext is: " + grabInnerText);
                    System.out.println("The outerHTML is: " + grabOuterHTML);
                    System.out.println("The id is: " + grabId);
                    System.out.println();

                    String elementIDCounter = Integer.toString(elementCounter);
                    elementPropertyData[elementCounter][0] = elementIDCounter;
                    elementPropertyData[elementCounter][1] = logicalName;
                    elementPropertyData[elementCounter][2] = grabTag;
                    elementPropertyData[elementCounter][3] = grabLocation;
                    elementPropertyData[elementCounter][4] = grabSize;
                    elementPropertyData[elementCounter][5] = grabColor;
                    elementPropertyData[elementCounter][6] = grabName;
                    elementPropertyData[elementCounter][7] = grabHREF;
                    elementPropertyData[elementCounter][8] = grabAlt;
                    elementPropertyData[elementCounter][9] = grabClass;
                    elementPropertyData[elementCounter][10] = grabInnerText;
                    elementPropertyData[elementCounter][11] = grabOuterHTML;
                    elementPropertyData[elementCounter][12] = grabId;

                } // END font grabber for loop

                for (int i = 1; i < arrayCounter; i++) {
                    Row row = sheet.createRow(i);
                    for (int j = 0; j < 10; j++) {
                        row.createCell(j).setCellValue(elementPropertyData[i][j]);
                    }//END column counter
                }//END row counter

                System.err.println("Found " + elementCounter + " potential replacement for healing element");

                driver.close();
                FileOutputStream fileOut = new FileOutputStream("ElementProperties.xlsx");
                workbook.write(fileOut);
                fileOut.close();

                // Closing the workbook
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }// END scrapeProperties - by tag

        public void scrapeObjectRepositoryPropertyScraper(WebDriver driver, String browserType, String URL, String accessType, String accessName) {
            // Initializes WebDriver and passes it the browser & URL


            WebElement elementFromRepo = driver.findElement(getElementByAttributes(accessType, accessName));

            String grabText = elementFromRepo.getText();
            String grabTag = elementFromRepo.getAttribute("tagName");
            String grabLocation = elementFromRepo.getLocation().toString(); //String grabLocation = foundFontTag.getAttribute("absLocation");
            String grabSize = elementFromRepo.getSize().toString();
            String grabColor = elementFromRepo.getCssValue("background-color").toString();
            String grabName = elementFromRepo.getAttribute("name");
            String grabHREF = elementFromRepo.getAttribute("href");
            String grabAlt = elementFromRepo.getAttribute("alt");
            String grabClass = elementFromRepo.getAttribute("class");
            String grabInnerText = elementFromRepo.getAttribute("innerText");
            String grabOuterHTML = elementFromRepo.getAttribute("outerHTML");
            String grabId = elementFromRepo.getAttribute("id");


        }

        public By getElementByAttributes(String attr, String access_name) {
            switch (attr.toLowerCase()) {
                case "id":
                    return By.id(access_name);
                case "name":
                    return By.name(access_name);
                case "class":
                    return By.className(access_name);
                case "xpath":
                    return By.xpath(access_name);
                case "css":
                    return By.cssSelector(access_name);
                case "linkText":
                    return By.linkText(access_name);
                case "partialLinkText":
                    return By.partialLinkText(access_name);
                case "tagName":
                    return By.tagName(access_name);
                default:
                    return null;

            }
        }


    }
}
