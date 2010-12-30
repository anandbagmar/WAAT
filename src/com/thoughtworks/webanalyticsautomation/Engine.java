package com.thoughtworks.webanalyticsautomation;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 */

import com.thoughtworks.webanalyticsautomation.common.CONFIG;
import com.thoughtworks.webanalyticsautomation.common.Utils;
import com.thoughtworks.webanalyticsautomation.inputdata.Section;
import com.thoughtworks.webanalyticsautomation.inputdata.TestData;
import com.thoughtworks.webanalyticsautomation.plugins.PluginFactory;
import com.thoughtworks.webanalyticsautomation.plugins.Status;
import com.thoughtworks.webanalyticsautomation.scriptrunner.IScriptRunner;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

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
    
    public Result verifyWebAnalyticsData(String testDataFileName, String actionName, IScriptRunner scriptRunner) {
        if (isWebAnalyticsTestingEnabled()) {
            ArrayList<Section> expectedSectionList = TestData.sectionsFor(testDataFileName, actionName);
            pluginFactory = new PluginFactory();
            IWAATPlugin pluginInstance = pluginFactory.getWebAnalyticsPluginInstance(CONFIG.getWEB_ANALYTIC_TOOL());
            ArrayList<Section> actualSectionList = pluginInstance.captureSections (scriptRunner);
            return verifyWebAnalyticsData (actualSectionList, expectedSectionList);
        }
        else {
            logger.info("Web Analytics testing is disabled.");
            return new Result(Status.SKIPPED, new ArrayList<String> ());
        }
    }

    private Result verifyWebAnalyticsData(ArrayList<Section> actualSectionList, ArrayList<Section> expectedSectionList) {
        ArrayList<Section> filteredActualSectionListByKeyList = getFilteredSectionListFromPlugin(actualSectionList, expectedSectionList);
        return verifyTagLists(filteredActualSectionListByKeyList, expectedSectionList);
    }

    private ArrayList<Section> getFilteredSectionListFromPlugin(ArrayList<Section> actualSectionList, ArrayList<Section> expectedSectionList) {
        ArrayList<Section> filteredActualSectionListByKeyList = new ArrayList<Section>();

        for (Section expectedSection: expectedSectionList) {
            filteredActualSectionListByKeyList.addAll(getActualSectionsWithMatchingKeyList(actualSectionList, expectedSection.getLoadedKeyList()));
        }
        return filteredActualSectionListByKeyList;
    }

    private Collection<? extends Section> getActualSectionsWithMatchingKeyList(ArrayList<Section> actualSectionList, ArrayList<String> expectedKeyList) {
        ArrayList <Section> matchingActualSections = new ArrayList<Section>();
        boolean isExpectedKeyPresent;

        for (Section actualSection: actualSectionList) {
            isExpectedKeyPresent = false;
            for (String expectedKey: expectedKeyList) {
                isExpectedKeyPresent = isExpectedKeyPresentInActualSectionKeyList (actualSection.getLoadedKeyList(), expectedKey);
                if (!isExpectedKeyPresent){
                    break;
                }
            }
            if (isExpectedKeyPresent) {
                matchingActualSections.add(actualSection);
            }
        }
        return matchingActualSections;
    }

    private boolean isExpectedKeyPresentInActualSectionKeyList(ArrayList<String> actualKeyList, String expectedKey) {
        boolean isExpectedKeyPresent = false;
        for (String actualKey: actualKeyList) {
            if (actualKey.contains(expectedKey)) {
                isExpectedKeyPresent = true;
                break;
            }
        }
        return isExpectedKeyPresent;
    }

    private Result verifyTagLists (ArrayList<Section> actualSectionList, ArrayList<Section> expectedSectionList) {
        ArrayList<String> errorList = new ArrayList<String>();
        if ((actualSectionList.size() == 0) && (expectedSectionList.size() != 0)) {
            return new Result (Status.FAIL, getAllTagsFromExpectedSectionList (expectedSectionList));
        }
        else {
            for (Section expectedSection: expectedSectionList) {
                errorList.addAll(getListOfMissingTagsInActualSections(actualSectionList, expectedSection.getLoadedTagList()));
            }
/*
            for (Section actualSection: actualSectionList) {
                ArrayList<String> actualSectionLoadedTagList = actualSection.getLoadedTagList();

                for (Section expectedSection: expectedSectionList){
                    ArrayList<String> expectedSectionLoadedTagList = expectedSection.getLoadedTagList();
                    for (String expectedTag: expectedSectionLoadedTagList) {
                        if(!actualSectionLoadedTagList.contains(expectedTag)){
                            errorList.add(expectedTag);
                        }
                    }
                }
            }
*/
            return new Result(errorList);
        }
    }

    private Collection<? extends String> getListOfMissingTagsInActualSections(ArrayList<Section> actualSectionList, ArrayList<String> expectedTagList) {
        ArrayList<String> errorList = new ArrayList<String>();
        for (Section actualSection: actualSectionList) {
            errorList.addAll(getListOfMissingTagsFromEachActualSection (actualSection.getLoadedTagList(), expectedTagList));
        }
        return errorList;
    }

    private Collection<? extends String> getListOfMissingTagsFromEachActualSection(ArrayList<String> actualSectionTagList, ArrayList<String> expectedTagList) {
        ArrayList<String> errorList = new ArrayList<String>();
        for (String expectedTag: expectedTagList) {
            if (!isExpectedTagPresentInActualTagList(actualSectionTagList, expectedTag)) {
                errorList.add(expectedTag);
            }
        }
        return errorList;
    }

    private boolean isExpectedTagPresentInActualTagList(ArrayList<String> actualSectionTagList, String expectedTag) {
        boolean isExpectedTagPresent = false;
        for (String actualTag: actualSectionTagList) {
            if (actualTag.contains(expectedTag)) {
                isExpectedTagPresent = true;
                logger.debug("TAG FOUND: " + expectedTag);
                break;
            }
        }
        return isExpectedTagPresent;
    }

    private ArrayList<String> getAllTagsFromExpectedSectionList(ArrayList<Section> expectedSectionList) {
        ArrayList<String> allTags = new ArrayList<String> ();
        for (Section expectedSection: expectedSectionList) {
            allTags.addAll(expectedSection.getLoadedTagList());
        }
        return allTags;
    }
}
