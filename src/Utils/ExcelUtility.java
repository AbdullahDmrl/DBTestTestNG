package Utils;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtility {

    public static int getrowNumber(String path, String sheetName)  {

      FileInputStream inputStream=null;
        Workbook workbook = null;
        try {
            inputStream=new FileInputStream(path);
            workbook = WorkbookFactory.create(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Sheet sheet=workbook.getSheet(sheetName);
    //  int rowCount=sheet.getPhysicalNumberOfRows();
        int rowCount=sheet.getPhysicalNumberOfRows();

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rowCount;
    }
    public static int getCloumnNumber(String path, String sheetName, int rowNumber) {

        Workbook workbook = null;
        try {
            FileInputStream inputStream=new FileInputStream(path);
            workbook = WorkbookFactory.create(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Sheet sheet=workbook.getSheet(sheetName);
       Row row= sheet.getRow(rowNumber);
       int cellNumber=row.getPhysicalNumberOfCells();


        return cellNumber;
    }


    public static List<List<String>> getlistData(String path,String sheetName,int clumCount) {
        List<List<String>> returnList=new ArrayList<>();
        Workbook workbook=null;
        try {
            FileInputStream inputStream=new FileInputStream(path);
            workbook= WorkbookFactory.create(inputStream);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        Sheet sheet=workbook.getSheet(sheetName);
        int rowCount=sheet.getPhysicalNumberOfRows();

        for(int i=0; i< rowCount; i++)
        {
            List<String> rowList=new ArrayList<>();
            Row row=sheet.getRow(i);
          //  int clumnCount=row.getPhysicalNumberOfCells();
            for(int j=0; j < clumCount;j++ )
            {
                rowList.add(row.getCell(j).toString());
            }
            returnList.add(rowList);
        }

        return returnList;
    }

    public static void writeExcel(String path, ITestResult result, String browser, String time){
        File file=new File(path);
        String sheetName="Sheet1";

        if (!file.exists()) {   //dosya yok ise
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(sheetName);
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue(result.getTestClass().getName());
            cell = row.createCell(1);
            cell.setCellValue(result.getMethod().getMethodName());
            cell = row.createCell(2);
            cell.setCellValue("Test is passed= "+result.isSuccess());
            cell = row.createCell(3);
            cell.setCellValue(browser);
            cell = row.createCell(4);
            cell.setCellValue(time);

            FileOutputStream outputStream=null;
            try {
                outputStream = new FileOutputStream(path);
                workbook.write(outputStream);

                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            FileInputStream inputStream=null;
            Workbook workbook= null;
            try {
                inputStream=new FileInputStream(path);
                workbook = WorkbookFactory.create(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Sheet sheet=workbook.getSheet(sheetName);
            int rowCount=sheet.getPhysicalNumberOfRows();
            Row row=sheet.createRow(rowCount);
            Cell cell = row.createCell(0);
            cell.setCellValue(result.getTestClass().getName());
            cell = row.createCell(1);
            cell.setCellValue(result.getMethod().getMethodName());
            cell = row.createCell(2);
            cell.setCellValue("Test is passed= "+result.isSuccess());
            cell = row.createCell(3);
            cell.setCellValue(browser);
            cell = row.createCell(4);
            cell.setCellValue(time);

            FileOutputStream outputStream=null;
            try {
                inputStream.close();
                outputStream=new FileOutputStream(path);
                workbook.write(outputStream);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public static void main(String[] args) {

       int row=getrowNumber("src/ApachePOI/searchTable.xlsx","Sheet1");
        System.out.println("row number="+ row+" ");
      //  int column=getCloumnNumber("src/ApachePOI/resources/serchTexts.xlsx","Sheet1",0);


        List<List<String>> excellData1= getlistData("src/ApachePOI/searchTable.xlsx","Sheet1",2);

       System.out.println("List =" +excellData1);


       // List<List<String>> excellData= getlistData("src/ApachePOI/WriteInTheExcelFile.xlsx","Sheet1",3);



      // System.out.println("list = " + excellData);

        Object[][] dataExcell=new Object[excellData1.size()][excellData1.get(0).size()];
        for (int i = 0; i < excellData1.size(); i++) {
            for (int j = 0; j < excellData1.get(i).size(); j++) {

                dataExcell[i][j]=excellData1.get(i).get(j);

            }

        }


        for (int i = 0; i < dataExcell.length; i++) {
            for (int j = 0; j < dataExcell[i].length; j++) {
                System.out.print(dataExcell[i][j]+" ");
            }
            System.out.println();
        }


    }

    }


