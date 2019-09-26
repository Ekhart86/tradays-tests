package pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static util.Wrapper.*;

/**
 * Экран открытого события
 */
public class EventPage extends BasePage {

    /**
     * Заголовок события
     */
    @AndroidFindBy(id = "title")
    public AndroidElement header;
    /**
     * Название страны
     */
    @AndroidFindBy(id = "country_name")
    public AndroidElement countryName;
    /**
     * Источник
     */
    @AndroidFindBy(id = "event_source")
    public AndroidElement eventSource;
    /**
     * Сектор
     */
    @AndroidFindBy(id = "event_sector")
    public AndroidElement eventSector;
    /**
     * Важность
     */
    @AndroidFindBy(id = "event_importance")
    public AndroidElement eventImportance;
    /**
     * Вкладка история
     */
    @AndroidFindBy(id = "tab_history")
    public AndroidElement historyTabButton;
    /**
     * Список отображаемых дат
     */
    @AndroidFindBy(id = "time")
    public List<AndroidElement> dateList;
    /**
     * Список отображаемых актуальных значений
     */
    @AndroidFindBy(id = "actual")
    public List<AndroidElement> actualList;
    /**
     * Список отображаемых прогнозируемых значений
     */
    @AndroidFindBy(id = "forecast")
    public List<AndroidElement> forecastList;
    /**
     * Список отображаемых предыдущих значений
     */
    @AndroidFindBy(id = "previous")
    public List<AndroidElement> previousList;


    public EventPage(AndroidDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /**
     * Проверить приоритет в открытом событии
     *
     * @param expectedImportance ожидаемый приоритет
     */
    public void checkImportance(String expectedImportance) {
        assertEquals(eventImportance.getText().toLowerCase(), expectedImportance.toLowerCase(), "Приоритет не соответствует ожидаемому!");
        logger.debug("Importance is " + expectedImportance);
    }

    /**
     * Проверить страну в открытом событии
     *
     * @param expectedCountry ожидаемая страна
     */
    public void checkCountry(String expectedCountry) {
        assertEquals(countryName.getText().toLowerCase(), expectedCountry.toLowerCase(), "Страна не соответствует ожидаемой!");
        logger.debug("Country is " + expectedCountry);
    }

    /**
     * Записать историю события за последние 12 месяцев если она есть
     */
    public void logHistory() {
        if (existsElement("tab_history")) {
            clickElement(historyTabButton);
            LocalDate endDate = getDate(dateList.get(2).getText()).minusYears(1);
            logger.info("Date one year earlier : " + endDate);
            logger.info("Recording history for the year:");
            logger.info(getHistoryTable(endDate));
        } else {
            logger.info("For an event" + header.getText() + " no story!");
        }
    }

    /**
     * Метод формирует таблицу с историей для записи в лог
     *
     * @param endDate дата на которой нужно прекратить формировать таблицу
     */
    private String getHistoryTable(LocalDate endDate) {
        String separator = " | ";
        StringBuilder builder = new StringBuilder();
        builder.append("\nDate | Actual | Forecast | Previous\n");
        long endTime = System.currentTimeMillis() + 120000;
        loop:
        while (System.currentTimeMillis() < endTime) {
            verticalSwipeByPercentages(0.9, 0.65, 0.5);
            for (int i = 0; i < dateList.size(); i++) {
                LocalDate localTempDate;
                String strDate = dateList.get(i).getText();
                try {
                    localTempDate = getDate(strDate);
                } catch (Exception e) {
                    logger.debug("Caught text, skip it.");
                    continue;
                }
                if (builder.toString().contains(strDate)) {
                    logger.debug("This date already exists, skip it");
                    continue;
                }
                if (localTempDate.isBefore(endDate)) {
                    logger.debug("Date older than a year, stop recording history");
                    break loop;
                }
                logger.debug("Write the date: " + dateList.get(i).getText());
                builder.append(strDate);
                builder.append(separator);
                builder.append(actualList.get(i).getText());
                builder.append(separator);
                builder.append(forecastList.get(i).getText());
                builder.append(separator);
                builder.append(previousList.get(i).getText());
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    private LocalDate getDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy").withLocale(Locale.ENGLISH);
        return LocalDate.parse(date.replaceAll(" ", "-"), formatter);
    }

}
