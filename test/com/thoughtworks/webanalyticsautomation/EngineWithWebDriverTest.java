package com.thoughtworks.webanalyticsautomation;

import com.thoughtworks.webanalyticsautomation.common.TestBase;
import com.thoughtworks.webanalyticsautomation.scriptrunner.WebDriverScriptRunner;
import com.thoughtworks.webanalyticsautomation.scriptrunner.helper.WebDriverScriptRunnerHelper;
import com.thoughtworks.webanalyticsautomation.utils.BROWSER;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static com.thoughtworks.webanalyticsautomation.Controller.getInstance;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Jan 4, 2011
 * Time: 10:36:28 AM
 */

@Test (singleThreaded = true)
public class EngineWithWebDriverTest extends TestBase {
    private WebDriverScriptRunnerHelper webDriverScriptRunnerHelper;
    private WebDriver driverInstance;
    private Engine engine;
    private String webAnalyticTool = "omniture";
    private String inputFileType = "xml";
    private boolean keepLoadedFileInMemory = true;
    private String log4jPropertiesAbsoluteFilePath = System.getProperty("user.dir") + "\\resources\\log4j.properties";
    private String actionName = "OpenUpcomingPage_WebDriver";

    private String inputDataFileName = System.getProperty("user.dir") + "\\test\\sampledata\\OmnitureTestData.xml";

    @AfterMethod()
    public void tearDown() throws Exception {
        engine.disableWebAnalyticsTesting();
        webDriverScriptRunnerHelper.stopDriver();
    }

    @Test
    public void captureAndVerifyDataReportedToWebAnalytics_WebDriver_IE() throws Exception {
        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, log4jPropertiesAbsoluteFilePath);
        engine.enableWebAnalyticsTesting();

        startWebDriver(BROWSER.iehta);
        driverInstance.get(WebDriverScriptRunnerHelper.BASE_URL + "/upcoming");

        Result verificationResult = engine.verifyWebAnalyticsData (inputDataFileName, actionName, new WebDriverScriptRunner(driverInstance));

        assertNotNull(verificationResult.getVerificationStatus(), "Verification status should NOT be NULL");
        assertNotNull(verificationResult.getListOfErrors(), "Failure details should NOT be NULL");
        logVerificationErrors(verificationResult);
        assertEquals(verificationResult.getVerificationStatus(), Status.PASS, "Verification status should be PASS");
        assertEquals(verificationResult.getListOfErrors().size(), 0, "Failure details should be empty");
    }

    @Test
    public void captureAndVerifyDataReportedToWebAnalytics_WebDriver_Firefox() throws Exception {
        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, log4jPropertiesAbsoluteFilePath);
        engine.enableWebAnalyticsTesting();

        startWebDriver(BROWSER.firefox);
        driverInstance.get(WebDriverScriptRunnerHelper.BASE_URL + "/upcoming");

        Result verificationResult = engine.verifyWebAnalyticsData (inputDataFileName, actionName, new WebDriverScriptRunner(driverInstance));

        assertNotNull(verificationResult.getVerificationStatus(), "Verification status should NOT be NULL");
        assertNotNull(verificationResult.getListOfErrors(), "Failure details should NOT be NULL");
        logVerificationErrors(verificationResult);
        assertEquals(verificationResult.getVerificationStatus(), Status.PASS, "Verification status should be PASS");
        assertEquals(verificationResult.getListOfErrors().size(), 0, "Failure details should be empty");
    }

    private void startWebDriver(BROWSER browser) {
        webDriverScriptRunnerHelper = new WebDriverScriptRunnerHelper(logger, browser);
        webDriverScriptRunnerHelper.startDriver();
        driverInstance = (WebDriver) webDriverScriptRunnerHelper.getDriverInstance();
    }
}
