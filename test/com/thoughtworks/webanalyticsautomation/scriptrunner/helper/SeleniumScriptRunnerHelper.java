package com.thoughtworks.webanalyticsautomation.scriptrunner.helper;

import com.thoughtworks.webanalyticsautomation.common.BROWSER;
import com.thoughtworks.webanalyticsautomation.common.Utils;
import com.thoughtworks.webanalyticsautomation.runUtils.UIDriverThreadRunner;
import org.apache.log4j.Logger;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.*;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 1:11:18 PM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

public class SeleniumScriptRunnerHelper extends ScriptRunnerHelper {

    private UIDriverThreadRunner uiDriverThreadRunner;
    private String UIDriver_BROWSER ="*";
    private final String DRIVER_HOST ="localhost";
    private String TIMEOUT="180000";

    public SeleniumScriptRunnerHelper(Logger logger, BROWSER browser, String baseUrl) {
        super(logger, browser, baseUrl);
        UIDriver_BROWSER = "*" + browser.name();
    }

    @Override
    public void startDriver()
    {
        logger.info ("Starting Selenium");
        String command = "java -jar " + Utils.getAbsolutePath(new String[] {"lib", "test", "webTestingFrameworks", "webdriver", "selenium-server-standalone-2.25.0.jar"});
        logger.info (command);
        this.uiDriverThreadRunner = new UIDriverThreadRunner(logger);
        this.uiDriverThreadRunner.runInThread(command);
        int DRIVER_SERVER_PORT = 4444;
        startSeleniumSession(
                DRIVER_HOST,
                DRIVER_SERVER_PORT,
                UIDriver_BROWSER,
                BASE_URL);
        session().setTimeout(TIMEOUT);
        session().open(BASE_URL);
    }

    @Override
    public void stopDriver()
    {
        logger.info ("Stopping driver.");
        try {
            closeSeleniumSession();
            if (null != uiDriverThreadRunner) {
                uiDriverThreadRunner.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getDriverInstance() {
        return session();
    }
}