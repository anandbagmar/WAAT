package com.thoughtworks.webanalyticsautomation.scriptrunner.helper;

import com.thoughtworks.webanalyticsautomation.common.BROWSER;
import org.apache.log4j.Logger;
import org.openqa.selenium.Proxy;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Jan 4, 2011
 * Time: 10:39:48 AM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

public abstract class ScriptRunnerHelper {
    static String BASE_URL;
    Logger logger;
    BROWSER browser;

    ScriptRunnerHelper(Logger logger, BROWSER browser, String baseURL){
        this.logger = logger;
        this.browser = browser;
        BASE_URL = baseURL;
    }

    public abstract void startDriver();

    public abstract void startDriverUsingProxy(Proxy proxy);

    public abstract void stopDriver();

    public abstract Object getDriverInstance();
}
