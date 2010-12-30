package com.thoughtworks.webanalyticsautomation.utils;

import org.apache.log4j.Logger;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.*;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 1:11:18 PM
 */

public class SeleniumScriptRunnerHelper {
    public static String BASE_URL="http://digg.com";

    private static String SELENIUM_HOST="localhost";
    private static int SELENIUM_SERVER_PORT=4444;
    private static String UIDriver_BROWSER ="*";
    private static String TIMEOUT="180000";
    private UIDriverThreadRunner uiDriverThreadRunner;
    private Logger logger;

    public SeleniumScriptRunnerHelper(Logger logger, BROWSER browser) {
        this.logger = logger;
        UIDriver_BROWSER = "*" + browser.name();
    }

    public void startSeleniumDriver ()
    {
        String cmd = "java -jar lib\\test\\webTestingFrameworks\\selenium-server-1.0.3-standalone.jar";
        this.uiDriverThreadRunner = new UIDriverThreadRunner(logger);
        this.uiDriverThreadRunner.runInThread(cmd);
        startSeleniumSession(
                SELENIUM_HOST,
                SELENIUM_SERVER_PORT,
                UIDriver_BROWSER,
                BASE_URL);
        session().setTimeout(TIMEOUT);
        session().open(BASE_URL);
    }

    public void stopSeleniumDriver()
    {
        try {
            closeSeleniumSession();
            if (null != uiDriverThreadRunner) {
                uiDriverThreadRunner.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
