package com.thoughtworks.webanalyticsautomation;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 */

import com.thoughtworks.webanalyticsautomation.plugins.Status;
import com.thoughtworks.webanalyticsautomation.scriptrunner.SeleniumScriptRunner;
import com.thoughtworks.webanalyticsautomation.utils.BROWSER;
import com.thoughtworks.webanalyticsautomation.utils.SeleniumScriptRunnerHelper;
import com.thoughtworks.webanalyticsautomation.utils.TestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.session;
import static com.thoughtworks.webanalyticsautomation.Controller.getInstance;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class EngineWithSeleniumTest extends TestBase {
    private SeleniumScriptRunnerHelper seleniumScriptRunnerHelper;

    private Engine engine;
    private String webAnalyticTool = "omniture";
    private String inputFileType = "xml";
    private boolean keepLoadedFileInMemory = true;

    private String inputDataFileName = System.getProperty("user.dir") + "\\test\\sampledata\\OmnitureTestData.xml";

    @AfterMethod()
    public void tearDown() throws Exception {
        engine.disableWebAnalyticsTesting();
        seleniumScriptRunnerHelper.stopSeleniumDriver();
    }

    @Test
    public void captureAndVerifyDataReportedToWebAnalytics_Selenium_IE() throws Exception {
        String actionName = "OpenUpcomingPage";
        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory);
        engine.enableWebAnalyticsTesting();

        startSeleniumDriver(BROWSER.iehta);
        session().open(SeleniumScriptRunnerHelper.BASE_URL + "/upcoming");

        Result verificationResult = engine.verifyWebAnalyticsData (inputDataFileName, actionName, new SeleniumScriptRunner(session()));

        assertNotNull(verificationResult.getVerificationStatus(), "Verification status should NOT be NULL");
        assertNotNull(verificationResult.getListOfErrors(), "Failure details should NOT be NULL");
        logVerificationErrors(verificationResult);
        assertEquals(verificationResult.getVerificationStatus(), Status.PASS, "Verification status should be PASS");
        assertEquals(verificationResult.getListOfErrors().size(), 0, "Failure details should be empty");
    }

    @Test
    public void captureAndVerifyDataReportedToWebAnalytics_Selenium_Firefox() throws Exception {
        String actionName = "OpenUpcomingPage";
        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory);
        engine.enableWebAnalyticsTesting();

        startSeleniumDriver(BROWSER.firefox);
        session().open(SeleniumScriptRunnerHelper.BASE_URL + "/upcoming");

        Result verificationResult = engine.verifyWebAnalyticsData (inputDataFileName, actionName, new SeleniumScriptRunner(session()));

        assertNotNull(verificationResult.getVerificationStatus(), "Verification status should NOT be NULL");
        assertNotNull(verificationResult.getListOfErrors(), "Failure details should NOT be NULL");
        logVerificationErrors(verificationResult);
        assertEquals(verificationResult.getVerificationStatus(), Status.PASS, "Verification status should be PASS");
        assertEquals(verificationResult.getListOfErrors().size(), 0, "Failure details should be empty");
    }

    private void logVerificationErrors(Result verificationResult) {
        if (verificationResult.getListOfErrors().size()>0){
            for (String error: verificationResult.getListOfErrors()) {
                logger.info (error);
            }
        }
    }

    private void startSeleniumDriver(BROWSER browser) {
        seleniumScriptRunnerHelper = new SeleniumScriptRunnerHelper(logger, browser);
        seleniumScriptRunnerHelper.startSeleniumDriver();
    }

/*
    public void nullActionNameTest() throws Exception {
        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, logDirectory);
        String actionName = null;
        Result verificationResult = engine.verifyWebAnalyticsData (inputDataFileName, actionName, null);
        assertEquals (verificationResult.getVerificationStatus(), false, "Verification Status should be FALSE");
    }

    public void emptyActionNameTest() throws Exception {
        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, logDirectory);
        String actionName = "";
        Result verificationResult = engine.verifyWebAnalyticsData (inputDataFileName, actionName, null);

    }

    public void invalidActionNameTest() throws Exception {
        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, logDirectory);
        String inputDataFileName = "E:\\Work\\src\\WAAT\\test\\sampledata\\OmnitureTestData.xml";
        String actionName = "OpenScheduler_Omniture_Tag_ForAll";

        Result verificationResult = engine.verifyWebAnalyticsData (inputDataFileName, actionName, null);
    }
*/
}
