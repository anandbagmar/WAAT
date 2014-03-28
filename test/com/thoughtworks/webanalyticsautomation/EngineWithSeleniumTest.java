package com.thoughtworks.webanalyticsautomation;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.webanalyticsautomation.common.BROWSER;
import com.thoughtworks.webanalyticsautomation.common.TestBase;
import com.thoughtworks.webanalyticsautomation.plugins.WebAnalyticTool;
import com.thoughtworks.webanalyticsautomation.scriptrunner.SeleniumScriptRunner;
import com.thoughtworks.webanalyticsautomation.scriptrunner.helper.SeleniumScriptRunnerHelper;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.thoughtworks.webanalyticsautomation.Controller.getInstance;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Test (singleThreaded = true)
public class EngineWithSeleniumTest extends TestBase {
    private SeleniumScriptRunnerHelper seleniumScriptRunnerHelper;
    private Selenium selenium;
    private String actionName;

    @BeforeMethod
    public void setup () {
        Controller.reset();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        if (engine!=null) {
            engine.disableWebAnalyticsTesting();
        }
        if (seleniumScriptRunnerHelper!=null) {
            seleniumScriptRunnerHelper.stopDriver();
        }
    }

    @Test
    public void captureAndVerifyDataReportedToWebAnalytics_HTTPSniffer_Omniture_Selenium_IE() throws Exception {
        captureAndVerifyDataReportedToWebAnalytics_HttpSniffer_Omniture(BROWSER.iehta);
    }

    @Test
    public void captureAndVerifyDataReportedToWebAnalytics_HTTPSniffer_Omniture_Selenium_Firefox() throws Exception {
        captureAndVerifyDataReportedToWebAnalytics_HttpSniffer_Omniture(BROWSER.firefox);
    }

    @Test
    public void captureAndVerifyDataReportedToWebAnalytics_HTTPSniffer_GoogleAnalytics_Selenium_IE() throws Exception {
        captureAndVerifyDataReportedToWebAnalytics_HttpSniffer_GoogleAnalytics(BROWSER.iehta);
    }

    @Test
    public void captureAndVerifyDataReportedToWebAnalytics_HTTPSniffer_GoogleAnalytics_Selenium_Firefox() throws Exception {
        captureAndVerifyDataReportedToWebAnalytics_HttpSniffer_GoogleAnalytics(BROWSER.firefox);
    }

    private void captureAndVerifyDataReportedToWebAnalytics_HttpSniffer_GoogleAnalytics(BROWSER browser) {
        String baseURL = "http://essenceoftesting.blogspot.com";
        String navigateToURL = baseURL + "/2010/12/waat-web-analytics-automation-testing.html";
        String[] urlPatterns = new String[] {"GET /ps/ifr?container=friendconnect&mid=0"};

        int minimumNumberOfPackets = 1;
        actionName = "OpenWAATArticleOnBlog_HttpSniffer";

        captureAndVerifyDataReportedToWebAnalytics_HttpSniffer(browser, baseURL, navigateToURL, actionName, urlPatterns, minimumNumberOfPackets);
    }

    private void captureAndVerifyDataReportedToWebAnalytics_HttpSniffer_Omniture(BROWSER browser) {
        String baseURL = "http://digg.com";
        String navigateToURL = baseURL + "/upcoming";
        String[] urlPatterns = new String[] {"GET /b/ss/diggcomv4"};

        int minimumNumberOfPackets = 1;
        actionName = "OpenUpcomingPage_HttpSniffer";

        captureAndVerifyDataReportedToWebAnalytics_HttpSniffer(browser, baseURL, navigateToURL, actionName, urlPatterns, minimumNumberOfPackets);
    }

    private void captureAndVerifyDataReportedToWebAnalytics_HttpSniffer(BROWSER browser, String baseURL, String navigateToURL, String actionName, String[] urlPatterns, int minimumNumberOfPackets) {
        webAnalyticTool = WebAnalyticTool.HTTP_SNIFFER;

        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, log4jPropertiesAbsoluteFilePath);
        engine.enableWebAnalyticsTesting();

        startSeleniumDriver(browser, baseURL);
        selenium.open(navigateToURL);

        Result verificationResult = engine.verifyWebAnalyticsData (inputDataFileName, actionName, urlPatterns, minimumNumberOfPackets);

        assertNotNull(verificationResult.getVerificationStatus(), "Verification status should NOT be NULL");
        assertNotNull(verificationResult.getListOfErrors(), "Failure details should NOT be NULL");
        logVerificationErrors(verificationResult);
        assertEquals(verificationResult.getVerificationStatus(), Status.PASS, "Verification status should be PASS");
        assertEquals(verificationResult.getListOfErrors().size(), 0, "Failure details should be empty");
    }

    private void startSeleniumDriver(BROWSER browser, String baseURL) {
        seleniumScriptRunnerHelper = new SeleniumScriptRunnerHelper(logger, browser, baseURL);
        seleniumScriptRunnerHelper.startDriver();
        selenium = (Selenium) seleniumScriptRunnerHelper.getDriverInstance();
    }
}