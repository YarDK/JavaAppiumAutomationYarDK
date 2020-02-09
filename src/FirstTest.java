import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.*;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.1");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "D:\\IdeaProjects\\JavaAppiumAutomationYarDK\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    // Кейс Клик по поиску, ввод Java, проверка наличия валидного результата
    @Test
    public void firstTest()
    {
        // Ищем поле поиска по xpath и кликаем по нему
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Element can not find",
                5
        );


        // Вводим ключевое слово
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_container"),
                "Java",
                "Element can not find",
                5
        );


        // Проверяем, что результы поиска содержат ключевое слово
        waitForElementPresents(
                By.xpath("//*[@class='android.view.ViewGroup']//*[@text='Java']"),
                "Cannot find result by request 'Java'"
        );

    }

    // Кейс клик по поиску, ввод слова Java, удаление слова, возврат на главное меню
    @Test
    public void testCancelSearch()
    {
        // Ищем поле поиска по id и кликаем по нему
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Element can not find",
                5
        );

        // Ищем поле поиска по id и вводим Java
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_container"),
                "Java",
                "",
                5
        );

        // Удаляем введенное слово
        waitElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search find",
                5
        );

        // Ищем крестик назад и кликаем по нему
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Element not found",
                5
        );

        // Проверяем, что заданный элемент отсутствует на экране
        waitForElementNotPresent(
                By.xpath("org.wikipedia:id/search_close_btn"),
                "Element found",
                5
        );

    }

    // Кейс клик по поиску, ввод слова Java, клик по результату, проверка наличия искомого слова в названии статьи
    @Test
    public void testCompareArticleTitle()
    {
        // Ищем поле поиска по id и кликаем по нему
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Element can not find",
                5
        );

        // Ищем поле поиска по id и вводим Java
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_container"),
                "Java",
                "",
                5
        );

        // Клик по результату c описанием java языка
        waitForElementAndClick(
                By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout[2]/android.view.ViewGroup/android.support.v4.view.ViewPager/android.view.ViewGroup/android.widget.FrameLayout/android.support.v7.widget.RecyclerView/android.widget.FrameLayout[2]/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]"),
                "Cannot find result by request 'Java'",
                5
        );

        WebElement page_element = waitForElementPresents(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Page not found",
                15
        );

        String article_title = page_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void checkSearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Element can not find",
                5
        );
        // org.wikipedia:id/search_src_text
        WebElement page_element = waitForElementPresents(
                By.id("org.wikipedia:id/search_src_text"),
                "Page not found",
                15
        );

        String article_title = page_element.getAttribute("text");

        Assert.assertEquals(
                "Can not find 'Search'",
                "Search…",
                article_title
        );
    }

    @Test
    public void cancelSearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Element 'search_container' can not find",
                5
        );

        WebElement element_searchContainerAndInterText = waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Failed inter search request 'Java' ",
                3
        );

        String placeholderSearch = element_searchContainerAndInterText.getAttribute("text");

        Assert.assertNotEquals(
                "Search container is empty",
                "Search…",
                placeholderSearch
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Element not found",
                5
        );

        WebElement element_searchContainer =  waitForElementPresents(
                By.id("org.wikipedia:id/search_src_text"),
                "Element 'search_container' can not find",
                5
        );

        String searchContainer = element_searchContainer.getAttribute("text");
        Assert.assertEquals(
                "Search container is not empty",
                "Search…",
                searchContainer
        );
    }

    @Test
    public void testCheckingWordsInSearch()
    {
        // Клик по сроке поиска
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Element can not find",
                5
        );

        String requestWorld = "Java";

        // Ввод запроса в строку поиска
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                requestWorld,
                "Failed inter search request 'Java'",
                5
        );

        // Убираем клавиатуру
        driver.hideKeyboard();

        // Строим лист со всеми найденными результатами поиска
        ArrayList<WebElement> list = new ArrayList<>(
                driver.findElement(By.id("org.wikipedia:id/search_results_list"))
                        .findElements(By.id("org.wikipedia:id/page_list_item_title")));

        // Проверяем каждый результат поиска на наличие запрашиваемого слова, и в случаи не совпадения тест падает
        for(WebElement s : list){
            String requestResult = s.getAttribute("text").toLowerCase();
            String message_error = "Result '"+ requestResult +"' do not have request word '"+ requestWorld + "'";
            Assert.assertTrue(message_error, requestResult.contains(requestWorld.toLowerCase()));
        }

    }

    // Метод для ожидания появления элемента
    private WebElement waitForElementPresents(By by, String error_massage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_massage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    // Перегруженный метод для ожидания появления элемента, таймаут задан по умолчанию 5 секунд
    private WebElement waitForElementPresents(By by, String error_massage) {
        return waitForElementPresents(by, error_massage, 5);
    }

    // Метод для совершения клика по элементу
    private WebElement waitForElementAndClick(By by, String error_massage, long timeoutInSeconds) {
        WebElement element = waitForElementPresents(by, error_massage, timeoutInSeconds);
        element.click();
        return element;
    }

    // Метод для ввода значения в выбранный элемент
    private WebElement waitForElementAndSendKeys(By by, String value, String error_massage, long timeoutInSeconds) {
        WebElement element = waitForElementPresents(by, error_massage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    // Метод для проверки отсутствия элемента
    private boolean waitForElementNotPresent(By by, String error_massage, long timeInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
        wait.withMessage(error_massage +"\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    // Метод для удаления текста
    private WebElement waitElementAndClear(By by, String error_massage, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresents(by, error_massage, timeoutInSeconds);
        element.clear();
        return element;
    }

    // Кастомный метод скрола. Делает свайп от элемента From к элементу To
    private void customSwipe(By from, By to)
    {
        WebElement resource = driver.findElement(from);
        WebElement openid = driver.findElement(to);
        TouchAction action = new TouchAction(driver);
        action.press(resource).moveTo(openid).release();
        action.perform();
    }

}

