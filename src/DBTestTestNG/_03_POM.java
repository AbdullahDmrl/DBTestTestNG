package DBTestTestNG;

import Utils.SingleDriver;
import Utils.ParameterDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class _03_POM {
    private static WebDriver driver= SingleDriver.driver;

   public _03_POM() {
        PageFactory.initElements(getDriver(), this);
    }
    @FindBy(id="mat-input-0")
    public WebElement username;

    @FindBy(id="mat-input-1")
    public WebElement password;

    @FindBy(css="button[aria-label='LOGIN']")
    public WebElement loginButton;

    @FindBy(xpath = "(//button[@class='consent-give'])[1]")
    public WebElement acceptCookies;



    @FindBy(xpath = "(//span[text()='Setup'])[1]")
    public WebElement setupOne;

    @FindBy(xpath = "//span[text()='Parameters']")
    public WebElement parameters;

    @FindBy(xpath = "//span[text()='Countries']")
    public WebElement country;
    @FindBy(xpath = "//span[text()='States']")
    public WebElement staTes;
    @FindBy(xpath = "(//span[text()='Cities'])[1]")
    public WebElement cities ;

    @FindBy(xpath = "//span[text()='Student']")
    public WebElement Student;

    @FindBy(xpath = "//span[text()='Students']")
    public WebElement Students;

    @FindBy(xpath = "//mat-select[@role='combobox']/div")
    public WebElement selectCountry;

    @FindBy(css = "div>mat-option")
    public List<WebElement> countryAllOptions;

    @FindBy(css = "ms-search-button>div.control-full>button")
    public WebElement searchButton;


    @FindBy(xpath = "//table/tbody/tr")
    public List<WebElement> elementsList;

    @FindBy(xpath = "//div//span[text()='Status']")
    public WebElement Status;

    @FindBy(xpath = "//div//span[text()='Grade Level']")
    public WebElement gradeLevel;

    @FindBy(xpath = "//div/ms-search-button")
    public WebElement searchDialogBtn;

    @FindBy(xpath = "//table/tbody/tr/td[1]")
    public List<WebElement> studentIDList;

    @FindBy(xpath = "//table/tbody/tr/td[3]")
    private List<WebElement> studentsFullnameList;

    @FindBy(xpath = "//table/tbody/tr/td[7]")
    private List<WebElement> studentsgradeLevelList;

    @FindBy(xpath = "//table/tbody/tr/td[9]")
    private List<WebElement> studentsstatusLevelList;



    public void clickFunction(WebElement element)
    {
        waitUntilClickable(element);
        scrollToElement(element);
        element.click();
    }

    public void sendKeysFunction(WebElement element, String value)
    {
        waitUntilVisible(element);
        scrollToElement(element);
        element.clear();
        element.sendKeys(value);
    }

    public void selectFunction(WebElement element, String value)
    {
        waitUntilVisible(element);
        scrollToElement(element);
        element.click();
         Select select=new Select(element);

        select.selectByVisibleText(value);
    }

    public void listWebelementsSelect(List<WebElement> listName, String option )
    {


        for(WebElement e: listName)
        {
            if (e.getText().contains(option)) {
                e.click();
                break;
            }
        }

    }




    public void waitUntilClickable(WebElement element){
        WebDriverWait wait=new WebDriverWait(getDriver(),Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitUntilVisible(WebElement element){
        WebDriverWait wait=new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void scrollToElement(WebElement element){
        JavascriptExecutor js= (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    public static WebDriver getDriver(){

       if (SingleDriver.driver==null){
           driver=ParameterDriver.driver;
       }
       else if(ParameterDriver.driver==null){
           driver= SingleDriver.driver;
       }

        return driver;
    }
}
