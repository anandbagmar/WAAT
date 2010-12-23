package com.thoughtworks.webanalyticsautomation;

import com.thoughtworks.webanalyticsautomation.common.CONFIG;
import com.thoughtworks.webanalyticsautomation.common.Utils;
import com.thoughtworks.webanalyticsautomation.inputdata.Section;
import com.thoughtworks.webanalyticsautomation.inputdata.TestData;
import com.thoughtworks.webanalyticsautomation.plugins.PluginFactory;
import com.thoughtworks.webanalyticsautomation.plugins.Status;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class Engine extends CONFIG {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();
    private PluginFactory pluginFactory;

    public Engine () {
        logger = Logger.getLogger(getClass());
    }

    public void enableWebAnalyticsTesting() {
        String threadLocalID = Utils.getThreadLocalID();
        logger.info("Setting variable on ThreadLocal: " + threadLocalID);
        threadLocal.set(threadLocalID);
    }

    public boolean isWebAnalyticsTestingEnabled() {
        String threadLocalID = Utils.getThreadLocalID();
        logger.info("Getting variable value from ThreadLocal: " + threadLocalID);
        boolean status = StringUtils.equals(threadLocal.get(), threadLocalID);
        logger.info("WebAnalytics enabled status: " + status);
        return status;
    }

    public void disableWebAnalyticsTesting() {
        logger.info("Reset variable on ThreadLocal");
        threadLocal.set(null);
    }
    
    public Result verifyWebAnalyticsData(String testDataFileName, String actionName) {
        if (isWebAnalyticsTestingEnabled()) {
            ArrayList expectedSectionList = TestData.sectionsFor(testDataFileName, actionName);
            pluginFactory = new PluginFactory();
            IWAATPlugin pluginInstance = pluginFactory.getWebAnalyticsPluginInstance(CONFIG.getWEB_ANALYTIC_TOOL());
            ArrayList<Section> actualSectionList = pluginInstance.captureSections ();
            return verifyWebAnalyticsData (actualSectionList, expectedSectionList);
        }
        else {
            logger.info("Web Analytics testing is disabled.");
            return new Result(Status.SKIPPED);
        }
    }

    private Result verifyWebAnalyticsData(ArrayList<Section> actualSectionList, ArrayList expectedSectionList) {
        ArrayList<String> errorList = new ArrayList<String> ();
        return new Result(Status.FAIL, errorList);
    }
}
