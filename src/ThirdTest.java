import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class ThirdTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
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

    @Test
    public void testCompareArticleTitle() {
        // Ищем поле поиска по id и кликаем по нему
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Element 'search_container' can not find",
                5
        );

        // Ищем поле поиска по id и вводим Java
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Appium",
                "Element 'search_src_text' can not find",
                5
        );

        // Клик по результату c описанием java языка
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Appium']"),
                "Cannot find result with text 'Appium'",
                5
        );

        // Скролл страницы, пока не будет найден элемент
        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "Text 'View page in browser' not found on page.",
                20
        );
    }

    @Test
    public void saveFirstArticleToMyList()
    {
        // Ищем поле поиска по id и кликаем по нему
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Element 'search_container' can not find",
                5
        );

        // Ищем поле поиска по id и вводим Java
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Element 'search_src_text' can not find",
                5
        );

        // Клик по результату c описанием java языка
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Java (programming language)']"),
                "Cannot find result with text 'Java (programming language)'.",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Element 'More options' can not find.",
                5
        );

        waitingForElement();

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Element 'Add to reading list' can not find.",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Element 'onboarding_button 'Got it'' can not find.",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                "Learning Programming",
                "Element 'text_input' can not find.",
                1
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Element 'OK' can not find.",
                5
        );


        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Element 'Navigate up 'X'' can not find.",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Element 'My lists' can not find.",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Learning Programming']"),
                "Element 'Learning Programming' can not find.",
                5
        );

        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Element with text 'Java (programming language)' can not find."
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Element with text 'Java (programming language)' can not find.",
                5);
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
    private boolean waitForElementNotPresent(By by, String error_massage, long timeInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
        wait.withMessage(error_massage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    // Метод для удаления текста
    private WebElement waitForElementAndClear(By by, String error_massage, long timeoutInSeconds) {
        WebElement element = waitForElementPresents(by, error_massage, timeoutInSeconds);
        element.clear();
        return element;
    }

    // Метод для удаления текста
    private WebElement waitForElementAndClearAndSendKeys(By by, String value, String error_massage, long timeoutInSeconds) {
        WebElement element = waitForElementPresents(by, error_massage, timeoutInSeconds);
        element.clear();
        element.sendKeys(value);
        return element;
    }

    // Метод для скролла
    protected void swipeUp(int timeOfSwipe){
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2; // Делим ширену экрана пополам
        int start_y = (int) (size.height * 0.8); // Процентное соотношение относительно общей высоты экрана
        int end_y = (int) (size.height * 0.2);
        // Нажать в заданной координате, держать заданное время, перетащить в заданную координату, закончить действие, передать на выполнение
        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x,end_y)
                .release()
                .perform();
    }

    // Метод для быстрого скрола на основе обычного
    protected void swipeUpQuick(){
        swipeUp(200);
    }

    // Метод для скорла, пока не появится элемент на странице
    protected void swipeUpToFindElement(By by, String error_message, int max_swipes){
        int already_swipe = 0;
        while(driver.findElements(by).size() == 0){
            if (already_swipe > max_swipes){
                waitForElementPresents(by, error_message + " Swipe limit exceeded", 0);
                return;
            }
            swipeUpQuick();
            already_swipe++;
        }
    }

    protected void swipeElementToLeft(By by, String error_message){
        WebElement element = waitForElementPresents(by, error_message, 10);
        int left_x = element.getLocation().getX(); // Левая сторона найденного элемента. Берем нулевое значение по оси Х
        int right_x = left_x + element.getSize().getWidth(); // Правая сторона элемента
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(150)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    protected void waitingForElement(){
        try{
            Thread.sleep(2000);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
