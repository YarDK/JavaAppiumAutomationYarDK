import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
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

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class Exc4Homework {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        //String path_to_apk = new File("org.wikipedia.apk").getAbsolutePath();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.1");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\Users\\yako\\IdeaProjects\\JavaAppiumAutomationYarDK\\apks\\org.wikipedia.apk");
        //capabilities.setCapability("app", "D:\\IdeaProjects\\JavaAppiumAutomationYarDK\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    /*
    Написать тест, который:
    1. Сохраняет две статьи в одну папку
    2. Удаляет одну из статей
    3. Убеждается, что вторая осталась
    4. Переходит в неё и убеждается, что title совпадает
    */

    @Test
    public void testSavingTwoArticles()
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

        //Открыть статью, где описывается, что это язык программирования
        String article_about_java_programming_language = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Java (programming language)']";
        waitForElementAndClick(
                By.xpath(article_about_java_programming_language),
                "Cannot find result with text 'Java (programming language)'.",
                5
        );

        //Сохранить в переменную название статьи и сабтитл
        String title_of_article_java_by_id = "org.wikipedia:id/view_page_title_text";
        String subtitle_of_article_java_by_id = "org.wikipedia:id/view_page_subtitle_text";

        waitForElementPresents(
                By.id(title_of_article_java_by_id),
                "Element 'title_of_article_java_by_id' can not find",
                10);

        String title_of_article_java = driver.findElement(By.id(title_of_article_java_by_id)).getAttribute("text");
        String subtitle_of_article_java = driver.findElement(By.id(subtitle_of_article_java_by_id)).getAttribute("text");

        //Нажать меню
        String more_options_button_by_xpath = "//android.support.v7.widget.LinearLayoutCompat//android.widget.ImageView[@content-desc='More options']";
        waitForElementAndClick(
                By.xpath(more_options_button_by_xpath),
                "Button 'More options' can not find.",
                5
        );

        //Нажать сохранить статью
        waitingForElement(3000);
        String function_add_to_reading_list_by_xpath = "//*[@text='Add to reading list']";
        waitForElementAndClick(
                By.xpath(function_add_to_reading_list_by_xpath),
                "Element 'Add to reading list' can not find.",
                5
        );
        //Прокликать ОК
        String onboarding_button__got_it_by_id = "org.wikipedia:id/onboarding_button";
        waitForElementAndClick(
                By.id(onboarding_button__got_it_by_id),
                "Element 'onboarding_button 'Got it'' can not find.",
                5
        );

        //Нужно создать папку, но в кейсе и в реале старт этой фичи отличается, нужно разобраться
        //Сохдать переменную с названием папки
        //Ввести название папки используя переменную
        String text_input_field_by_id = "org.wikipedia:id/text_input";
        String name_folder = "Learning Programming";
        waitForElementAndClear(
                By.id(text_input_field_by_id),
                "Element 'text_input_field' can not find.",
                5
        );
        waitForElementAndSendKeys(
                By.id(text_input_field_by_id),
                name_folder,
                "Element 'text_input_field' can not find.",
                3
        );

        //Подтвердить создание
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Element 'OK' can not find.",
                5
        );

        //Закрыть статью
        String button_navigation_cross_by_xpath = "//android.widget.ImageButton[@content-desc='Navigate up']";
        waitForElementAndClick(
                By.xpath(button_navigation_cross_by_xpath),
                "Element 'Navigate up 'X'' can not find.",
                5
        );

        //Нажать на поиск
        waitForElementAndClick(
                By.id(input_field_search_wikipedia_by_id),
                "Element 'input_field_search_wikipedia_by_id' can not find",
                5
        );

        //Ввести Python
        waitForElementAndSendKeys(
                By.id(input_field_search_by_id),
                "Python",
                "Element 'input_field_search' can not find",
                5
        );

        //Открыть статью, где описывается, что это язык программирования

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Python (programming language)']"),
                "Cannot find result with text 'Java (programming language)'.",
                5
        );

        //Сохранить в переменную название статьи и сабтитл
        String title_of_article_python_by_id = "org.wikipedia:id/view_page_title_text";
        String subtitle_of_article_python_by_id = "org.wikipedia:id/view_page_subtitle_text";

        waitForElementPresents(
                By.id(title_of_article_python_by_id),
                "Element 'title_of_article_python_by_id' can not find",
                5);

        String title_of_article_python = driver.findElement(By.id(title_of_article_python_by_id)).getAttribute("text");

        //Нажать меню
        waitForElementAndClick(
                By.xpath(more_options_button_by_xpath),
                "Button 'More options' can not find.",
                5
        );

        //Нажать сохранить статью
        waitingForElement(3000);
        waitForElementAndClick(
                By.xpath(function_add_to_reading_list_by_xpath),
                "Element 'Add to reading list' can not find.",
                5
        );

        //Выбрать папку, ранее созданную
        String folder_with_articles_by_xpath = "//*[@resource-id='org.wikipedia:id/item_container']//*[@text='" + name_folder + "']";
        waitForElementAndClick(
                By.xpath(folder_with_articles_by_xpath),
                "Folder or element '"+ name_folder +"' can not find.",
                5
        );

        //Закрыть статью
        waitForElementAndClick(
                By.xpath(button_navigation_cross_by_xpath),
                "Element 'Navigate up 'X'' can not find.",
                5
        );

        //Открыть окно Мой лист
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Element 'My lists' can not find.",
                5
        );

        //Открыть папку, ранее созданную
        waitingForElement(3000);
        waitForElementAndClick(
                By.xpath("//*[@text='" + name_folder + "']"),
                "Folder 'Learning Programming' can not find.",
                5
        );

        //Дождаться загрузку элементов
        waitForElementPresents(
                By.xpath("//*[@text='" + name_folder + "']"),
                "Name folder 'Learning Programming' can not find."
        );

        //Удалить статью, который про Python
        swipeElementToLeft(
                By.xpath("//*[@text='" + title_of_article_python + "']"),
                "Element with text '" + title_of_article_python + "' can not find."
        );
        //Проверить, что вторая статья осталась
        waitForElementPresents(
                By.xpath("//*[@text='" + title_of_article_java + "']"),
                "Element with text '" + title_of_article_java + "' can not find."
        );
        //Открыть ее
        waitForElementAndClick(
                By.xpath(article_about_java_programming_language),
                "Cannot find article with text 'Java (programming language)'.",
                5
        );
        //Проверить, что эта статья про JAva
        WebElement title_element = waitForElementPresents(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Element 'view_page_title_text' not found",
                5
        );
        Assert.assertEquals(
                "Article is not about 'Java (programming language)'",
                title_element.getAttribute("text"),
                "Java (programming language)"
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

}
