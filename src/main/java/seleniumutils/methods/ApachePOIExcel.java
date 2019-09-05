package seleniumutils.methods;

import com.github.javafaker.Faker;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

public class ApachePOIExcel {

    public static XSSFWorkbook readXL(String fileName)
    {
        FileInputStream fis = null;
        XSSFWorkbook workbook;
        try{
            fis=new FileInputStream(fileName);
            workbook = new XSSFWorkbook(fis);
            fis.close();
        }catch(Exception e){
            workbook =null;
        }
        return workbook;
    }

    /**TODO Use the writeXL method from Builproperties method of Self-Healing
     * write contents to an xl file
     * @param fileName : String : Excel workbook name
     * @throws Throwable
     */
    public static void write2XL(String fileName){
       try {
           XSSFWorkbook workbook = new XSSFWorkbook();
           //Add a worksheet
           XSSFSheet sheet = workbook.createSheet("Sheet1");
           //Create two rows and add content in first 3 cells of each row
           for (int i = 0; i < 2; i++) {
               XSSFRow row = sheet.createRow(i);
               row.createCell(0).setCellValue(new Faker().name().firstName());
               row.createCell(1).setCellValue(new Faker().address().streetAddressNumber());
               row.createCell(2).setCellValue(new Faker().phoneNumber().phoneNumber());
           }
           FileOutputStream fout = new FileOutputStream(fileName);
           workbook.write(fout);
           fout.flush();
           fout.close();
       }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param sheetName : String : sheet name
     * @throws Throwable
     */
    public static void printSheet(XSSFWorkbook workbook,String sheetName){
        try
        {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            for(int i=0;i<sheet.getPhysicalNumberOfRows();i++)
            {
                Row currentRow = sheet.getRow(i);
                for(int j=0;j<currentRow.getPhysicalNumberOfCells();j++)
                {
                    Cell currentCell = currentRow.getCell(j);
                    System.out.print(currentCell.getStringCellValue() + "\t");

                }
                System.out.println("\n");

            }

            }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * perform delete operations on a workbook
     * @param sheetName : String : sheet name
     * @throws Throwable
     */

    public static void deleteFromXL(XSSFWorkbook workbook,String sheetName){
        System.out.println("Deleting data from Random.xlsx.....");
        XSSFSheet sheet = workbook.getSheet(sheetName);
        System.out.println("removing Row 1....");
        sheet.removeRow(sheet.getRow(1));
        System.out.println("Removing column 2 from Row 0....");
        Row firstRow = sheet.getRow(0);
        firstRow.removeCell(firstRow.getCell(2));
        System.out.println("Printing the contents of Random.xlsx now.....");

        for(int i=0;i<sheet.getPhysicalNumberOfRows();i++)
        {
            Row currentRow = sheet.getRow(i);
            for(int j=0;j<currentRow.getPhysicalNumberOfCells();j++)
            {
                Cell currentCell = currentRow.getCell(j);
                System.out.print(currentCell.getStringCellValue() + "\t");

            }

        }
    }


    /**
     *
     * @param sheetName : String : sheet name
     * @throws Throwable
     */
    public static void getWorksheet(XSSFWorkbook workbook,String sheetName){
        XSSFSheet sheet = workbook.getSheet(sheetName);
    }

    /**
     * Returns the sheetName given the file path and sheet index
     * @param sheetIndex : String : sheet name
     * @return SheetName
     * @throws Throwable
     */
    public static String getSheetName(int sheetIndex, String excelPath){
        String sheetName="";
        XSSFWorkbook workbook=readXL(excelPath);
        if(workbook!=null)
            sheetName= workbook.getSheetName(sheetIndex);
        return sheetName;
    }

    /**
     * Returns the sheetIndex given the file path and sheet name
     * @param sheetName : String : sheet name
     * @return Sheet Index (0 based)
     * @throws Throwable
     */
    public static int getSheetIndexFromName(String sheetName, String excelPath){
        int sheetIndex=-1;
        XSSFWorkbook workbook=readXL(excelPath);
        if(workbook!=null)
            sheetIndex= workbook.getSheetIndex(sheetName);
        return sheetIndex;
    }

    /**
     *access the rows and cells
     * @param sheetName : String : sheet name
     * @throws Throwable
     */
    public static String getCell(XSSFWorkbook workbook,String sheetName,int row, int column){
        XSSFSheet sheet = workbook.getSheet(sheetName);
        String cellValue="";
        Row currentRow = sheet.getRow(row);
        Cell currentCell = currentRow.getCell(column);
        cellValue = currentCell.getStringCellValue();
        return cellValue;
    }
    /** convert the sheet data into hash with keys as column headers
     * @param filename : String : excel workbook name
     */
    public static HashMap<String,HashMap<String,HashMap<String, String>>> readXL2Hash(String filename){
        XSSFWorkbook workbook=readXL(filename);
        ElementObject pObj = new ElementObject();
        HashMap<String,HashMap<String,HashMap<String, String>>> xlData=new HashMap<>();
            for(int k=0;k<workbook.getNumberOfSheets();k++) {
                XSSFSheet sheet = workbook.getSheetAt(k);
                Row HeaderRow = sheet.getRow(0);
                HashMap<String,HashMap<String, String>> sheetData=new HashMap<String,HashMap<String, String>>();
                for (int i = 1; i < sheet.getPhysicalNumberOfRows()-1; i++) {
                    Row currentRow = sheet.getRow(i);
                    HashMap<String, String> currentHash = new HashMap<String, String>();
                    for (int j = 2; j < 11; j++) {
                        Cell currentCell = currentRow.getCell(j);
                        if (currentCell == null)
                            continue;
                        switch (currentCell.getCellType()) {
                            case BLANK:
                                continue;
                            case STRING:
                                String key=HeaderRow.getCell(j).getStringCellValue().toLowerCase();
                                String value=currentCell.getStringCellValue();
                                currentHash.put(key, value);
                                break;
                            case NUMERIC:
                                key=HeaderRow.getCell(j).getStringCellValue().toLowerCase();
                                currentHash.put(key, String.valueOf(currentCell.getNumericCellValue()));
                                break;
                            case BOOLEAN:
                                key=HeaderRow.getCell(j).getStringCellValue().toLowerCase();
                                currentHash.put(key, String.valueOf(currentCell.getBooleanCellValue()));
                                break;
                        }

                    }
                    sheetData.put(currentRow.getCell(1).getStringCellValue().toLowerCase(), currentHash);
                }

                xlData.put(sheet.getSheetName().toLowerCase(),sheetData);
            }
            return xlData;
        }

    /** convert the sheet data into hash with keys as column headers
     * @param filename : String : excel workbook name
     */
    public static HashMap<String, ElementObject> readXL2FlatHash(String filename){
        XSSFWorkbook workbook=readXL(filename);
        HashMap<String, ElementObject> flatData=new HashMap<>();
        for(int k=0;k<workbook.getNumberOfSheets();k++) {
            XSSFSheet sheet = workbook.getSheetAt(k);
            String sheetname=sheet.getSheetName();
            Row HeaderRow = sheet.getRow(0);
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row currentRow = sheet.getRow(i);
                for (int j = 2; j < 11; j++) {
                    Cell currentCell = currentRow.getCell(j);
                    if (currentCell == null)
                        continue;
                    String x = currentCell.getStringCellValue();
                    switch (currentCell.getCellType()) {
                        case BLANK:
                            continue;
                        case STRING:
                            String key=HeaderRow.getCell(j).getStringCellValue().toLowerCase();
                            String value=currentCell.getStringCellValue();
                            ElementObject pObjString = new ElementObject();
                            pObjString.setAccessType(key);
                            pObjString.setAccessName(value);
                            flatData.put(sheet.getSheetName().toLowerCase()+"."+currentRow.getCell(1).getStringCellValue().toLowerCase(),pObjString);
                            break;
                        case NUMERIC:
                            key=HeaderRow.getCell(j).getStringCellValue().toLowerCase();
                            ElementObject pObjnum = new ElementObject();
                            pObjnum.setAccessType(key);
                            pObjnum.setAccessName(String.valueOf(currentCell.getNumericCellValue()));
                            flatData.put(sheet.getSheetName().toLowerCase()+"."+currentRow.getCell(1).getStringCellValue().toLowerCase(),pObjnum);
                            break;
                    }

                }
            }

        }
        return flatData;
    }

}
