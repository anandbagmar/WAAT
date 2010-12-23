package com.thoughtworks.webanalyticsautomation;

import com.thoughtworks.webanalyticsautomation.common.CONFIG;
import com.thoughtworks.webanalyticsautomation.utils.TestBase;
import org.testng.annotations.Test;
import static com.thoughtworks.webanalyticsautomation.Controller.getInstance;
import static org.testng.Assert.*;

public class ControllerTest extends TestBase {

    @Test
    public void getInstanceTest() throws Exception {
        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, logDirectory);
        assertNotNull(engine);
        assertEquals(webAnalyticTool.toUpperCase(), CONFIG.getWEB_ANALYTIC_TOOL().name(), "WebAnalyticTool not set correctly");
        assertEquals(inputFileType.toUpperCase(), CONFIG.getINPUT_FILE_TYPE().name(), "Input file type not set correctly");
        assertEquals(keepLoadedFileInMemory, CONFIG.isKEEP_LOADED_INPUT_FILE_IN_MEMORY(), "keepLoadedFileInMemory not set correctly");
        assertEquals(logDirectory, CONFIG.getOUTPUT_LOG_DIRECTORY(), "Output log directory not set correctly");
    }

    @Test
    public void getInstanceTestDefaultParameters() throws Exception {
        engine = getInstance();
        assertNotNull(engine);
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
