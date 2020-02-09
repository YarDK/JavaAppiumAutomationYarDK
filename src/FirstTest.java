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

    /*
    Ex4*: Тест: проверка слов в поиске
    Написать тест, который:
    -Ищет какое-то слово
    -Убеждается, что в каждом результате поиска есть это слово
    */

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

