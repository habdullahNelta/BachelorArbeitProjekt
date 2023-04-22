import Page.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTest extends TestBase{
  private LoginPage loginPage ;



    @Test(priority = 1)
    public void userHitsLogInLink() {
        loginPage = new LoginPage(driver);
        loginPage.hitLoginLink().click();
    }

    @Test(priority = 2)
    @Parameters("email")
    public void userEntersEmail(String email) {
        loginPage.enterEmail().sendKeys(email);
    }
    @Test(priority = 3)
    @Parameters("password")
    public void userEntersPassword(String password) {
        loginPage.enterPassword().sendKeys(password);
    }
    @Test(priority = 4)
    public void userClickLoginButton() {
        loginPage.clickOnLogin().click();
    }
    @Test(priority = 5)
    public void loginShouldBeUnsuccessful() throws InterruptedException {
        // boolean bool= ;
        Assert.assertTrue(loginPage.hasAlreadyLoggedIn().isDisplayed());
    }

    @Test(priority = 6)
    public void invalidEmailAddress() throws InterruptedException {
        // boolean bool= ;
        Assert.assertTrue(loginPage.invalidEmailAddress().isDisplayed());
    }
}
