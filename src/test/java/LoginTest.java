import Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.*;


public class LoginTest extends TestBase {
    public LoginPage loginPage;

    @Test(groups = {"LoginWithCorrectEmailAndPassword",
            "LoginWithIncorrectEmailAndPassword",
            "LoginUnsuccessfulInvalidEmail"}, priority = 1)
    public void userHitsLogInLink() {
        loginPage = new LoginPage(driver);
        loginPage.hitLoginLink().click();
    }
    @Test(groups = {"LoginWithCorrectEmailAndPassword",
            "LoginWithIncorrectEmailAndPassword",
            "LoginUnsuccessfulInvalidEmail"}, priority = 2)
    @Parameters("email")
    public void userEntersEmail(String email) {
        loginPage.enterEmail().sendKeys(email);
    }

    @Test(groups = {"LoginWithCorrectEmailAndPassword",
            "LoginWithIncorrectEmailAndPassword",
            "LoginUnsuccessfulInvalidEmail"}, priority = 3)
    @Parameters("password")
    public void userEntersPassword(String password) {
        loginPage.enterPassword().sendKeys(password);
    }

    @Test(groups = {"LoginWithCorrectEmailAndPassword",
            "LoginWithIncorrectEmailAndPassword",
            "LoginUnsuccessfulInvalidEmail"}, priority = 4)
    public void userClickLoginButton() {
        loginPage.clickOnLogin().click();
    }

    @Test(groups = {"LoginWithCorrectEmailAndPassword"}, priority = 5)
    public void verifyLoginSuccessful() {
        // loginPage.checkSuccessfulLogin().isDisplayed(); // TODO: 24.04.2023
    }

    @Test(groups = {"LoginWithIncorrectEmailAndPassword"}, priority = 6)
    public void loginShouldBeUnsuccessful() throws InterruptedException {
        // boolean bool= ;
       // Thread.sleep(10000);
        Assert.assertTrue(loginPage.hasAlreadyLoggedIn().isDisplayed());
    }

    @Test(groups = {"LoginUnsuccessfulInvalidEmail"}, priority = 7)
    public void invalidEmailAddress() throws InterruptedException {
        Assert.assertTrue(loginPage.invalidEmailAddress().isDisplayed());
    }
}
