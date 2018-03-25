package test.selenium;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Pages {



    public static class Browser {

        private static final transient Log LOG = LogFactory.getLog(Browser.class.getName());

        static final String localHmsPathBase = "http://127.0.0.1:8084/console";

        static WebDriver driver = WebDriverInstance.getWebDriverInstance().getDriver();

        static String nameField = "name";

        static String submitButton = "//div/div/form/button";

        static void goTo(String url) {

            WebDriverInstance.getWebDriverInstance().getDriver().get(url);
        }

        public static String getUrl() {

            return driver.getCurrentUrl();
        }

        static void sleepThreeSeconds() {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        static void loginAsAdmin() {

            String username = "abc";
            String password = "abc";

            WebElement userNameField =
                (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
            userNameField.sendKeys(username);
            driver.findElement(By.name("password")).sendKeys(password);
            driver.findElement(By.name("signin")).submit();
            sleepThreeSeconds();

        }

        static void loginAsQA() {

            String username = "QA";
            String password = "QA";
            WebElement userNameField =
                (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
            userNameField.sendKeys(username);
            driver.findElement(By.name("password")).sendKeys(password);
            driver.findElement(By.name("signin")).submit();
        }

        static void loginAsDEV() {

            String username = "DEV";
            String password = "DEV";
            WebElement userNameField =
                (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
            userNameField.sendKeys(username);
            driver.findElement(By.name("password")).sendKeys(password);
            driver.findElement(By.name("signin")).submit();
        }

        static String getTitle() {

            return driver.getTitle();
        }

        static void takeSnapshot() {

            Long idForSnapshot = createUniqueConfNumberFromCurrentTime();

            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            try {
                FileUtils.copyFile(scrFile, new File("./target/screenshots/screenshot-" + idForSnapshot + ".png"));

                LOG.info("taking snapshot with id: " + idForSnapshot);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        static Long createUniqueConfNumberFromCurrentTime() {

            DateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");
            Date date = new Date();
            String todayDate = dateFormat.format(date);
            return Long.valueOf(todayDate);
        }

        public static void enterName(String attributeName) {

            WebElement enterName =
                (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.name(nameField)));
            enterName.sendKeys(attributeName);

        }


        public static void clickSubmit() {

            driver.findElement(By.xpath(submitButton)).click();
        }

        public static boolean findLogoutElement() {

            List<WebElement> logoutLink = driver.findElements(By.partialLinkText("Logout"));
            if (logoutLink.size() > 0) {
                findLogoutElementAndClick();
                LOG.debug(" had to click on Logout link before trying to login ");
                return true;
            }
            return false;
        }

        public static void findLogoutElementAndClick() {

            driver.findElement(By.partialLinkText("Logout")).click();
        }


        public static void logoutAsAdmin() {

            String logoutLink = "Logout (abc)";
            WebElement logout =
                (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.linkText(logoutLink)));
            logout.click();
        }

        public static void quit() {

            driver.quit();
        }

        public static void popupWindowClickOKToDelete() {

            Alert alert = driver.switchTo().alert();
            alert.accept();
        }

        public static String readAlertMessage() {

            WebElement alertMessage =
                (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert")));
            return alertMessage.getText();
        }

        public static void clickCreateNewLink() {

            WebElement createNewLink =
                (new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Create New")));
            sleepOneSecond();
            createNewLink.click();
        }

        public static void sleepOneSecond() {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void pageDown() {

            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollBy(0,250)", "");
        }

    }


    public static class HomePage {

        private static final transient Log LOG = LogFactory.getLog(HomePage.class.getName());

        private static final String localHmsPath = Pages.Browser.localHmsPathBase + "/login";

        public static final String pageTitle = "HMS: Global Prefs";

        static WebDriver driver = WebDriverInstance.getWebDriverInstance().getDriver();

        public static void goTo() {

            Pages.Browser.goTo(localHmsPath);
        }

        public static boolean isAt() {

            LOG.info(" isAt() method: title was:" + Pages.Browser.getTitle());
            //System.out.println(Pages.Browser.getTitle());
            return Pages.Browser.getTitle().equals(pageTitle);
        }

        public static String getUrl() {

            return driver.getCurrentUrl();
        }

        public static void goToAsAdmin() {

            Pages.Browser.goTo(localHmsPath);
            LOG.info(" localHmsPath was " + localHmsPath);
            driver.manage().window().maximize();
            Pages.Browser.loginAsAdmin();
        }

        public static void goToAsQA() {

            Pages.Browser.goTo(localHmsPath);
            Pages.Browser.loginAsQA();
        }

        public static void goToAsDEV() {

            Pages.Browser.goTo(localHmsPath);
            Pages.Browser.loginAsDEV();
        }

    }


    public static class ContextualImagesAdminAssetsPage {

        // private static final LOG LOG = LogManager.getLOG(ContextualImagesAdminAssetsPage.class);
        private static final transient Log LOG = LogFactory.getLog(ContextualImagesAdminAssetsPage.class);

        private static final String localHmsPath = Pages.Browser.localHmsPathBase + "/asset/list";

        public static final String pageTitle = "HMS: Assets";

        static WebDriver driver = WebDriverInstance.getWebDriverInstance().getDriver();

        public static void goTo() {

            Pages.Browser.goTo(localHmsPath);
        }

        public static boolean isAt() {

            return Browser.getTitle().equals(pageTitle);
        }

    }

}
