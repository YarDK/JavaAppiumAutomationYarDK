import io.appium.java_client.AppiumDriver;
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
import java.sql.SQLOutput;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "7.0");
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
        // Скип велком страницы
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'SKIP')]"),
                "Element can not find",
                5
        );


        // Ищем поле поиска по xpath и кликаем по нему
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Element can not find",
                5
        );


        // Вводим ключевое слово
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@resource-id,'org.wikipedia:id/search_src_text')]"),
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
        // Скип велком страницы
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'SKIP')]"),
                "Element can not find",
                5
        );

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

        // Ищем стрелку назад и кликаем по ней
        waitForElementAndClick(
                By.xpath("//*[contains(@class, 'android.widget.ImageButton')]"),
                "Element not found",
                5
        );

        // Проверяем, что заданный элемент отсутствует на экране
        waitForElementNotPresent(
                By.xpath("//*[contains(@class, 'android.widget.ImageButton')]"),
                "Element found",
                5
        );

    }

    // Кейс клик по поиску, ввод слова Java, клик по результату, проверка наличия искомого слова в названии статьи
    @Test
    public void testCompareArticleTitle()
    {
        // Скип велком страницы
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'SKIP')]"),
                "Element can not find",
                5
        );

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
                By.xpath("//*[@class='android.view.ViewGroup']//*[@text='Java (programming language)']"),
                "Cannot find result by request 'Java'",
                5
        );

        WebElement page_element = waitForElementPresents(
                By.id("org.wikipedia:id/page_web_view"),
                "Page not found",
                15
        );

        String article_title = page_element.getAttribute("content-desc");

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );
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

}

