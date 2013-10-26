package com.thoughtworks.webanalyticsautomation.inputdata;

import com.thoughtworks.webanalyticsautomation.common.CONFIG;
import com.thoughtworks.webanalyticsautomation.common.TestBase;
import com.thoughtworks.webanalyticsautomation.plugins.WebAnalyticTool;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static com.thoughtworks.webanalyticsautomation.Controller.getInstance;
import static com.thoughtworks.webanalyticsautomation.Controller.reset;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Dec 31, 2010
 * Time: 1:01:36 PM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

public class TestDataTest extends TestBase {
    private String actionName = "OpenUpcomingPage_OmnitureDebugger_Selenium";

    @Test
    public void loadOmnitureDataInMemoryTest() {
        ArrayList sections = TestData.getSectionsFor(inputDataFileName, actionName);
        assertEquals(sections.size(), 1);
        assertEquals(((Section)sections.get(0)).getLoadedTagList().size(), 13);
        logger.info("loadOmnitureDataInMemory test complete");
    }

    @Test
    public void keepLoadedFileInMemoryTest() throws Exception {
        keepLoadedFileInMemory = true;
        webAnalyticTool = WebAnalyticTool.OMNITURE_DEBUGGER;
        reset();
        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, log4jPropertiesAbsoluteFilePath);
        TestData.getSectionsFor(inputDataFileName, actionName);
        assertEquals(keepLoadedFileInMemory, CONFIG.isKEEP_LOADED_INPUT_FILE_IN_MEMORY(), "keepLoadedFileInMemory not set correctly");
        boolean isFileLoaded = TestData.isFileLoaded(inputDataFileName);
        assertEquals(keepLoadedFileInMemory, isFileLoaded, "File should be loaded in memory");
    }

    @Test
    public void removeLoadedFileFromMemoryTest() throws Exception {
        keepLoadedFileInMemory = false;
        webAnalyticTool = WebAnalyticTool.OMNITURE_DEBUGGER;
        reset();
        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, log4jPropertiesAbsoluteFilePath);
        assertNotNull(engine, "Engine should not be null");
        TestData.getSectionsFor(inputDataFileName, actionName);
        assertEquals(keepLoadedFileInMemory, CONFIG.isKEEP_LOADED_INPUT_FILE_IN_MEMORY(), "keepLoadedFileInMemory not set correctly");
        boolean isFileLoaded = TestData.isFileLoaded(inputDataFileName);
        assertEquals(keepLoadedFileInMemory, isFileLoaded, "File should NOT be loaded in memory");
    }
}