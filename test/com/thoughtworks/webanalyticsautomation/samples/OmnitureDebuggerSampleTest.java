package com.thoughtworks.webanalyticsautomation.samples;

import com.thoughtworks.webanalyticsautomation.Engine;
import com.thoughtworks.webanalyticsautomation.Result;
import com.thoughtworks.webanalyticsautomation.Status;
import com.thoughtworks.webanalyticsautomation.common.BROWSER;
import com.thoughtworks.webanalyticsautomation.common.Utils;
import com.thoughtworks.webanalyticsautomation.inputdata.InputFileType;
import com.thoughtworks.webanalyticsautomation.plugins.WebAnalyticTool;
import com.thoughtworks.webanalyticsautomation.scriptrunner.SeleniumScriptRunner;
import static com.thoughtworks.webanalyticsautomation.Controller.getInstance;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.webanalyticsautomation.common.TestBase;
import com.thoughtworks.webanalyticsautomation.scriptrunner.helper.SeleniumScriptRunnerHelper;

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
    private Selenium selenium;
    private SeleniumScriptRunnerHelper seleniumScriptRunnerHelper;

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
        String navigateToURL = baseURL + "/upcoming";

        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, log4jPropertiesAbsoluteFilePath);
        engine.enableWebAnalyticsTesting();

        startSeleniumDriver(browser, baseURL);
        selenium.open(navigateToURL);

        Result verificationResult = engine.verifyWebAnalyticsData (inputDataFileName, actionName, new SeleniumScriptRunner(selenium));

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
