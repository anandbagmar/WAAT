package com.thoughtworks.webanalyticsautomation.samples;

import com.thoughtworks.webanalyticsautomation.Controller;
import com.thoughtworks.webanalyticsautomation.Engine;
import com.thoughtworks.webanalyticsautomation.Result;
import com.thoughtworks.webanalyticsautomation.Status;
import com.thoughtworks.webanalyticsautomation.common.BROWSER;
import com.thoughtworks.webanalyticsautomation.common.TestBase;
import com.thoughtworks.webanalyticsautomation.common.Utils;
import com.thoughtworks.webanalyticsautomation.inputdata.InputFileType;
import com.thoughtworks.webanalyticsautomation.plugins.WebAnalyticTool;
import com.thoughtworks.webanalyticsautomation.scriptrunner.helper.MobileDriverScriptRunnerHelper;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.MobileElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.Proxy;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.ArrayList;
import static com.thoughtworks.webanalyticsautomation.Controller.getInstance;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by gshilpa on 8/25/16.
 */
public class VerifyWebAnalyticsForAndroid extends TestBase{


    protected static AndroidDriver<MobileElement> driverAndroid;
    private Logger logger = Logger.getLogger(getClass());
    private Engine engine;
    private WebAnalyticTool webAnalyticTool = WebAnalyticTool.PROXY;
    private InputFileType inputFileType = InputFileType.XML;
    private boolean keepLoadedFileInMemory = true;
    private String log4jPropertiesAbsoluteFilePath = Utils.getAbsolutePath(new String[] {"resources","log4j.properties"});
    private String inputDataFileName = Utils.getAbsolutePath(new String[] {"src", "test", "sampledata", "TestData.xml"});
    private String actionName = "OpenWAATArticleOnBlog_Proxy";
    private MobileDriverScriptRunnerHelper mobileDriverScirptRunnerHelper;


    @BeforeMethod
    public void setup () throws IOException {
        Controller.reset();
    }

    @Test
    public void captureVerifyAnalyticsDataForAndroidMobile()
    {
        String baseURL = "http://essenceoftesting.blogspot.com";
        String navigateToURL = baseURL + "/search/label/waat";
        ArrayList<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("https://ssl.google-analytics.com/__");
        int minimumNumberOfPackets = 1;
        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, log4jPropertiesAbsoluteFilePath);
        Proxy mobileProxy=(Proxy) engine.getAppiumBasedProxyPlugin();
        startMobileDriver(BROWSER.chrome, baseURL, mobileProxy);
        logger.info("********************************************Start capture********************************************");
        engine.enableWebAnalyticsTesting(actionName);
        logger.info("********************************************Do action********************************************");
        driverAndroid.get(navigateToURL);
        try {
            Thread.sleep(10000);
            logger.info("Waiting for application to load");
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        logger.info("********************************************Verify result********************************************");
        Result verificationResult = engine.verifyWebAnalyticsData(inputDataFileName, actionName, urlPatterns, minimumNumberOfPackets);
        logger.info("********************************************Verification pattern should not be null********************************************");
        assertNotNull(verificationResult.getVerificationStatus(), "Verification status should NOT be NULL");
        logger.info("********************************************Failure details should NOT be NULL********************************************");
        assertNotNull(verificationResult.getListOfErrors(), "Failure details should NOT be NULL");
        logVerificationErrors(verificationResult);
        logger.info("********************************************Verification status should be PASS********************************************");
        assertEquals(verificationResult.getVerificationStatus(), Status.PASS, "Verification status should be PASS");
        logger.info("********************************************Failure details should be empty********************************************");
        assertEquals(verificationResult.getListOfErrors().size(), 0, "Failure details should be empty");
    }

    private void startMobileDriver(BROWSER browser, String baseURL, Proxy mobileProxy) {
        mobileDriverScirptRunnerHelper = new MobileDriverScriptRunnerHelper(logger, browser, baseURL);
        mobileDriverScirptRunnerHelper.startDriverUsingProxy(mobileProxy);
        driverAndroid = (AndroidDriver<MobileElement>) mobileDriverScirptRunnerHelper.getDriverInstance();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        if (engine!=null) {
            engine.disableWebAnalyticsTesting();
        }
        if (mobileDriverScirptRunnerHelper!=null) {
            mobileDriverScirptRunnerHelper.stopDriver();
        }
    }
}
