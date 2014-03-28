package com.thoughtworks.webanalyticsautomation.samples;

import com.thoughtworks.webanalyticsautomation.Engine;
import com.thoughtworks.webanalyticsautomation.Result;
import com.thoughtworks.webanalyticsautomation.Status;
import com.thoughtworks.webanalyticsautomation.common.BROWSER;
import com.thoughtworks.webanalyticsautomation.common.TestBase;
import com.thoughtworks.webanalyticsautomation.common.Utils;
import com.thoughtworks.webanalyticsautomation.inputdata.InputFileType;
import com.thoughtworks.webanalyticsautomation.plugins.WebAnalyticTool;
import com.thoughtworks.webanalyticsautomation.scriptrunner.helper.WebDriverScriptRunnerHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static com.thoughtworks.webanalyticsautomation.Controller.getInstance;
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
    private String log4jPropertiesAbsoluteFilePath = Utils.getAbsolutePath(new String[] {"resources","log4j.properties"});
    private String inputDataFileName = Utils.getAbsolutePath(new String[] {"test", "sampledata", "TestData.xml"});
    private String actionName = "OpenWAATArticleOnBlog_HttpSniffer";
    private WebDriverScriptRunnerHelper webDriverScriptRunnerHelper;
    private WebDriver driverInstance;

    @Test
    public void captureAndVerifyDataReportedToWebAnalytics_HTTPSniffer_GoogleAnalytics_WebDriver_Firefox() throws
            Exception {
        String baseURL = "http://essenceoftesting.blogspot.com";
        String navigateToURL = baseURL + "/2011/01/my-article-on-future-of-test-automation.html";
        String[] urlPatterns = new String[] {"action=backlinks&widgetId"};
        int minimumNumberOfPackets = 1;

        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, log4jPropertiesAbsoluteFilePath);
        engine.enableWebAnalyticsTesting();

        startWebDriver(BROWSER.firefox, baseURL);
        driverInstance.get(navigateToURL);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) { }
        Result verificationResult = engine.verifyWebAnalyticsData(inputDataFileName, actionName, urlPatterns, minimumNumberOfPackets);

        assertNotNull(verificationResult.getVerificationStatus(), "Verification status should NOT be NULL");
        assertNotNull(verificationResult.getListOfErrors(), "Failure details should NOT be NULL");
        logVerificationErrors(verificationResult);
        Assert.assertEquals(verificationResult.getVerificationStatus(), Status.PASS, "Verification status should be PASS");
        assertEquals(verificationResult.getListOfErrors().size(), 0, "Failure details should be empty");
    }

    private void startWebDriver(BROWSER browser, String baseURL) {
        webDriverScriptRunnerHelper = new WebDriverScriptRunnerHelper(logger, browser, baseURL);
        webDriverScriptRunnerHelper.startDriver();
        driverInstance = (WebDriver) webDriverScriptRunnerHelper.getDriverInstance();
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
