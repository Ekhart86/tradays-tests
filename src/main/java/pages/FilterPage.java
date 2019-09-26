package pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static util.Wrapper.clickElementByText;
import static util.Wrapper.scrollToText;

/**
 * Экран выбора фильтров
 */
public class FilterPage extends BasePage {

    /**
     * Список кнопок выбрать всё
     */
    @AndroidFindBy(id = "select_all")
    public List<AndroidElement> selectAllList;
    /**
     * Кнопка возврата назад
     */
    @AndroidFindBy(className = "android.widget.ImageButton")
    public AndroidElement backButton;

    public FilterPage(AndroidDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /**
     * Выбрать фильтр
     *
     * @param filterName название требуемого фильтра
     */
    public void selectFilter(String filterName) {
        scrollToText(filterName);
        clickElementByText(filterName);
        logger.debug(filterName + " filter selected");
    }
}
