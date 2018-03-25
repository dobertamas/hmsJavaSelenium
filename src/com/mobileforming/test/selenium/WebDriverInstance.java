package test.selenium;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;

class WebDriverInstance {

    private EventFiringWebDriver eventFiringWD;

    private static WebDriverInstance webDriverInstance = new WebDriverInstance();

    private WebDriverInstance() {

        System.setProperty("webdriver.gecko.driver", "/opt/geckodriver");
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "C:\\temp\\logs.txt");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addPreference("marionette", true);
        firefoxOptions.addPreference("firefox_binary", "/usr/local/bin/firefox");
        firefoxOptions.addPreference("acceptInsecureCerts", true);
        WebDriver driver = new FirefoxDriver(firefoxOptions);
        eventFiringWD = new EventFiringWebDriver(driver);
        MyEventListener myEventListener = new MyEventListener();
        eventFiringWD.register(myEventListener);
    }

    static WebDriverInstance getWebDriverInstance() {

        return webDriverInstance;
    }

    WebDriver getDriver() {

        return eventFiringWD;
    }
}


class MyEventListener extends AbstractWebDriverEventListener {

    private static final Logger logger = LogManager.getLogger(MyEventListener.class);

    @Override
    public void onException(Throwable arg0, WebDriver arg1) {

        logger.debug(" Taking a snapshot after an exception occurred ");
        Pages.Browser.takeSnapshot();

    }

    @Override
    public void afterNavigateTo(java.lang.String url, WebDriver driver) {

        logger.debug(" navigated to: " + driver.getCurrentUrl());
    }

}
