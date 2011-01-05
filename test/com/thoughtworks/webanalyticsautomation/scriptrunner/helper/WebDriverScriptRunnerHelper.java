package com.thoughtworks.webanalyticsautomation.scriptrunner.helper;

import com.thoughtworks.webanalyticsautomation.utils.BROWSER;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Jan 4, 2011
 * Time: 10:38:05 AM
 */

public class WebDriverScriptRunnerHelper extends ScriptRunnerHelper {

    WebDriver driver;

    public WebDriverScriptRunnerHelper(Logger logger, BROWSER browser) {
        super(logger,browser);
    }

    @Override
    public void startDriver() {
        logger.info ("Starting WebDriver for browser: " + browser.name());
        if (browser.equals(BROWSER.firefox)) {
            driver = new FirefoxDriver();
            driver.get(BASE_URL);
        }
        else if (browser.equals(BROWSER.iehta)) {
            driver = new InternetExplorerDriver();
            driver.get(BASE_URL);
        }
        logger.info ("Driver started: " + browser.name());
        logger.info ("Page title: " + driver.getTitle());
    }

    @Override
    public void stopDriver() {
        if (null != this.driver) {
            driver.close();
            driver.quit();
        }
    }

    @Override
    public Object getDriverInstance() {
        if (null == driver) {
            logger.info("Driver is null");
        }
        return driver;
    }
}
