package com.thoughtworks.webanalyticsautomation.scriptrunner;

import com.thoughtworks.webanalyticsautomation.utils.BROWSER;
import com.thoughtworks.webanalyticsautomation.utils.UIDriverThreadRunner;
import org.apache.log4j.Logger;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.*;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 1:11:18 PM
 */

public class SeleniumScriptRunnerHelper extends ScriptRunnerHelper {

    private UIDriverThreadRunner uiDriverThreadRunner;
    private String UIDriver_BROWSER ="*";
    private final String DRIVER_HOST ="localhost";
    private int DRIVER_SERVER_PORT =4444;
    private String TIMEOUT="180000";

    public SeleniumScriptRunnerHelper(Logger logger, BROWSER browser) {
        super(logger, browser);
        UIDriver_BROWSER = "*" + browser.name();
    }

    @Override
    public void startDriver()
    {
        String command = "java -jar lib\\test\\webTestingFrameworks\\webdriver\\selenium-server-standalone-2.0b1.jar";
        this.uiDriverThreadRunner = new UIDriverThreadRunner(logger);
        this.uiDriverThreadRunner.runInThread(command);
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