import com.epam.healenium.SelfHealingDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import qa.util.LoadData;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestBase {
    // static public WebDriver driver;
    static public SelfHealingDriver driver;
    public static void logger(Level level, String message) {
        Logger logger = Logger.getAnonymousLogger();
        logger.log(level, message);
    }

    String WebAppPath = "https://demowebshop.tricentis.com";
    String browser = LoadData.userData.getProperty("browser");

    @BeforeSuite
    public void init_driver() {
        logger(Level.INFO, "browser value is: " + browser);

        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.setHeadless(true);

            WebDriver delegate = new ChromeDriver(options);
            driver = SelfHealingDriver.create(delegate);
        } else if (browser.equals("firefox")) {

            WebDriverManager.firefoxdriver().setup();
            WebDriver delegate = new FirefoxDriver();
            driver = SelfHealingDriver.create(delegate);

        } else if (browser.equals("edge")) {

            WebDriverManager.edgedriver().setup();
            EdgeOptions edgeOption = new EdgeOptions();
            edgeOption.addArguments("--remote-allow-origins=*");
            WebDriver delegate = new EdgeDriver(edgeOption);
            driver = SelfHealingDriver.create(delegate);

        } else {
            logger(Level.WARNING, "Please pass the correct browser value: " + browser);
        }






        /* normal
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        //   options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);*/

        driver.navigate().to(WebAppPath);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    TestRail testRail = new TestRail();

    @AfterSuite
    // @Parameters({"suiteXmlFile"})
    public void QuitDriver(/*String suiteXmlFile*/) {
        driver.quit();
        //testRail.TestrailUpdateCSVtoYml(suiteXmlFile);
    }
}
