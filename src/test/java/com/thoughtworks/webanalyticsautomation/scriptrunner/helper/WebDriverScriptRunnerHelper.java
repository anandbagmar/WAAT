package com.thoughtworks.webanalyticsautomation.scriptrunner.helper;

import com.thoughtworks.webanalyticsautomation.common.BROWSER;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.SkipException;

import java.util.logging.Level;

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
        startDriverUsingProxy(null);
    }

    private void instantiateChromeDriver(Proxy proxy) {
        System.setProperty("webdriver.chrome.logfile", "out/chromedriver.log");
        System.setProperty("webdriver.chrome.verboseLogging", "true");
        WebDriverManager.chromedriver().setup();
        System.setProperty("webdriver.chrome.driver",WebDriverManager.chromedriver().getBinaryPath());

        ChromeOptions chromeOptions = new ChromeOptions();
        if (null != proxy) {
            chromeOptions.setCapability(CapabilityType.PROXY, proxy);
        }
        driver = new ChromeDriver(chromeOptions);
        driver.get(BASE_URL);
    }

    private void instantiateIEDriver(String os, Proxy proxy) {
        WebDriverManager.iedriver().setup();
        if (!os.contains("win")) {
            throw new SkipException("Skipping this test as Internet Explorer browser is NOT available on " + os);
        }
        InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
        if (null != proxy) {
            internetExplorerOptions.setProxy(proxy);
        }
        driver = new InternetExplorerDriver(internetExplorerOptions);
        driver.get(BASE_URL);
    }

    private void instantiateEdgeDriver(String os, Proxy proxy) {
        WebDriverManager.edgedriver().setup();
        if (!os.contains("win")) {
            throw new SkipException("Skipping this test as Internet Explorer browser is NOT available on " + os);
        }
        EdgeOptions edgeOptions = new EdgeOptions();
        if (null != proxy) {
            edgeOptions.setProxy(proxy);
        }
        driver = new EdgeDriver(edgeOptions);
        driver.get(BASE_URL);
    }

    private void instantiateFireFoxDriver(Proxy proxy) {
        WebDriverManager.firefoxdriver().setup();
        System.setProperty("webdriver.gecko.driver",WebDriverManager.firefoxdriver().getBinaryPath());
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        if (null != proxy) {
//            firefoxOptions.setCapability(CapabilityType.PROXY, proxy);
        }

        driver = new FirefoxDriver(firefoxOptions);
        driver.get(BASE_URL);
    }

    @Override
    public void startDriverUsingProxy(Proxy proxy) {
        String os = System.getProperty("os.name").toLowerCase();
        logger.info ("Starting WebDriver on OS: " + os + " for browser: " + browser.name());
        if (browser.equals(BROWSER.firefox)) {
            instantiateFireFoxDriver(proxy);
        }
        else if (browser.equals(BROWSER.edge)) {
            instantiateEdgeDriver(os, proxy);
        }
        else if (browser.equals(BROWSER.iehta)) {
            instantiateIEDriver(os, proxy);
        } else if (browser.equals(BROWSER.chrome)) {
            instantiateChromeDriver(proxy);
        }
        logger.info ("Driver started: " + browser.name());
        logger.info ("Page title: " + driver.getTitle());
    }

    @Override
    public void stopDriver() {
        if (null != this.driver) {
            driver.close();
        }
        driver.quit();
    }

    @Override
    public Object getDriverInstance() {
        if (null == driver) {
            logger.info("Driver is null");
        }
        return driver;
    }
}
