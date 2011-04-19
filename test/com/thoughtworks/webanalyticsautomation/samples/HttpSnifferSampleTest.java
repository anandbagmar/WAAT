package com.thoughtworks.webanalyticsautomation.samples;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.webanalyticsautomation.Engine;
import com.thoughtworks.webanalyticsautomation.Result;
import com.thoughtworks.webanalyticsautomation.Status;
import com.thoughtworks.webanalyticsautomation.common.BROWSER;
import com.thoughtworks.webanalyticsautomation.common.TestBase;
import com.thoughtworks.webanalyticsautomation.inputdata.InputFileType;
import com.thoughtworks.webanalyticsautomation.plugins.WebAnalyticTool;
import com.thoughtworks.webanalyticsautomation.scriptrunner.helper.SeleniumScriptRunnerHelper;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static com.thoughtworks.webanalyticsautomation.Controller.getInstance;
import static com.thoughtworks.webanalyticsautomation.common.Utils.currentDirectory;
import static com.thoughtworks.webanalyticsautomation.common.Utils.fileSeparator;
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

public class HttpSnifferSampleTest extends TestBase {
    private Logger logger = Logger.getLogger(getClass());
    private Engine engine;
    private WebAnalyticTool webAnalyticTool = WebAnalyticTool.HTTP_SNIFFER;
    private InputFileType inputFileType = InputFileType.XML;
    private boolean keepLoadedFileInMemory = true;
    private String log4jPropertiesAbsoluteFilePath = currentDirectory() + fileSeparator() + "resources"  + fileSeparator() + "log4j.properties";
    private String inputDataFileName = currentDirectory() + fileSeparator() + "test"  + fileSeparator() + "sampledata"  + fileSeparator() + "TestData.xml";
    private String actionName = "OpenWAATArticleOnBlog_HttpSniffer";
    private Selenium selenium;
    private SeleniumScriptRunnerHelper seleniumScriptRunnerHelper;

    @Test
    public void captureAndVerifyDataReportedToWebAnalytics_HTTPSniffer_GoogleAnalytics_Selenium_Firefox() throws Exception {
        String baseURL = "http://essenceoftesting.blogspot.com";
        String navigateToURL = baseURL + "/2011/01/my-article-on-future-of-test-automation.html";
        String[] urlPatterns = new String[] {"GET /ps/ifr?container=friendconnect&mid=0"};
        int minimumNumberOfPackets = 1;

        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, log4jPropertiesAbsoluteFilePath);
        engine.enableWebAnalyticsTesting();

        startSeleniumDriver(BROWSER.firefox, baseURL);
        selenium.open(navigateToURL);

        Result verificationResult = engine.verifyWebAnalyticsData (inputDataFileName, actionName, urlPatterns, minimumNumberOfPackets);

        assertNotNull(verificationResult.getVerificationStatus(), "Verification status should NOT be NULL");
        assertNotNull(verificationResult.getListOfErrors(), "Failure details should NOT be NULL");
        logVerificationErrors(verificationResult);
        Assert.assertEquals(verificationResult.getVerificationStatus(), Status.PASS, "Verification status should be PASS");
        assertEquals(verificationResult.getListOfErrors().size(), 0, "Failure details should be empty");
    }

    private void startSeleniumDriver(BROWSER browser, String baseURL) {
        seleniumScriptRunnerHelper = new SeleniumScriptRunnerHelper(logger, browser, baseURL);
        seleniumScriptRunnerHelper.startDriver();
        selenium = (Selenium) seleniumScriptRunnerHelper.getDriverInstance();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        engine.disableWebAnalyticsTesting();
        seleniumScriptRunnerHelper.stopDriver();
    }    
}
