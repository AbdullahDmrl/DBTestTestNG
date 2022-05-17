package Utils;

import DBTestTestNG._03_POM;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.io.FileHandler;
import org.testng.*;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SingleDriver {

    public static WebDriver driver;

    @BeforeClass
    public void BaslangicIslemleri()

    {   System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY,"true");
        System.setProperty("webdriver.chrome.driver","drivers/chromedriver100.exe");
        driver=new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        driver.get("https://demo.mersys.io/");

        LoginTest();
    }

    @DataProvider(name = "getDataFromExcell")
    public Object[][] getDataExcell() {
        List<List<String>> excellData= ExcelUtility.getlistData("src/ApachePOI/searchTable.xlsx","Sheet1",1);

        Object[][] dataExcell=new Object[excellData.size()][excellData.get(0).size()];
        for (int i = 0; i < excellData.size(); i++) {
            for (int j = 0; j < excellData.get(i).size(); j++) {

                dataExcell[i][j]=excellData.get(i).get(j);

            }

        }

        return dataExcell;


    }



    @AfterMethod
    public void printError(ITestResult result) {
        LocalDateTime date=LocalDateTime.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if (result.getStatus() == ITestResult.FAILURE) {
            String clsName = result.getTestClass().getName();
            clsName = clsName.substring(clsName.lastIndexOf(".") + 1);
            System.out.println("Test " + clsName + "." +
                    result.getName() + " FAILED");

            TakesScreenshot ts= (TakesScreenshot) driver;
            File screenShots=ts.getScreenshotAs(OutputType.FILE);
            try {
                FileHandler.copy(screenShots, new File("src/ZScreenShots"+date.format(formatter)+".png"));

            } catch (IOException e) {
                e.printStackTrace();
            }
           }
        ExcelUtility.writeExcel("src/ZExcell/Results.xlsx",result,"chrome",date.format(formatter));

    }



    @AfterClass
    public void BitisIslemleri() {

        driver.quit();
    }


    void LoginTest()
    {
        _03_POM elements=new _03_POM();

        elements.sendKeysFunction(elements.username,"richfield.edu" );
        elements.sendKeysFunction(elements.password,"Richfield2020!");
        elements.clickFunction(elements.acceptCookies);
        elements.clickFunction(elements.loginButton);

    }


}
