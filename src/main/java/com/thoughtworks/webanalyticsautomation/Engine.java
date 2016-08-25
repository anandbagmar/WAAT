package com.thoughtworks.webanalyticsautomation;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

import com.thoughtworks.webanalyticsautomation.common.CONFIG;
import com.thoughtworks.webanalyticsautomation.common.Utils;
import com.thoughtworks.webanalyticsautomation.inputdata.Section;
import com.thoughtworks.webanalyticsautomation.inputdata.TestData;
import com.thoughtworks.webanalyticsautomation.plugins.PluginFactory;
import com.thoughtworks.webanalyticsautomation.plugins.WaatPlugin;
import com.thoughtworks.webanalyticsautomation.plugins.WebAnalyticTool;
import com.thoughtworks.webanalyticsautomation.scriptrunner.ScriptRunner;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Engine extends CONFIG {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();

    public Engine() {
        logger = Logger.getLogger(getClass());
    }

    public void enableWebAnalyticsTesting(String name) {
        String threadLocalID = Utils.getThreadLocalID();
        logger.info("Enabling Web Analytics Testing - Setting variable on ThreadLocal: " + threadLocalID);
        threadLocal.set(threadLocalID);
        enablePacketCapture(name);
    }

    private void enablePacketCapture(String name) {
        if (CONFIG.getWEB_ANALYTIC_TOOL().equals(WebAnalyticTool.PROXY)) {
            logger.info("Enable Packet Capture");
            WaatPlugin pluginInstance = PluginFactory.getWebAnalyticsPluginInstance(CONFIG.getWEB_ANALYTIC_TOOL());
            pluginInstance.enableCapture(name);
        }
    }

    boolean isWebAnalyticsTestingEnabled() {
        String threadLocalID = Utils.getThreadLocalID();
        logger.info("Is Web Analytics Testing Enabled? - Getting variable value from ThreadLocal: " + threadLocalID);
        boolean status = StringUtils.equals(threadLocal.get(), threadLocalID);
        logger.info("Is Web Analytics Testing Enabled? - WebAnalytics enabled status: " + status);
        return status;
    }

    public void disableWebAnalyticsTesting() {
        logger.info("Web Analytics Testing Disabled - Reset variable on ThreadLocal");
        threadLocal.set(null);
    }

    public Result verifyWebAnalyticsData(String testDataFileName, String actionName, ScriptRunner scriptRunner) {
        if (isWebAnalyticsTestingEnabled()) {
            logger.info("Verify Web Analytics Data for " + CONFIG.getWEB_ANALYTIC_TOOL());
            ArrayList<Section> expectedSectionList = TestData.getSectionsFor(testDataFileName, actionName);
            WaatPlugin pluginInstance = PluginFactory.getWebAnalyticsPluginInstance(CONFIG.getWEB_ANALYTIC_TOOL());
            ArrayList<Section> actualSectionList = pluginInstance.captureSections(scriptRunner);
            return verifyWebAnalyticsData(actionName, actualSectionList, expectedSectionList);
        } else {
            logger.info("Web Analytics testing is disabled. Return Result - " + Status.SKIPPED);
            return new Result(actionName, Status.SKIPPED, new ArrayList<String>());
        }
    }

    public Result verifyWebAnalyticsData(String testDataFileName, String actionName, List<String> urlPatterns, int minimumNumberOfPackets) {
        if (isWebAnalyticsTestingEnabled()) {
            logger.info("Verify Web Analytics Data for " + CONFIG.getWEB_ANALYTIC_TOOL());
            ArrayList<Section> expectedSectionList = TestData.getSectionsFor(testDataFileName, actionName);
            WaatPlugin pluginInstance = PluginFactory.getWebAnalyticsPluginInstance(CONFIG.getWEB_ANALYTIC_TOOL());
            ArrayList<Section> actualSectionList = pluginInstance.captureSections(urlPatterns, minimumNumberOfPackets);
            return verifyWebAnalyticsData(actionName, actualSectionList, expectedSectionList);
        } else {
            logger.info("Web Analytics testing is disabled. Return Result - " + Status.SKIPPED);
            return new Result(actionName, Status.SKIPPED, new ArrayList<String>());
        }
    }

    private Result verifyWebAnalyticsData(String actionName, ArrayList<Section> actualSectionList, ArrayList<Section> expectedSectionList) {
        System.out.println("+++++++++++++++++++++++++++++Inside web analytics enabled ++++++++++++++++++++++++++++++++++++++++++");
        if ((actualSectionList.size() == 0) && (expectedSectionList.size() != 0)) {
            System.out.println("Done1");
            return new Result(actionName, Status.FAIL, getAllTagsFromExpectedSectionList(expectedSectionList));
        } else {
            System.out.println("Done2");
            ArrayList<String> errorList = new ArrayList<String>();
            for (Section expectedSection : expectedSectionList) {
                errorList.addAll(getListOfMissingTagsInActualSections(actualSectionList, expectedSection));
            }
            if (errorList.size() != 0) {
                System.out.println("Done3");
                errorList.addAll(addActualSectionsInErrorList(actualSectionList));
            }
            return new Result(actionName, errorList);
        }
    }

    private ArrayList<String> addActualSectionsInErrorList(ArrayList<Section> actualSectionList) {
        ArrayList<String> errorList = new ArrayList<String>();
        int count = 1;
        for (Section actualSection : actualSectionList) {
            errorList.add("Adding Actual Section List: " + count++);
            for (String actualTag : actualSection.getLoadedTagList()) {
                errorList.add(actualTag);
            }
        }
        return errorList;
    }

    private ArrayList<String> getListOfMissingTagsInActualSections(ArrayList<Section> actualSectionList, Section expectedSection) {
        ArrayList<String> errorList = new ArrayList<String>();
        int actualNumberOfEventsTriggered = actualSectionList.size();
        int expectedNumberOfEventsToBeTriggered = expectedSection.getNumberOfEventsTriggered();
        errorList.addAll(verifyNumberOfEventsForEachExpectedSection(actualNumberOfEventsTriggered, expectedNumberOfEventsToBeTriggered));
        errorList.addAll(verifyTagsForEachExpectedSection(actualSectionList, expectedSection));
        return errorList;
    }

    private ArrayList<String> verifyTagsForEachExpectedSection(ArrayList<Section> actualSectionList, Section expectedSection) {
        ArrayList<String> errorList = new ArrayList<String>();
        for (Section actualSection : actualSectionList) {
            errorList.addAll(getListOfMissingTagsFromEachActualSection(actualSection.getLoadedTagList(), expectedSection.getLoadedTagList()));
        }
        return errorList;
    }

    private ArrayList<String> verifyNumberOfEventsForEachExpectedSection(int actualNumberOfEventsTriggered, int expectedNumberOfEventsToBeTriggered) {
        ArrayList<String> errorList = new ArrayList<String>();
        if (actualNumberOfEventsTriggered != expectedNumberOfEventsToBeTriggered) {
            errorList.add("Number of events triggered (" + actualNumberOfEventsTriggered + ") NOT equal to expected number of events (" + expectedNumberOfEventsToBeTriggered + ").");
        }
        return errorList;
    }

    private ArrayList<String> getListOfMissingTagsFromEachActualSection(ArrayList<String> actualSectionTagList, ArrayList<String> expectedTagList) {
        ArrayList<String> errorList = new ArrayList<String>();
        for (String expectedTag : expectedTagList) {
            if (!isExpectedTagPresentInActualTagList(actualSectionTagList, expectedTag)) {
                errorList.add(expectedTag);
            }
        }
        if (errorList.size() != 0) {
            errorList.add(0, "Following tags found missing: ");
        }
        return errorList;
    }

    private boolean isExpectedTagPresentInActualTagList(ArrayList<String> actualSectionTagList, String expectedTag) {
        boolean isExpectedTagPresent = false;
        //Done pattern matching to support variable values
        Pattern pattern = Pattern.compile(expectedTag);

        for (String actualTag : actualSectionTagList) {
            Matcher matcher = pattern.matcher(actualTag);
            boolean result = matcher.matches();
            if (result) {
                isExpectedTagPresent = true;
                logger.debug("TAG FOUND: " + expectedTag);
                break;
            }
        }
        return isExpectedTagPresent;
    }

    private ArrayList<String> getAllTagsFromExpectedSectionList(ArrayList<Section> expectedSectionList) {
        ArrayList<String> allTags = new ArrayList<String>();
        allTags.add("Following tags found missing: ");
        for (Section expectedSection : expectedSectionList) {
            allTags.addAll(expectedSection.getLoadedTagList());
        }
        return allTags;
    }

    public Object getSeleniumBasedProxyPlugin() {
        logger.debug("Get Selenium Based Proxy Plugin");
        WaatPlugin pluginInstance = PluginFactory.getWebAnalyticsPluginInstance(CONFIG.getWEB_ANALYTIC_TOOL());
        return pluginInstance.getSeleniumProxy(0);
    }

    public Object getAppiumBasedProxyPlugin(){
        logger.info("Get Appium Based Proxy Plugin");
        WaatPlugin pluginInstance = PluginFactory.getWebAnalyticsPluginInstance(CONFIG.getWEB_ANALYTIC_TOOL());
        return pluginInstance.getSeleniumProxy(5555);
    }

}