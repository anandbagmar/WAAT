package com.thoughtworks.webanalyticsautomation;

import com.thoughtworks.webanalyticsautomation.utils.TestBase;
import org.testng.annotations.Test;

import static com.thoughtworks.webanalyticsautomation.Controller.getInstance;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class EngineTest extends TestBase {

    public void nullActionNameTest() throws Exception {
        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, logDirectory);
        String actionName = null;
        Result verificationResult = engine.verifyWebAnalyticsData (inputDataFileName, actionName);
        assertEquals (verificationResult.getVerificationStatus(), false, "Verification Status should be FALSE");
    }

    public void emptyActionNameTest() throws Exception {
        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, logDirectory);
        String actionName = "";
        Result verificationResult = engine.verifyWebAnalyticsData (inputDataFileName, actionName);

    }

    public void invalidActionNameTest() throws Exception {
        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, logDirectory);
        String inputDataFileName = "E:\\Work\\src\\WAAT\\test\\sampledata\\OmnitureData.xml";
        String actionName = "OpenScheduler_Omniture_Tag_ForAll";

        Result verificationResult = engine.verifyWebAnalyticsData (inputDataFileName, actionName);

    }

    @Test
    public void captureAndVerifyDataReportedToWebAnalytics() throws Exception {
        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, logDirectory);
        String actionName = "Clean_Scheduler_Step1_Complete_Tags";
        engine.enableWebAnalyticsTesting();
        Result verificationResult = engine.verifyWebAnalyticsData (inputDataFileName, actionName);
        assertNotNull(verificationResult.getVerificationStatus(), "Verification status should NOT be NULL");
        assertNotNull(verificationResult.getListOfErrors(), "Failure details should NOT be NULL");
        assertEquals(verificationResult.getVerificationStatus(), true, "Verification status should be TRUE");
        assertEquals(verificationResult.getListOfErrors(), "", "Failure details should be empty");
    }

}
