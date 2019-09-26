package pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Экран главной страницы приложения
 */
public class MainPage extends BasePage {

    /**
     * Кнопка выбора фильтров
     */
    @AndroidFindBy(id = "menu_filter")
    public AndroidElement filterButton;
    /**
     * Кнопка настроек
     */
    @AndroidFindBy(id = "bottom_bar_settings")
    public AndroidElement settingsButton;
    /**
     * Список заголовков экономического календаря
     */
    @AndroidFindBy(id = "title")
    public List<AndroidElement> titleList;

    public MainPage(AndroidDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

}
