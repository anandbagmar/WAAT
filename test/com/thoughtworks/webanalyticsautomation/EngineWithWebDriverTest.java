package com.thoughtworks.webanalyticsautomation;

import com.thoughtworks.webanalyticsautomation.common.BROWSER;
import com.thoughtworks.webanalyticsautomation.common.TestBase;
import com.thoughtworks.webanalyticsautomation.plugins.WebAnalyticTool;
import com.thoughtworks.webanalyticsautomation.scriptrunner.WebDriverScriptRunner;
import com.thoughtworks.webanalyticsautomation.scriptrunner.helper.WebDriverScriptRunnerHelper;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.thoughtworks.webanalyticsautomation.Controller.getInstance;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;


/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Jan 4, 2011
 * Time: 10:36:28 AM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

@Test (singleThreaded = true)
public class EngineWithWebDriverTest extends TestBase {
    private WebDriverScriptRunnerHelper webDriverScriptRunnerHelper;
    private WebDriver driverInstance;

    @BeforeMethod
    public void setup () {
        Controller.reset();
    }

    @AfterMethod()
    public void tearDown() throws Exception {
        engine.disableWebAnalyticsTesting();
        webDriverScriptRunnerHelper.stopDriver();
    }

    @Test
    public void captureAndVerifyDataReportedToWebAnalytics_WebDriver_IE() throws Exception {
        captureAndVerifyDataReportedToWebAnalytics_WebDriver(BROWSER.iehta);
    }

    @Test
    public void captureAndVerifyDataReportedToWebAnalytics_WebDriver_Firefox() throws Exception {
        captureAndVerifyDataReportedToWebAnalytics_WebDriver(BROWSER.firefox);
    }

    private void captureAndVerifyDataReportedToWebAnalytics_WebDriver(BROWSER browser) {
        String actionName = "OpenUpcomingPage_OmnitureDebugger_WebDriver";
        WebAnalyticTool webAnalyticTool = WebAnalyticTool.OMNITURE_DEBUGGER;

        String baseURL = "http://digg.com";
        String navigateToURL = baseURL + "/channel/sports";

        boolean keepLoadedFileInMemory = true;
        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, log4jPropertiesAbsoluteFilePath);
        engine.enableWebAnalyticsTesting(actionName);

        startWebDriver(browser, baseURL);
        driverInstance.get(navigateToURL);

        Result verificationResult = engine.verifyWebAnalyticsData (inputDataFileName, actionName, new WebDriverScriptRunner(driverInstance));

        assertNotNull(verificationResult.getVerificationStatus(), "Verification status should NOT be NULL");
        assertNotNull(verificationResult.getListOfErrors(), "Failure details should NOT be NULL");
        logVerificationErrors(verificationResult);
        assertEquals(verificationResult.getVerificationStatus(), Status.PASS, "Verification status should be PASS");
        assertEquals(verificationResult.getListOfErrors().size(), 0, "Failure details should be empty");
    }

    private void startWebDriver(BROWSER browser, String baseURL) {
        webDriverScriptRunnerHelper = new WebDriverScriptRunnerHelper(logger, browser, baseURL);
        webDriverScriptRunnerHelper.startDriver();
        driverInstance = (WebDriver) webDriverScriptRunnerHelper.getDriverInstance();
    }
}
