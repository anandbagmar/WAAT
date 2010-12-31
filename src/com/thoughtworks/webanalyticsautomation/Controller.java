package com.thoughtworks.webanalyticsautomation;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 */

import com.thoughtworks.webanalyticsautomation.common.CONFIG;
import com.thoughtworks.webanalyticsautomation.inputdata.InputFileType;
import com.thoughtworks.webanalyticsautomation.plugins.WebAnalyticTool;
import org.apache.log4j.Logger;

public class Controller extends CONFIG {
    private static Engine engine;

    static {
        logger = Logger.getLogger(Controller.class.getName());
    }

    public static Engine getInstance(String webAnalyticTool,
                                     String inputFileType,
                                     boolean keepLoadedInputFileInMemory,
                                     String log4jPropertiesAbsoluteFilePath) throws IllegalArgumentException {
        return getEngineInstance(webAnalyticTool, inputFileType, keepLoadedInputFileInMemory, log4jPropertiesAbsoluteFilePath);
    }

    static Engine getInstance () throws IllegalArgumentException {
        return getEngineInstance(WebAnalyticTool.OMNITURE.name(), InputFileType.XML.name(), true, CONFIG.getLOG4J_PROPERTIES_ABSOLUTE_FILE_PATH());
    }

    private static Engine getEngineInstance(String webAnalyticTool, String inputFileType, boolean keepLoadedInputFileInMemory, String log4jPropertiesAbsoluteFilePath) {
        if (null != engine) {
            logger.info("Returning existing Engine reference");
            return engine;
        }
        else {
            engine = createNewEngine(webAnalyticTool, inputFileType, keepLoadedInputFileInMemory, log4jPropertiesAbsoluteFilePath);
            return engine;
        }
    }

    private static Engine createNewEngine(String webAnalyticTool,
                                          String inputFileType,
                                          boolean keepLoadedInputFileInMemory,
                                          String log4jPropertiesAbsoluteFilePath) {
        setUpConfig(webAnalyticTool, inputFileType, keepLoadedInputFileInMemory, log4jPropertiesAbsoluteFilePath);
        logger.info("Creating new Engine reference");
        return new Engine ();
    }
}
