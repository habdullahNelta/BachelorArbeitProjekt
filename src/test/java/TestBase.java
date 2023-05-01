import com.epam.healenium.SelfHealingDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import qa.util.LoadData;

import java.util.logging.Level;

import static qa.util.Logger.logger;

public class TestBase {
    // static public WebDriver driver;
    static public SelfHealingDriver driver;

     TestRailIntegration testRail = new TestRailIntegration();

    private final String WebAppPath = "https://demowebshop.tricentis.com";
    private final String browser = LoadData.userData.getProperty("browser");

    @BeforeSuite(alwaysRun = true)
    public void init_driver() {
        logger(Level.INFO, "browser value is " + browser);

        if (browser.equals("chrome")) {

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.setHeadless(false);

            WebDriver delegate = new ChromeDriver(options);
            driver = SelfHealingDriver.create(delegate);

        } else if (browser.equals("firefox")) {

            WebDriver delegate = new FirefoxDriver();
            driver = SelfHealingDriver.create(delegate);

        } else if (browser.equals("edge")) {

            EdgeOptions edgeOption = new EdgeOptions();
            edgeOption.addArguments("--remote-allow-origins=*");
            WebDriver delegate = new EdgeDriver(edgeOption);
            driver = SelfHealingDriver.create(delegate);

        } else {
            logger(Level.WARNING, "Please pass the correct browser value: " + browser);
        }

        driver.navigate().to(WebAppPath);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }


    @AfterSuite(alwaysRun = true)
    @Parameters({"suiteXmlFile"})
    public void QuitDriver(@Optional("noUpdate") String suiteXmlFile) throws Exception {
        driver.quit();
        testRail.TestrailUpdateCSVtoYml(suiteXmlFile);
    }
}
