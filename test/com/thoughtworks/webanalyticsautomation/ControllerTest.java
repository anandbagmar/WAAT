package com.thoughtworks.webanalyticsautomation;

import com.thoughtworks.webanalyticsautomation.common.Config;
import com.thoughtworks.webanalyticsautomation.common.TestBase;
import com.thoughtworks.webanalyticsautomation.plugins.WebAnalyticTool;
import org.testng.annotations.Test;

import static com.thoughtworks.webanalyticsautomation.Controller.getInstance;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class ControllerTest extends TestBase {

    @Test
    public void getInstanceTest() throws Exception {
        webAnalyticTool = WebAnalyticTool.OMNITURE_DEBUGGER;
        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, log4jPropertiesAbsoluteFilePath);
        assertNotNull(engine);
        assertEquals(webAnalyticTool, Config.getWEB_ANALYTIC_TOOL(), "WebAnalyticTool not set correctly");
        assertEquals(inputFileType, Config.getINPUT_FILE_TYPE(), "Input file type not set correctly");
        assertEquals(keepLoadedFileInMemory, Config.isKEEP_LOADED_INPUT_FILE_IN_MEMORY(), "keepLoadedFileInMemory not set correctly");
        assertEquals(log4jPropertiesAbsoluteFilePath, Config.getLOG4J_PROPERTIES_ABSOLUTE_FILE_PATH(), "log4j Properties file path not set correctly");
    }

    @Test
    public void getInstanceTestDefaultParameters() throws Exception {
        engine = getInstance();
        assertNotNull(engine, "Engine should not be null");
    }

    @Test
    public void enableAndDisableWebAnalyticsTesting() throws Exception {
        engine = getInstance();
        engine.enableWebAnalyticsTesting();
        assertEquals(true, engine.isWebAnalyticsTestingEnabled(), "WebAnalytics Testing should be enabled.");
        engine.disableWebAnalyticsTesting();
        assertEquals(false, engine.isWebAnalyticsTestingEnabled(), "WebAnalytics Testing should be disabled.");
    }
}