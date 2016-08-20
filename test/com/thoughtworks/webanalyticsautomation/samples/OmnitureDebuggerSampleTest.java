package com.thoughtworks.webanalyticsautomation.samples;

import com.thoughtworks.webanalyticsautomation.Engine;
import com.thoughtworks.webanalyticsautomation.Result;
import com.thoughtworks.webanalyticsautomation.Status;
import com.thoughtworks.webanalyticsautomation.common.BROWSER;
import com.thoughtworks.webanalyticsautomation.common.Utils;
import com.thoughtworks.webanalyticsautomation.inputdata.InputFileType;
import com.thoughtworks.webanalyticsautomation.plugins.WebAnalyticTool;

import static com.thoughtworks.webanalyticsautomation.Controller.getInstance;

import com.thoughtworks.webanalyticsautomation.scriptrunner.helper.WebDriverScriptRunnerHelper;
import org.apache.log4j.Logger;
import org.testng.Assert;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.openqa.selenium.WebDriver;
import com.thoughtworks.webanalyticsautomation.common.TestBase;
import com.thoughtworks.webanalyticsautomation.scriptrunner.WebDriverScriptRunner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Feb 2, 2011
 * Time: 4:23:29 PM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

public class OmnitureDebuggerSampleTest extends TestBase {
    private Logger logger = Logger.getLogger(getClass());
    private Engine engine;
    private WebAnalyticTool webAnalyticTool = WebAnalyticTool.OMNITURE_DEBUGGER;
    private InputFileType inputFileType = InputFileType.XML;
    private boolean keepLoadedFileInMemory = true;
    private String log4jPropertiesAbsoluteFilePath = Utils.getAbsolutePath(new String[] {"resources", "log4j.properties"});
    private String inputDataFileName = Utils.getAbsolutePath(new String[] {"test", "sampledata", "TestData.xml"});
    private String actionName = "OpenUpcomingPage_OmnitureDebugger_Selenium";
    private WebDriverScriptRunnerHelper webDriverScriptRunnerHelper;
    private WebDriver driverInstance;

    @Test
    public void captureAndVerifyDataReportedToWebAnalytics_OmnitureDebugger_Selenium_IE() throws Exception {
        captureAndVerifyDataReportedToWebAnalytics_Omniture_Selenium(BROWSER.iehta);
    }

    @Test
    public void captureAndVerifyDataReportedToWebAnalytics_OmnitureDebugger_Selenium_Firefox() throws Exception {
        captureAndVerifyDataReportedToWebAnalytics_Omniture_Selenium(BROWSER.firefox);
    }

    private void captureAndVerifyDataReportedToWebAnalytics_Omniture_Selenium(BROWSER browser) throws Exception {
        String baseURL = "http://digg.com";
        String navigateToURL = baseURL + "/channel/sports";

        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, log4jPropertiesAbsoluteFilePath);
        engine.enableWebAnalyticsTesting(actionName);

        startSeleniumDriver(browser, baseURL);
        driverInstance.get(navigateToURL);

        Result verificationResult = engine.verifyWebAnalyticsData (inputDataFileName, actionName, new WebDriverScriptRunner(driverInstance));

        assertNotNull(verificationResult.getVerificationStatus(), "Verification status should NOT be NULL");
        assertNotNull(verificationResult.getListOfErrors(), "Failure details should NOT be NULL");
        logVerificationErrors(verificationResult);
        Assert.assertEquals(verificationResult.getVerificationStatus(), Status.PASS, "Verification status should be PASS");
        assertEquals(verificationResult.getListOfErrors().size(), 0, "Failure details should be empty");
    }

    private void startSeleniumDriver(BROWSER browser, String baseURL) {
        webDriverScriptRunnerHelper = new WebDriverScriptRunnerHelper(logger, browser, baseURL);
        webDriverScriptRunnerHelper.startDriver();
        driverInstance = (WebDriver) webDriverScriptRunnerHelper.getDriverInstance();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        engine.disableWebAnalyticsTesting();
        webDriverScriptRunnerHelper.stopDriver();
    }
}
