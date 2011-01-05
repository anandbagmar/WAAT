package com.thoughtworks.webanalyticsautomation.scriptrunner;

import com.thoughtworks.webanalyticsautomation.utils.BROWSER;
import org.apache.log4j.Logger;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Jan 4, 2011
 * Time: 10:39:48 AM
 */

public abstract class ScriptRunnerHelper {
    public static final String BASE_URL="http://digg.com";
    protected Logger logger;
    protected BROWSER browser;

    public ScriptRunnerHelper(Logger logger,BROWSER browser){
        this.logger = logger;
        this.browser = browser;
    }

    public abstract void startDriver();

    public abstract void stopDriver();

    public abstract Object getDriverInstance();
}
