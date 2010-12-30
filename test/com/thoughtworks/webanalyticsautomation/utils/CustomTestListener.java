package com.thoughtworks.webanalyticsautomation.utils;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 */

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomTestListener implements ITestListener {
    private static String WAAT_START_LOG_MESSAGE = "\n\n" +
            "------------------------------------------------------------------------------------\n" +
            "----------------------------------" +
            " Start WAAT Log " +
            "----------------------------------\n" +
            "------------------------------------------------------------------------------------\n";

    private static String WAAT_END_LOG_MESSAGE = "\n\n" +
            "------------------------------------------------------------------------------------\n" +
            "-----------------------------------" +
            " End WAAT Log " +
            "-----------------------------------\n" +
            "------------------------------------------------------------------------------------\n";

/*
    {
        PropertyConfigurator.configure(CONFIG.getLOG4J_PROPERTIES_ABSOLUTE_FILE_PATH());
    }
*/

    private Logger logger = Logger.getLogger(getClass());

    public void onTestStart(ITestResult iTestResult) {
        String testName = "Test name: " + iTestResult.getName();
        testName += "\n\t------------------------------------------------------------\n";
        logger.info (testName);
    }

    public void onTestSuccess(ITestResult iTestResult) {
    }

    public void onTestFailure(ITestResult iTestResult) {
    }

    public void onTestSkipped(ITestResult iTestResult) {
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    }

    public void onStart(ITestContext iTestContext) {
        logger.info(WAAT_START_LOG_MESSAGE);
    }

    public void onFinish(ITestContext iTestContext) {
        logger.info(WAAT_END_LOG_MESSAGE);
    }
    
}
