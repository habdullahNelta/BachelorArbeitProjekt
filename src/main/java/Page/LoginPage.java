package Page;

import com.epam.healenium.SelfHealingDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage  extends MainPage {
  // private SelfHealingDriver driver;


    @FindBy(linkText = "Log in")
    WebElement hitLoginLinkFind;
    @FindBy(id = "Email")
    WebElement enterEmailFind;
    @FindBy(id = "Password")
    WebElement enterPasswordFind;
    @FindBy(xpath = "//input[@type=\"submit\"][@value=\"Log in\"]")
    WebElement clickOnLoginFind;

    @FindBy(xpath = "//li[contains(text(), 'No customer account found')]")
    WebElement hasAlreadyLoggedInFind;
    @FindBy(linkText = "Log out")
    WebElement checkUnsuccessfulLoginFind;
    @FindBy(linkText = "Log in")
    WebElement getLoginLinkFind;
    @FindBy(linkText = "Shopping cart")
    WebElement getShoppingCartLinkFind;

    @FindBy(xpath = "/html/body/div[4]/div[1]/div[4]/div[2]/div/div[2]/div[1]/div[2]/div[2]/form/div[2]/span/span")
    WebElement invalidEmailAddressFind;

    // Constructor of the page class:
    public LoginPage(SelfHealingDriver driver) {
        super(driver);
    }

    public WebElement hitLoginLink() {
        return hitLoginLinkFind;
    }

    public WebElement enterEmail() {
       return   enterEmailFind;
    }

    public WebElement enterPassword() {
        return enterPasswordFind;
    }

    public WebElement clickOnLogin() {
       return   clickOnLoginFind;
    }

    public WebElement hasAlreadyLoggedIn() {
        return hasAlreadyLoggedInFind;
    }

    public WebElement checkUnsuccessfulLogin() {
        return  checkUnsuccessfulLoginFind;
    }

    public WebElement getLoginLink() {
        return  getLoginLinkFind;
    }

    public WebElement getShoppingCartLink() {
        return   getShoppingCartLinkFind;
    }

    public WebElement invalidEmailAddress() {
        return invalidEmailAddressFind;
    }
}
