package com.thoughtworks.webanalyticsautomation.runUtils;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
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

    private static String WAAT_TEST_END_LOG_MESSAGE = "\n\n" +
            "-----------------------------------" +
            " TESTNAME " +
            "-----------------------------------\n";

    private Logger logger = Logger.getLogger(getClass());

    public void onTestStart(ITestResult iTestResult) {
        String testName = "Test name: " + iTestResult.getName();
        testName += "\n\t------------------------------------------------------------\n";
        logger.info (testName);
    }

    public void onTestSuccess(ITestResult iTestResult) {
        String testCompleteMessage = WAAT_TEST_END_LOG_MESSAGE.replace("TESTNAME", iTestResult.getName() + ": PASS");
        logger.info(testCompleteMessage);
    }

    public void onTestFailure(ITestResult iTestResult) {
        String testCompleteMessage = WAAT_TEST_END_LOG_MESSAGE.replace("TESTNAME", iTestResult.getName() + ": FAIL");
        logger.info(testCompleteMessage);
    }

    public void onTestSkipped(ITestResult iTestResult) {
        String testCompleteMessage = WAAT_TEST_END_LOG_MESSAGE.replace("TESTNAME", iTestResult.getName() + ": SKIP");
        logger.info(testCompleteMessage);
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
