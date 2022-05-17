package DBTestTestNG;

import Utils.DBUtilities;
import Utils.ExcelUtility;
import Utils.ParameterDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class _02_statesDBCheckCrossBrowserSteps extends ParameterDriver {

    List<WebElement> uiList;

    @Test()
    void Test1StatesUICheck()
    {
        _03_POM elements=new _03_POM();
        elements.clickFunction(elements.setupOne);
        elements.clickFunction(elements.parameters);
        elements.clickFunction(elements.staTes);
        elements.clickFunction(elements.selectCountry);
        elements.listWebelementsSelect(elements.countryAllOptions,"United States of America");
        elements.clickFunction(elements.searchButton);
        uiList= elements.elementsList;
        List<List<String>> excelList= ExcelUtility.getlistData("src/ApachePOI/AmericaStates.xlsx","Sheet1",4);

        for (int i = 0; i < uiList.size(); i++) {
            for (int j = 1; j < excelList.get(i).size(); j++) {
                //   System.out.println(uiList.get(i).getText()+" "+excelList.get(i).get(j)); // kontrol icin yazildi
                Assert.assertTrue(uiList.get(i).getText().contains(excelList.get(i).get(j)));
            }
        }
    }

    @Test()
    void Test2CreatingAndInsertingMySQLTable()
    {
        List<String> tableName = DBUtilities.getNamesOfTables("show tables");

        if (!tableName.contains("States")) {
            DBUtilities.createTable("create table States(\n" +
                    " id int primary key,\n" +
                    " Name varchar(50) not null,\n" +
                    " ShortName varchar(50) not null,\n" +
                    " Country varchar(50) not null\n" + ");");

            DBUtilities.insertIntoTable("insert into States (id,Name,ShortName,Country) values ('1','Alabama','AL','United States of America')");
            DBUtilities.insertIntoTable("insert into States (id, Name,ShortName,Country) values ('2', 'Alaska','AK','United States of America' )");
            DBUtilities.insertIntoTable("insert into States (id, Name,ShortName,Country) values ('3', 'Arizona','AZ','United States of America' )");
            DBUtilities.insertIntoTable("insert into States (id, Name,ShortName,Country) values ('4', 'Arkansas','AR','United States of America' )");
            DBUtilities.insertIntoTable("insert into States (id, Name,ShortName,Country) values ('5', 'California','CA','United States of America' )");
            DBUtilities.insertIntoTable("insert into States (id, Name,ShortName,Country) values ('6', 'Colorado','CO','United States of America' )");
            DBUtilities.insertIntoTable("insert into States (id, Name,ShortName,Country) values ('7', 'Connecticut','CT','United States of America' )");
            DBUtilities.insertIntoTable("insert into States (id, Name,ShortName,Country) values ('8', 'Delaware','DE','United States of America' )");
            DBUtilities.insertIntoTable("insert into States (id, Name,ShortName,Country) values ('9', 'Dictrict of Columbia','DC','United States of America' )");
            DBUtilities.insertIntoTable("insert into States (id, Name,ShortName,Country) values ('10', 'Florida','FL','United States of America' )");
        }
    }

    @Test()
    void Test3CheckingResultsMatch()
    {

        List<List<String>> dbList= DBUtilities.getlistData("select * from States");

        for (int i = 0; i < uiList.size(); i++) {
            for (int j = 0; j < dbList.get(i).size(); j++) {
                // System.out.println(uiList.get(i).getText()+" "+dbList.get(i).get(j)); // to control
                Assert.assertTrue(uiList.get(i).getText().contains(dbList.get(i).get(j)));
            }
        }

    }

    }





