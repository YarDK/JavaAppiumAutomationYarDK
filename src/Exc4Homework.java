import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;


public class Exc4Homework {
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
        //capabilities.setCapability("app", "C:\\Users\\yako\\IdeaProjects\\JavaAppiumAutomationYarDK\\apks\\org.wikipedia.apk");
        capabilities.setCapability("app", "D:\\IdeaProjects\\JavaAppiumAutomationYarDK\\apks\\org.wikipedia.apk");


        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.rotate(ScreenOrientation.PORTRAIT);

    }

    @After
    public void tearDown() {
        driver.quit();
    }

    /*
    Как нам сделать так, чтобы после теста на поворот экрана сам экран всегда оказывался в правильном положении,
    даже если тест упал в тот момент, когда экран был наклонен?
    */

    @Test
    public void testOne()
    {

        //Нажать на поиск
        String input_field_search_wikipedia_by_id = "org.wikipedia:id/search_container";
        waitForElementAndClick(
                By.id(input_field_search_wikipedia_by_id),
                "Element 'input_field_search_wikipedia_by_id' can not find",
                5
        );

        //Ввести Java
        String input_field_search_by_id = "org.wikipedia:id/search_src_text";
        waitForElementAndSendKeys(
                By.id(input_field_search_by_id),
                "Java",
                "Element 'input_field_search' can not find",
                5
        );
        //throw new AssertionError("Throw Assertion with rotation");
    }

    @Test
    public void testTwo()
    {
        //Нажать на поиск
        String input_field_search_wikipedia_by_id = "org.wikipedia:id/search_container";
        waitForElementAndClick(
                By.id(input_field_search_wikipedia_by_id),
                "Element 'input_field_search_wikipedia_by_id' can not find",
                5
        );

        //Ввести Java
        String input_field_search_by_id = "org.wikipedia:id/search_src_text";
        waitForElementAndSendKeys(
                By.id(input_field_search_by_id),
                "Python",
                "Element 'input_field_search' can not find",
                5
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
    private WebElement  waitForElementAndSendKeys(By by, String value, String error_massage, long timeoutInSeconds) {
        waitForElementPresents(by,error_massage,timeoutInSeconds);
        MobileElement element = (MobileElement) driver.findElement(by);
        element.setValue(value);
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
                .waitAction(300)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    protected void waitingForElement(int timeForWaiting){
        try{
            Thread.sleep(timeForWaiting);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    protected void assertElementPresent(By by){
        try {
            driver.findElement(by);
        } catch (NoSuchElementException e){
            throw new AssertionError("Element '" + by.toString() + "' not found");
        }
    }

}
