package test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import listener.ListenerTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import util.Wrapper;

@Listeners(ListenerTest.class)

@Feature("Тесты приложения Tradays")
public class TradaysTest extends BaseTest {

    @Test(description = "Фильтрация событий экономического календаря")
    @Description("Фильтрация событий экономического календаря")
    public void filteringCalendarEventsTest() {

        String importance = "Medium";
        String country = "Switzerland";

        step("Открыть приложение 'Tradays'", () -> elementShouldBeVisible(mainPage.titleList.get(0)));
        step("Перейти в раздел 'Настройки'", () -> clickElement(mainPage.settingsButton));
        step("Выбрать язык приложения 'Английский'", () -> settingsPage.selectEnglishLanguage());
        step("Перейти в фильтры событий экономического календаря", () -> clickElement(mainPage.filterButton));
        step("Снять все выбранные фильтры", () -> filterPage.selectAllList.forEach(Wrapper::clickElement));
        step("Выбрать приоритет - " + importance, () -> filterPage.selectFilter(importance));
        step("Выбрать страну - " + country, () -> filterPage.selectFilter(country));
        step("Вернуться в экономический календарь", () -> clickElement(filterPage.backButton));
        step("Открыть первое " + country + " событие в отфильтрованном списке", () -> clickElement(mainPage.titleList.get(0)));
        step("Проверить что приоритет - " + importance, () -> eventPage.checkImportance(importance));
        step("Проверить что страна - " + country, () -> eventPage.checkCountry(country));
        step("Записать в лог историю события за последние 12 месяцев", () -> eventPage.logHistory());
    }
}