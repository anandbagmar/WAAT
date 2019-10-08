package com.anandbagmar.webanalyticsautomation.samples;

import com.anandbagmar.webanalyticsautomation.Controller;
import com.anandbagmar.webanalyticsautomation.Engine;
import com.anandbagmar.webanalyticsautomation.Result;
import com.anandbagmar.webanalyticsautomation.Status;
import com.anandbagmar.webanalyticsautomation.common.BROWSER;
import com.anandbagmar.webanalyticsautomation.common.Utils;
import com.anandbagmar.webanalyticsautomation.common.TestBase;
import com.anandbagmar.webanalyticsautomation.inputdata.InputFileType;
import com.anandbagmar.webanalyticsautomation.plugins.WebAnalyticTool;
import com.anandbagmar.webanalyticsautomation.scriptrunner.helper.WebDriverScriptRunnerHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;


/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Feb 2, 2011
 * Time: 5:17:08 PM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

public class ProxySampleTest extends TestBase {
    private Logger logger = Logger.getLogger(getClass());
    private Engine engine;
    private WebAnalyticTool webAnalyticTool = WebAnalyticTool.PROXY;
    private InputFileType inputFileType = InputFileType.XML;
    private boolean keepLoadedFileInMemory = true;
    private String log4jPropertiesAbsoluteFilePath = Utils.getAbsolutePath(new String[] {"resources","log4j.properties"});
    private String inputDataFileName = Utils.getAbsolutePath(new String[] {"src", "test", "sampledata", "TestData.xml"});
    private String actionName = "OpenWAATArticleOnBlog_Proxy";
    private WebDriverScriptRunnerHelper webDriverScriptRunnerHelper;
    private WebDriver driverInstance;

    @Test
    public void captureAndVerifyDataReportedToWebAnalytics_Proxy_GoogleAnalytics_WebDriver_Firefox() throws
            Exception {
        String baseURL = "http://essenceoftesting.blogspot.com";
        String navigateToURL = baseURL + "/search/label/waat";
        ArrayList<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("https://ssl.google-analytics.com/");
        urlPatterns.add("__/utm.gif");
        int minimumNumberOfPackets = 1;

        engine = Controller.getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, log4jPropertiesAbsoluteFilePath);
        Proxy seProxy = (Proxy) engine.getSeleniumBasedProxyPlugin();

        startWebDriver(BROWSER.firefox, baseURL, seProxy);
        logger.info("Start capture");
        engine.enableWebAnalyticsTesting(actionName);
        logger.info("Do action");
        driverInstance.get(navigateToURL);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) { }

        logger.info("Verify result");
        Result verificationResult = engine.verifyWebAnalyticsData(inputDataFileName, actionName, urlPatterns, minimumNumberOfPackets);

        assertNotNull(verificationResult.getVerificationStatus(), "Verification status should NOT be NULL");
        assertNotNull(verificationResult.getListOfErrors(), "Failure details should NOT be NULL");
        logVerificationErrors(verificationResult);
        Assert.assertEquals(verificationResult.getVerificationStatus(), Status.PASS, "Verification status should be PASS");
        assertEquals(verificationResult.getListOfErrors().size(), 0, "Failure details should be empty");
    }

    @Test
    public void captureAndVerifyDataReportedToWebAnalytics_Proxy_GoogleAnalytics_WebDriver_Chrome() throws
            Exception {
        String baseURL = "http://essenceoftesting.blogspot.com";
        String navigateToURL = baseURL + "/search/label/waat";
        ArrayList<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("https://ssl.google-analytics.com/");
        urlPatterns.add("__/utm.gif");
        int minimumNumberOfPackets = 1;

        engine = Controller.getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, log4jPropertiesAbsoluteFilePath);
        Proxy seProxy = (Proxy) engine.getSeleniumBasedProxyPlugin();

        startWebDriver(BROWSER.chrome, baseURL, seProxy);
        logger.info("Start capture");
        engine.enableWebAnalyticsTesting(actionName);
        logger.info("Do action");
        driverInstance.get(navigateToURL);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) { }

        logger.info("Verify result");
        Result verificationResult = engine.verifyWebAnalyticsData(inputDataFileName, actionName, urlPatterns, minimumNumberOfPackets);

        assertNotNull(verificationResult.getVerificationStatus(), "Verification status should NOT be NULL");
        assertNotNull(verificationResult.getListOfErrors(), "Failure details should NOT be NULL");
        logVerificationErrors(verificationResult);
        assertEquals(verificationResult.getVerificationStatus(), Status.PASS, "Verification status should be PASS");
        assertEquals(verificationResult.getListOfErrors().size(), 0, "Failure details should be empty");
    }

    private void startWebDriver(BROWSER browser, String baseURL, Proxy seProxy) {
        webDriverScriptRunnerHelper = new WebDriverScriptRunnerHelper(logger, browser, baseURL);
        webDriverScriptRunnerHelper.startDriverUsingProxy(seProxy);
        driverInstance = (WebDriver) webDriverScriptRunnerHelper.getDriverInstance();
    }

    @BeforeMethod
    public void setup () {
        Controller.reset();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        if (engine!=null) {
            engine.disableWebAnalyticsTesting();
        }
        if (webDriverScriptRunnerHelper!=null) {
            webDriverScriptRunnerHelper.stopDriver();
        }
    }
}
