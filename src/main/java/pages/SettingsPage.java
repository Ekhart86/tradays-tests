package pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.Wrapper.clickElementByText;
import static util.Wrapper.waitElement;

/**
 * Экран настроек приложения
 */
public class SettingsPage extends BasePage {

    private Map<String, String> map = createMap();
    /**
     * Список чекбоксов с языками
     */
    @AndroidFindBy(className = "android.widget.CheckedTextView")
    public List<AndroidElement> languageList;
    /**
     * Элемент с текущим выбранным языком в разделе настроек
     */
    @AndroidFindBy(xpath = "(//android.widget.TextView[@resource-id ='net.metaquotes.economiccalendar:id/hint'])[2]")
    public AndroidElement currentLanguage;

    public SettingsPage(AndroidDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /**
     * Выбрать английский язык, вне зависимости от того какой язык установлен сейчас
     */
    public void selectEnglishLanguage() {
        clickElementByText(currentLanguage.getText());
        waitElement(languageList.get(0), 10);
        for (AndroidElement element : languageList) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (element.getText().equals(entry.getValue())) {
                    logger.info("Choose : " + entry.getValue());
                    clickElementByText(entry.getValue());
                    return;
                }
            }
        }
    }

    private static Map<String, String> createMap() {
        Map<String, String> map = new HashMap<>();
        map.put("Арабский", "الإنجليزية");
        map.put("Китайский", "英语");
        map.put("Немецкий", "Englisch");
        map.put("Японский", "英語");
        map.put("Португальский", "Inglês");
        map.put("Русский", "Английский");
        map.put("Турецкий", "İngilizce");
        map.put("Испанский", "Ingles");
        map.put("Английский", "English");
        return map;
    }
}

