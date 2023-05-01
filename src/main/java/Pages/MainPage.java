package Pages;

import com.epam.healenium.SelfHealingDriver;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
    public MainPage(SelfHealingDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
