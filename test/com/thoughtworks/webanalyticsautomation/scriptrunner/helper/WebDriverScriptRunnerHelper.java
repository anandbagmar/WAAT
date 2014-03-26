package com.thoughtworks.webanalyticsautomation.scriptrunner.helper;

import com.thoughtworks.webanalyticsautomation.common.BROWSER;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.SkipException;

import static org.testng.Assert.assertTrue;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Jan 4, 2011
 * Time: 10:38:05 AM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

public class WebDriverScriptRunnerHelper extends ScriptRunnerHelper {

    private WebDriver driver;

    public WebDriverScriptRunnerHelper(Logger logger, BROWSER browser, String baseURL) {
        super(logger,browser, baseURL);
    }

    @Override
    public void startDriver() {
        String os = System.getProperty("os.name").toLowerCase();
        logger.info ("Starting WebDriver on OS: " + os + " for browser: " + browser.name());
        if (browser.equals(BROWSER.firefox)) {
            driver = new FirefoxDriver();
            driver.get(BASE_URL);
        }
        else if (browser.equals(BROWSER.iehta)) {
            if (!os.contains("win")) {
                throw new SkipException("Skipping this test as Internet Explorer browser is NOT available on " + os);
            }
            driver = new InternetExplorerDriver();
            driver.get(BASE_URL);
        } else if (browser.equals(BROWSER.chrome)) {
            driver = new ChromeDriver();
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
