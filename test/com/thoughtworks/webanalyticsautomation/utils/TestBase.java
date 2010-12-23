package com.thoughtworks.webanalyticsautomation.utils;

import com.thoughtworks.webanalyticsautomation.Engine;
import org.apache.log4j.Logger;

public class TestBase{
    protected Logger logger = Logger.getLogger(getClass());
/*
    private static String TEST_RUN_START_LOG_MESSAGE = "\n\n" +
            "------------------------------------------------------------------------------------\n" +
            "--------------------------------" +
            "Starting new Test Run" +
            "-------------------------------\n" +
            "------------------------------------------------------------------------------------\n";
*/

/*
    protected void newTestLogMessage(String testName) {
        logger.info("\n" +
                "------------------------------------------\n" +
                "Running Test : " + testName +
                "\n------------------------------------------\n");
    }
*/
    protected Engine engine;
    protected String webAnalyticTool = "omniture";
    protected String inputFileType = "xml";
    protected boolean keepLoadedFileInMemory = true;
    protected String logDirectory = System.getProperty("user.directory");

    protected String inputDataFileName = "E:\\Work\\src\\WAAT\\test\\sampledata\\OmnitureData.xml";
    protected String actionName = "OpenScheduler_Omniture_Tag_ForAll";

}
